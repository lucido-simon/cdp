package fr.polytech.polystore.order.controllers;

import com.rabbitmq.client.Channel;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.order.service.OrderCommand;
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
public class OrderListener {
    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private OrderCommand orderCommand;

    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);
    @RabbitListener(queues = "${shipping.order.compensation.queue}")

    public void receiveCompensateShipping(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderStatus>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderStatus> message = (PolyStoreMessage<OrderStatus>) converter.fromMessage(payload, typeRef);
            logger.warn("Received compensation from shipping for order: {}", message.getOrderId());
            logger.debug("Payload: {}", message.getPayload());

            orderCommand.shippingCompensate(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }

    @RabbitListener(queues = "${shipping.order.queue}")
    public void receiveShipping(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<ShipmentDTO>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<ShipmentDTO> message = (PolyStoreMessage<ShipmentDTO>) converter.fromMessage(payload, typeRef);
            logger.info("Received message from shipment: {}", message.getOrderStatus());
            logger.debug("Payload: {}", message.getPayload());

            orderCommand.shppingUpdate(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }
    @RabbitListener(queues = "${payment.order.compensation.queue}")
    public void receiveCompensatePayment(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderStatus>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderStatus> message = (PolyStoreMessage<OrderStatus>) converter.fromMessage(payload, typeRef);
            logger.warn("Received compensation from payment for order: {}", message.getOrderId());
            logger.debug("Payload: {}", message.getPayload());

            orderCommand.paymentCompensate(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }

    @RabbitListener(queues = "${payment.order.queue}")
    public void receivePayment(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<PaymentDTO>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<PaymentDTO> message = (PolyStoreMessage<PaymentDTO>) converter.fromMessage(payload, typeRef);
            logger.info("Received message from payment: {}", message.getOrderStatus());
            logger.debug("Payload: {}", message.getPayload());

            orderCommand.updatePayment(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }

    @RabbitListener(queues = "${inventory.order.queue}")
    public void receiveInventory(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<List<StockDTO>>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<List<StockDTO>> message = (PolyStoreMessage<List<StockDTO>>) converter.fromMessage(payload, typeRef);
            logger.info("Received message: {}", message.getOrderStatus());
            logger.debug("Payload: {}", message.getPayload());

            orderCommand.updateProducts(message);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }

    @RabbitListener(queues = "${inventory.order.compensation.queue}")
    public void receiveCompensateInventory(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderStatus>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderStatus> message = (PolyStoreMessage<OrderStatus>) converter.fromMessage(payload, typeRef);
            logger.warn("Received compensation from inventory for order: {}", message.getOrderId());
            logger.debug("Payload: {}", message.getPayload());

            orderCommand.inventoryCompensate(message);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }

    @RabbitListener(queues = "${cart.order.queue}")
    public String receiveOrder(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderDTO>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderDTO> message = (PolyStoreMessage<OrderDTO>) converter.fromMessage(payload, typeRef);
            logger.info("Received message: {}", message.getOrderStatus());
            logger.debug("Payload: {}", message.getPayload());

            String id = orderCommand.createOrder(message);
            channel.basicAck(tag, false);
            return id;
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }
}
