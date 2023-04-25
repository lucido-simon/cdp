package fr.polytech.polystore.inventory.controllers;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InventoryListener {

    @RabbitListener(queues = "${order.inventory.queue}")
    public void receive(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Received <" + payload + ">" + tag);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "${order.inventory.compensation.queue}")
    public void compensate(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Compensating <" + payload + ">" + tag);
        channel.basicAck(tag, false);
    }
}
