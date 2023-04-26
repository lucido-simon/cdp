package fr.polytech.polystore.payment.service;

import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentProducer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProducer.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue paymentOrderQueue;

    @Autowired
    private Queue paymentOrderCompensationQueue;

    public void convertAndSend(PaymentDTO payment, String orderId, OrderStatus orderStatus) {
        logger.info("Sending to order for {}", orderId);
        PolyStoreMessage<PaymentDTO> message = new PolyStoreMessage<>(orderId, orderStatus, payment);
        logger.debug("Message: {}", message);
        this.template.convertAndSend(paymentOrderQueue.getName(), message);
    }

    public void convertAndSendCompensation(String orderId, OrderStatus orderStatus) {
        logger.warn("Sending compensation to order for {}", orderId);
        this.template.convertAndSend(paymentOrderCompensationQueue.getName(), new PolyStoreMessage<OrderStatus>(orderId, orderStatus, orderStatus));
    }
}
