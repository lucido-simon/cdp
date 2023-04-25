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

    public void convertAndSendInventory(List<StockDTO> stocks, String orderId, OrderStatus orderStatus) {
        logger.info("Sending to inventory for {}", orderId);
        PolyStoreMessage<List<StockDTO>> message = new PolyStoreMessage<>(orderId, orderStatus, stocks);
        logger.debug("Message: {}", message);
        this.template.convertAndSend(orderInventoryQueue.getName(), message);
    }

    public void convertAndSendCompensationInventory() {
        String message = "Hello World!";
        this.template.convertAndSend(orderInventoryCompensationQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
