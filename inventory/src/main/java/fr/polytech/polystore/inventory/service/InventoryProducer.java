package fr.polytech.polystore.inventory.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue inventoryOrderQueue;

    @Autowired
    private Queue inventoryOrderCompensationQueue;

    public void convertAndSend() {
        String message = "Hello World!";
        this.template.convertAndSend(inventoryOrderQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    public void convertAndSendCompensation() {
        String message = "Hello World!";
        this.template.convertAndSend(inventoryOrderCompensationQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
