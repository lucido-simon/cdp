package fr.polytech.polystore.order.service;

import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.order.entities.Order;
import fr.polytech.polystore.order.entities.OrderProduct;
import fr.polytech.polystore.order.repositories.OrderProductRepository;
import fr.polytech.polystore.order.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public String createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(java.util.UUID.randomUUID().toString());
        order.setUserId(orderDTO.getUserId());
        order.setOrderStatus(OrderStatus.OrderCreated);

        List<OrderProduct> orderProducts = orderDTO.getOrderProducts().stream().map(product -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(product.getId());
            orderProduct.setQuantity(product.getQuantity());
            orderProduct.setOrder(order);
            return orderProduct;
        }).collect(Collectors.toList());

        order.setOrderProducts(orderProducts);
        orderRepository.save(order);

        try {
            orderProducer.convertAndSendInventory(orderProducts.stream().map(product -> {
                        StockDTO stockDTO = new StockDTO();
                        stockDTO.setId(product.getProductId());
                        stockDTO.setQuantity(product.getQuantity());
                        return stockDTO;
                    }).collect(Collectors.toList())
                    , order.getId(), order.getOrderStatus());
        } catch (Exception e) {
            this.compensate(order.getId());
        }

        return order.getId();
    }

    public void compensate(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            order.get().setOrderStatus(OrderStatus.OrderCreationFailed);
            orderRepository.save(order.get());
        }
    }

    public OrderDTO getOrder(String id) throws PolystoreException.NotFound {
        return this.orderToOrderDTO(orderRepository.findById(id).orElseThrow(() -> new PolystoreException.NotFound("Order not found: " + id)));
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::orderToOrderDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::orderToOrderDTO).collect(Collectors.toList());
    }

//    @Transactional
//    public void shippingResponse(PolyStoreMessage<ShipmentDTO> message) {
//        logger.info("Shipping response for order: " + message.getOrderId());
//        ShipmentDTO shipping = message.getPayload();
//
//        try {
//            orderRepository.findById(message.getOrderId()).ifPresentOrElse(order -> {
//                order.setOrderStatus(OrderStatus.OrderDelivered);
//                order.setShipmentId(shipping.getId());
//                orderRepository.save(order);
//            }, () -> logger.error("Order not found while handling shipping: " + message.getOrderId()))
//        } catch (Exception e) {
//
//        }
//    }
    @Transactional
    public void inventoryResponse(PolyStoreMessage<List<StockDTO>> payload) {
        logger.info("Inventory response for order: " + payload.getOrderId());
        payload.getPayload().forEach(stockDTO -> {
            Optional<OrderProduct> orderProduct = orderProductRepository.findOrderProductByProductIdAndOrderId(stockDTO.getId(), payload.getOrderId());
            if (orderProduct.isPresent()) {
                orderProduct.get().setQuantity(stockDTO.getQuantity());
                orderProductRepository.save(orderProduct.get());
            } else {
                logger.error("Order product not found: " + stockDTO.getId());
            }
        });
        // Safe since we know it exists due to SQL constraint and above code
        Order order = orderRepository.findById(payload.getOrderId()).get();
        order.setOrderStatus(OrderStatus.OrderPrepared);
        orderRepository.save(order);

        orderProducer.convertAndSendPayment(orderToOrderDTO(order));
    }

    @Transactional
    public void paymentCompensate(PolyStoreMessage<OrderStatus> payload) {
        logger.warn("Compensating order due to payment failure: " + payload.getOrderId());
        try {
            orderRepository.findById(payload.getOrderId()).ifPresentOrElse(order -> {
                order.setOrderStatus(OrderStatus.OrderPaymentFailed);
                orderRepository.save(order);
            }, () -> logger.error("Order not found while compensating payment: " + payload.getOrderId()));

            orderProducer.convertAndSendCompensationInventory(payload.getOrderId(), OrderStatus.OrderPreparationFailed);
        } catch (Exception e) {
            logger.error("Error while compensating: " + e.getMessage());
        }
    }

    @Transactional
    public void paymentResponse(PolyStoreMessage<PaymentDTO> message) {
        try {
            orderRepository.findById(message.getOrderId()).ifPresentOrElse(order -> {
                order.setOrderStatus(message.getOrderStatus());
                if (message.getOrderStatus() == OrderStatus.OrderProcessingPayment) {
                    order.setPaymentId(message.getPayload().getId());
                    orderRepository.save(order);
                } else {
                    orderRepository.save(order);
                    this.orderProducer.convertAndSendShipping(orderToOrderDTO(order));
                }
            }, () -> logger.error("Order {} not found while handling payment", message.getOrderId()));
        } catch (Exception e) {
            logger.error("Error while handling payment for order {}: {}", message.getOrderId(), e.getMessage());

            Order order = orderRepository.findById(message.getOrderId()).get();
            order.setOrderStatus(OrderStatus.OrderPaymentFailed);
            orderRepository.save(order);

            logger.error("Compensating...");
            this.orderProducer.convertAndSendCompensationPayment(message.getOrderId(), OrderStatus.OrderPaymentFailed);
        }
    }

    public void inventoryCompensate(PolyStoreMessage<OrderStatus> payload) {
        logger.warn("Compensating inventory for order: " + payload.getOrderId());
        try {
            orderRepository.findById(payload.getOrderId()).ifPresentOrElse(order -> {
                order.setOrderStatus(OrderStatus.OrderPreparationFailed);
                orderRepository.save(order);
            }, () -> logger.error("Order not found while compensating inventory: " + payload.getOrderId()));
        } catch (Exception e) {
            logger.error("Error while compensating inventory: " + e.getMessage());
        }
    }

    private OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPaymentId(order.getPaymentId());
        orderDTO.setShipmentId(order.getShippingId());
        orderDTO.setOrderProducts(order.getOrderProducts().stream().map(orderProduct -> {
            StockDTO orderProductDTO = new StockDTO();
            orderProductDTO.setId(orderProduct.getProductId());
            orderProductDTO.setQuantity(orderProduct.getQuantity());
            orderProductDTO.setPrice((double) orderProduct.getPrice());
            return orderProductDTO;
        }).collect(Collectors.toList()));
        return orderDTO;
    }
}