package fr.polytech.polystore.order.controllers;

import com.rabbitmq.client.Channel;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderListener {
    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @RabbitListener(queues = "${inventory.order.queue}")
    public void receiveInventory(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Received <" + payload + ">" + tag);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "${inventory.order.compensation.queue}")
    public void receiveCompensateInventory(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Compensating <" + payload + ">" + tag);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "${cart.order.queue}")
    public String receiveOrder(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderDTO>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderDTO> message = (PolyStoreMessage<OrderDTO>) converter.fromMessage(payload, typeRef);
            logger.info("Received message: {}", message.getOrderStatus());
            logger.info("Payload: {}", message.getPayload());

            String id = orderService.createOrder(message.getPayload());
            channel.basicAck(tag, false);
            return id;
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }
}
