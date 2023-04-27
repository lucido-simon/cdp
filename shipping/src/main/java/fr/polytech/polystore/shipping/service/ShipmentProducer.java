package fr.polytech.polystore.shipping.service;

import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShipmentProducer {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentProducer.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue shippingOrderQueue;

    @Autowired
    private Queue shippingOrderCompensationQueue;

    public void convertAndSend(ShipmentDTO shipment, String orderId, OrderStatus orderStatus) {
        logger.info("Sending to order for {}", orderId);
        PolyStoreMessage<ShipmentDTO> message = new PolyStoreMessage<>(orderId, orderStatus, shipment);
        logger.debug("Message: {}", message);
        this.template.convertAndSend(shippingOrderQueue.getName(), message);
    }

    public void convertAndSendCompensation(String orderId, OrderStatus orderStatus) {
        logger.warn("Sending compensation to order for {}", orderId);
        this.template.convertAndSend(shippingOrderCompensationQueue.getName(), new PolyStoreMessage<OrderStatus>(orderId, orderStatus, orderStatus));
    }
}
