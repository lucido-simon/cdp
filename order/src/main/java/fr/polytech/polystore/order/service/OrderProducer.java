package fr.polytech.polystore.order.service;

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
}
