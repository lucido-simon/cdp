package fr.polytech.polystore.inventory.controllers;

import com.rabbitmq.client.Channel;
import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.inventory.service.InventoryService;
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
public class InventoryListener {
    private static final Logger logger = LoggerFactory.getLogger(InventoryListener.class);

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private InventoryService inventoryService;

    @RabbitListener(queues = "${order.inventory.queue}")
    public void receive(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException, PolystoreException.NotFound {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<List<StockDTO>>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<List<StockDTO>> message = (PolyStoreMessage<List<StockDTO>>) converter.fromMessage(payload, typeRef);
            logger.info("Received message: {}", message.getOrderStatus());
            logger.debug("Payload: {}", message.getPayload());

            inventoryService.removeStockFromOrder(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }

    @RabbitListener(queues = "${order.inventory.compensation.queue}")
    public void compensate(@Payload Message payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws IOException, PolystoreException.NotFound {
        try {
            Jackson2JsonMessageConverter converter = (Jackson2JsonMessageConverter) messageConverter;
            ParameterizedTypeReference<PolyStoreMessage<OrderStatus>> typeRef = new ParameterizedTypeReference<>() {
            };
            PolyStoreMessage<OrderStatus> message = (PolyStoreMessage<OrderStatus>) converter.fromMessage(payload, typeRef);
            logger.info("Received compensation message: {}", message.getOrderStatus());
            logger.debug("Payload: {}", message.getPayload());

            inventoryService.compensate(message);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicReject(tag, !redelivered);
            throw e;
        }
    }
}
