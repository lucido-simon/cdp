package fr.polytech.polystore.cart.services;

import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue cartOrderQueue;

    private static final Logger logger = LoggerFactory.getLogger(CartProducer.class);

    public String send(OrderDTO orderDTO) {
        PolyStoreMessage<OrderDTO> message = new PolyStoreMessage<>(null, OrderStatus.OrderCheckout, orderDTO);
        logger.info("Sending event: {}", message.getOrderStatus());
        logger.debug("Message: {}", message);
        return (String) this.template.convertSendAndReceive(cartOrderQueue.getName(), message);
    }
}
