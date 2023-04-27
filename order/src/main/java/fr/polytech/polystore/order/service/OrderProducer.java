package fr.polytech.polystore.order.service;

import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProducer {

    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue orderInventoryQueue;

    @Autowired
    private Queue orderInventoryCompensationQueue;

    @Autowired
    private Queue orderPaymentQueue;

    @Autowired
    private Queue orderPaymentCompensationQueue;

    @Autowired
    private Queue orderShippingQueue;

    @Autowired
    private Queue orderShippingCompensationQueue;

    public void convertAndSendShipping(OrderDTO orderDTO) {
        logger.info("Sending to shipping for {}", orderDTO.getId());
        PolyStoreMessage<OrderDTO> message = new PolyStoreMessage<>(orderDTO.getId(), orderDTO.getOrderStatus(), orderDTO);
        logger.debug("Message: {}", message);
        this.template.convertAndSend(orderShippingQueue.getName(), message);
    }

    public void convertAndSendCompensationShipping(String orderId, OrderStatus orderStatus) {
        logger.info("Sending compensation to shipping for {}", orderId);
        this.template.convertAndSend(orderShippingCompensationQueue.getName(), new PolyStoreMessage<OrderStatus>(orderId, orderStatus, orderStatus));
        this.convertAndSendCompensationPayment(orderId, orderStatus);
    }

    public void convertAndSendInventory(List<StockDTO> stocks, String orderId, OrderStatus orderStatus) {
        logger.info("Sending to inventory for {}", orderId);
        PolyStoreMessage<List<StockDTO>> message = new PolyStoreMessage<>(orderId, orderStatus, stocks);
        logger.debug("Message: {}", message);
        this.template.convertAndSend(orderInventoryQueue.getName(), message);
    }

    public void convertAndSendCompensationInventory(String orderId, OrderStatus orderStatus) {
        logger.info("Sending compensation to inventory for {}", orderId);
        this.template.convertAndSend(orderInventoryCompensationQueue.getName(), new PolyStoreMessage<OrderStatus>(orderId, orderStatus, orderStatus));
    }

    public void convertAndSendPayment(OrderDTO orderDTO) {
        logger.info("Sending to payment for {}", orderDTO.getId());
        PolyStoreMessage<OrderDTO> message = new PolyStoreMessage<>(orderDTO.getId(), orderDTO.getOrderStatus(), orderDTO);
        logger.debug("Message: {}", message);
        this.template.convertAndSend(orderPaymentQueue.getName(), message);
    }

    public void convertAndSendCompensationPayment(String orderId, OrderStatus orderStatus) {
        logger.info("Sending compensation to payment for {}", orderId);
        this.template.convertAndSend(orderPaymentCompensationQueue.getName(), new PolyStoreMessage<OrderStatus>(orderId, orderStatus, orderStatus));
        this.convertAndSendCompensationInventory(orderId, orderStatus);
    }
}
