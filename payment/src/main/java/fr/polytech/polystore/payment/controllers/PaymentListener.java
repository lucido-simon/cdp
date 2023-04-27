package fr.polytech.polystore.payment.controllers;

import com.rabbitmq.client.Channel;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.payment.service.PaymentService;
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
import java.util.List;

@Service
public class PaymentListener {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private PaymentService paymentService;

    @RabbitListener(queues = "${order.payment.queue}")
    public void receive(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderDTO>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderDTO> message = (PolyStoreMessage<OrderDTO>) converter.fromMessage(payload, typeRef);
            logger.info("Received message: {}", message.getOrderStatus());
            logger.info("Payload: {}", message.getPayload());

            // TODO: Send to payment service
            this.paymentService.createPayment(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }

    @RabbitListener(queues = "${order.payment.compensation.queue}")
    public void compensate(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderStatus>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderStatus> message = (PolyStoreMessage<OrderStatus>) converter.fromMessage(payload, typeRef);
            logger.warn("Received compensation for order: {}", message.getOrderId());
            logger.debug("Payload: {}", message.getPayload());

            this.paymentService.compensatePayment(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }

    }
}
