package fr.polytech.polystore.order.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.order.models.PolyStoreEvent;
import fr.polytech.polystore.order.models.PolyStoreEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCommand {
    private static final Logger logger = LoggerFactory.getLogger(OrderCommand.class);

    @Value("${order.cqrs.topic}")
    private String ORDER_CQRS_TOPIC;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "${order.cqrs.compensation.topic}", groupId = "order-compensation")
    public void queryCompensation(String message) {
        logger.info("Received compensation message: {}", message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            PolyStoreEvent<OrderStatus> event = mapper.readValue(message, PolyStoreEvent.class);
            switch (event.getEventType()) {
                case CreateOrder:
                    event.setOrderStatus(OrderStatus.OrderCreationFailed);
                    this.statusUpdate(event);
                    this.orderProducer.convertAndSendCompensationInventory(event.getOrderId(), OrderStatus.OrderCreationFailed);
                    break;
                case UpdateProducts:
                    event.setOrderStatus(OrderStatus.OrderPreparationFailed);
                    this.statusUpdate(event);
                    this.orderProducer.convertAndSendCompensationInventory(event.getOrderId(), OrderStatus.OrderCreationFailed);
                    break;
                case UpdatePayment:
                    event.setOrderStatus(OrderStatus.OrderPaymentFailed);
                    this.statusUpdate(event);
                    this.orderProducer.convertAndSendCompensationPayment(event.getOrderId(), OrderStatus.OrderPaymentFailed);
                    break;
                case UpdateShipment:
                    event.setOrderStatus(OrderStatus.OrderDeliveryFailed);
                    this.statusUpdate(event);
                    this.orderProducer.convertAndSendCompensationShipping(event.getOrderId(), OrderStatus.OrderDeliveryFailed);
                    break;
                default:
                    logger.error("Unknown event type: {}", event.getEventType());
                    break;
            }
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing compensation message", e);
        }
    }



    public String createOrder(PolyStoreMessage<OrderDTO> event) {
        String id = java.util.UUID.randomUUID().toString();
        event.setOrderId(id);
        event.setOrderStatus(OrderStatus.OrderCreated);
        OrderDTO orderDTO = event.getPayload();
        orderDTO.setId(id);
        logger.info("Creating order {}", orderDTO.getId());

        this.sendEvent(new PolyStoreEvent<>(PolyStoreEventType.CreateOrder, event));

        PolyStoreMessage<List<StockDTO>> stockEvent = new PolyStoreMessage<>();
        stockEvent.setOrderId(id);
        stockEvent.setOrderStatus(OrderStatus.OrderCreated);
        stockEvent.setPayload(orderDTO.getOrderProducts());

        orderProducer.convertAndSendInventory(stockEvent);

        return orderDTO.getId();
    }

    public void updateProducts(PolyStoreMessage<List<StockDTO>> event) {
        logger.info("Inventory event for order: " + event.getOrderId());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(OrderStatus.OrderPrepared);
        orderDTO.setId(event.getOrderId());
        orderDTO.setOrderProducts(event.getPayload());

        this.sendEvent(new PolyStoreEvent<>(PolyStoreEventType.UpdateProducts, event));

        orderProducer.convertAndSendPayment(orderDTO);
    }

    public void updatePayment(PolyStoreMessage<PaymentDTO> message) {
        logger.info("Payment event for order: " + message.getOrderId());

        this.sendEvent(new PolyStoreEvent<>(PolyStoreEventType.UpdatePayment, message));

        if (message.getOrderStatus() == OrderStatus.OrderPaid) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(message.getOrderId());
            orderDTO.setOrderStatus(message.getOrderStatus());
            this.orderProducer.convertAndSendShipping(orderDTO);
        }
    }

    public void shippingUpdate(PolyStoreMessage<ShipmentDTO> message) {
        logger.info("Shipping update for order: " + message.getOrderId());

        this.sendEvent(new PolyStoreEvent<>(PolyStoreEventType.UpdateShipment, message));

        if (message.getOrderStatus() == OrderStatus.OrderDelivered) {
            logger.info("Order delivered: " + message.getOrderId());
        }
    }

    public void statusUpdate(PolyStoreMessage<OrderStatus> message) {
        logger.info("Changing status for {}: {}", message.getOrderId(), message.getPayload());

        this.sendEvent(new PolyStoreEvent<>(PolyStoreEventType.UpdateStatus, message));
    }

    private void sendEvent(PolyStoreEvent<?> event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(event);
            this.kafkaTemplate.send(ORDER_CQRS_TOPIC, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void handleShippingCompensate(PolyStoreMessage<OrderStatus> message) {
        orderProducer.convertAndSendCompensationPayment(message.getOrderId(), message.getOrderStatus());
        this.statusUpdate(message);
    }

    public void handePaymentCompensate(PolyStoreMessage<OrderStatus> message) {
        orderProducer.convertAndSendCompensationInventory(message.getOrderId(), message.getOrderStatus());
        this.statusUpdate(message);
    }

    public void handeInventoryCompensate(PolyStoreMessage<OrderStatus> message) {
        this.statusUpdate(message);
    }
}
