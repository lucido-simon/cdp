package fr.polytech.polystore.inventory.service;

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
public class InventoryProducer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryProducer.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue inventoryOrderQueue;

    @Autowired
    private Queue inventoryOrderCompensationQueue;

    public void send(List<StockDTO> stocks, String orderId) {
        logger.info("Sending from inventory for {}", orderId);
        PolyStoreMessage<List<StockDTO>> message = new PolyStoreMessage<>(orderId, OrderStatus.OrderPrepared, stocks);
        logger.debug("Message: {}", message);
        this.template.convertAndSend(inventoryOrderQueue.getName(), message);
    }

    public void convertAndSendCompensation() {
        String message = "Hello World!";
        this.template.convertAndSend(inventoryOrderCompensationQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
