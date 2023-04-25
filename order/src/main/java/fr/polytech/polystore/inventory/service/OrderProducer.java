package fr.polytech.polystore.inventory.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue orderInventoryQueue;

    @Autowired
    private Queue orderInventoryCompensationQueue;

    @Autowired
    private Queue orderCartQueue;

    public void convertAndSendInventory() {
        String message = "Hello World!";
        this.template.convertAndSend(orderInventoryQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    public void convertAndSendCompensationInventory() {
        String message = "Hello World!";
        this.template.convertAndSend(orderInventoryCompensationQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    public void convertAndSendCart() {
        String message = "Hello World!";
        this.template.convertAndSend(orderCartQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
