package fr.polytech.polystore.order.service;

import fr.polytech.polystore.common.dtos.OrderDTO;
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

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

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

            // TODO: Send to payment

        orderRepository.save(order);
    }

    @Transactional
    public void inventoryCompensate(PolyStoreMessage<OrderStatus> payload) {
        logger.warn("Compensating inventory for order: " + payload.getOrderId());
        try {
            orderRepository.findById(payload.getOrderId()).ifPresentOrElse(order -> {
                order.setOrderStatus(OrderStatus.OrderPreparationFailed);
                orderRepository.save(order);
            }, () -> logger.error("Order not found while compensating inventory: " + payload.getOrderId()));

            orderProducer.convertAndSendCompensationInventory(payload.getOrderId(), OrderStatus.OrderPreparationFailed);
        } catch (Exception e) {
            logger.error("Error while compensating inventory: " + e.getMessage());
        }
    }
}