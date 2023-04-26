package fr.polytech.polystore.payment.service;

import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PaymentStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.payment.entities.Payment;
import fr.polytech.polystore.payment.repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    PaymentProducer paymentProducer;
    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentDTO> getAllPayments() {
        return this.paymentRepository.findAll().stream().map(this::paymentToPaymentDTO).collect(Collectors.toList());
    }

    @Transactional
    public void createPayment(PolyStoreMessage<OrderDTO> message) {
        logger.info("Creating payment for order {}", message.getOrderId());
        Payment payment;
        try {
            OrderDTO orderDTO = message.getPayload();
            double price = orderDTO.getOrderProducts()
                    .stream()
                    .map(orderProductDTO -> orderProductDTO.getPrice() * orderProductDTO.getQuantity())
                    .reduce(0.0, Double::sum);
            payment = new Payment();
            payment.setId(java.util.UUID.randomUUID().toString());
            payment.setOrderId(orderDTO.getId());
            payment.setPrice(price);
            payment.setPaymentStatus(PaymentStatus.PaymentCreated);
            this.paymentRepository.save(payment);
            logger.info("Payment created for order {}", message.getOrderId());
        } catch (Exception e) {
            logger.error("Error while creating payment for order {}: {}", message.getOrderId(), e.getMessage());
            this.paymentProducer.convertAndSendCompensation(message.getOrderId(), OrderStatus.OrderPaymentFailed);
            return;
        }

        // Simulate payment processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.debug(e.getMessage());
            logger.warn("Payment processing interrupted for order {}, ignoring..", message.getOrderId());
        }

        try {
            payment.setPaymentStatus(PaymentStatus.PaymentSucceeded);
            this.paymentRepository.save(payment);
            paymentProducer.convertAndSend(paymentToPaymentDTO(payment), message.getOrderId(), OrderStatus.OrderPaid);
        }
        catch (Exception e) {
            logger.error("Error after simulating payment for order {}: {}", message.getOrderId(), e.getMessage());
            this.failure(payment);
            this.paymentProducer.convertAndSendCompensation(message.getOrderId(), OrderStatus.OrderPaymentFailed);
        }
    }

    private void failure(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.PaymentFailed);
        paymentRepository.save(payment);
    }


    private PaymentDTO paymentToPaymentDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getOrderId(), payment.getPrice(), payment.getPaymentStatus());
    }
}