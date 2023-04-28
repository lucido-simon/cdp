package fr.polytech.polystore.order.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.order.entities.Order;
import fr.polytech.polystore.order.entities.OrderProduct;
import fr.polytech.polystore.order.models.PolyStoreEvent;
import fr.polytech.polystore.order.repositories.OrderProductRepository;
import fr.polytech.polystore.order.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderQuery {
    private static final Logger logger = LoggerFactory.getLogger(OrderQuery.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Value("${order.cqrs.compensation.topic}")
    private String ORDER_CQRS_COMPENSATION_TOPIC;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "${order.cqrs.topic}", groupId = "order-query")
    public void receiveEvent(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<PolyStoreEvent<?>> typeRef = new TypeReference<>() {
        };
        PolyStoreEvent<?> polyStoreEvent = objectMapper.readValue(message, typeRef);
        logger.debug("Received event: {}", polyStoreEvent);

        switch (polyStoreEvent.getEventType()) {
            case CreateOrder -> {
                TypeReference<PolyStoreEvent<OrderDTO>> qualifiedTypeRef = new TypeReference<>() {
                };
                this.createOrder(objectMapper.readValue(message, qualifiedTypeRef));
            }
            case UpdateProducts -> {
                TypeReference<PolyStoreEvent<List<StockDTO>>> qualifiedTypeRef = new TypeReference<>() {
                };
                this.updateProducts(objectMapper.readValue(message, qualifiedTypeRef));
            }
            case UpdatePayment -> {
                TypeReference<PolyStoreEvent<PaymentDTO>> qualifiedTypeRef = new TypeReference<>() {
                };
                this.updatePayment(objectMapper.readValue(message, qualifiedTypeRef));
            }
            case UpdateShipment -> {
                TypeReference<PolyStoreEvent<ShipmentDTO>> qualifiedTypeRef = new TypeReference<>() {
                };
                this.updateShipping(objectMapper.readValue(message, qualifiedTypeRef));
            }
            case UpdateStatus -> {
                TypeReference<PolyStoreEvent<OrderStatus>> qualifiedTypeRef = new TypeReference<>() {
                };
                this.updateStatus(objectMapper.readValue(message, qualifiedTypeRef));
            }
            default -> logger.error("Unknown event type: {}", polyStoreEvent.getEventType());
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

    @Transactional
    public void createOrder(PolyStoreEvent<OrderDTO> event) {
        logger.info("Query: creating order {}", event.getOrderId());
        try {
            OrderDTO orderDTO = event.getPayload();
            Order order = new Order();
            order.setId(orderDTO.getId());
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
        } catch (Exception e) {
            logger.error("Query: Error while creating order", e);
            this.compensate(event.getOrderId());
        }
    }

    @Transactional
    public void updateProducts(PolyStoreMessage<List<StockDTO>> event) {
        logger.info("Query: updating products for order {}", event.getOrderId());
        try {
            Order order = orderRepository.findById(event.getOrderId()).orElseThrow(() -> new PolystoreException.NotFound("Order not found: " + event.getOrderId()));
            order.setOrderStatus(event.getOrderStatus());

            for (StockDTO product : event.getPayload()) {
                this.orderProductRepository.findOrderProductByProductIdAndOrderId(product.getId(), order.getId()).ifPresentOrElse(orderProduct -> {
                    orderProduct.setQuantity(product.getQuantity());
                    orderProduct.setPrice(product.getPrice().floatValue());
                    orderProductRepository.save(orderProduct);
                }, () -> {
                    logger.warn("Query: Product {} not found in order {}, creating one..", product.getId(), order.getId());
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProductId(product.getId());
                    orderProduct.setQuantity(product.getQuantity());
                    orderProduct.setPrice(product.getPrice().floatValue());
                    orderProduct.setOrder(order);
                    orderProductRepository.save(orderProduct);
                });
            }
            orderRepository.save(order);
        } catch (Exception e) {
            logger.error("Query: Error while updating products", e);
            this.compensate(event.getOrderId());
        }
    }

    @Transactional
    public void updatePayment(PolyStoreMessage<PaymentDTO> message) {
        logger.info("Query: updating payment for order {}", message.getOrderId());
        try {
            orderRepository.findById(message.getOrderId()).ifPresentOrElse(order -> {
                order.setOrderStatus(message.getOrderStatus());
                order.setPaymentId(message.getPayload().getId());
                orderRepository.save(order);
            }, () -> logger.error("Order {} not found while handling payment", message.getOrderId()));
        } catch (Exception e) {
            logger.error("Query: Error while handling payment: " + e.getMessage());
            this.compensate(message.getOrderId());
        }
    }

    @Transactional
    public void updateShipping(PolyStoreMessage<ShipmentDTO> message) {
        logger.info("Query: handling shipping for order {}", message.getOrderId());
        ShipmentDTO shipping = message.getPayload();
        try {
            orderRepository.findById(message.getOrderId()).ifPresentOrElse(order -> {
                order.setOrderStatus(message.getOrderStatus());
                order.setShippingId(shipping.getId());
                orderRepository.save(order);
            }, () -> logger.error("Order not found while handling shipping: " + message.getOrderId()));
        } catch (Exception e) {
            logger.error("Error while handling shipping: " + e.getMessage());
            if (message.getOrderStatus() == OrderStatus.OrderDelivering) {
                this.compensate(message.getOrderId());
            } else {
                logger.error("Error while handling successful shipping: " + e.getMessage());
            }
        }
    }

    private void updateStatus(PolyStoreEvent<OrderStatus> orderStatusPolyStoreEvent) {
        logger.info("Query: Updating status for {}: {}", orderStatusPolyStoreEvent.getOrderId(), orderStatusPolyStoreEvent.getPayload());
        try {
            this.orderRepository.findById(orderStatusPolyStoreEvent.getOrderId()).ifPresentOrElse(
                    order -> {
                        order.setOrderStatus(orderStatusPolyStoreEvent.getOrderStatus());
                        orderRepository.save(order);
                    },
                    () -> logger.error("Query: Couldn't find order {}", orderStatusPolyStoreEvent.getOrderId())
            );
        } catch (Exception e) {
            logger.error("Query: couldn't update status for {}: {}", orderStatusPolyStoreEvent.getOrderId(), e.getMessage());
        }
    }

    private void compensate(String orderId) {
        logger.warn("Compensating order {}", orderId);
        // Todo: Send event
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
