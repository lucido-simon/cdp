package fr.polytech.polystore.payment.service;

import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.payment.entities.Payment;
import fr.polytech.polystore.payment.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentDTO> getAllPayments() {
        return this.paymentRepository.findAll().stream().map(this::paymentToPaymentDTO).collect(Collectors.toList());
    }

    private PaymentDTO paymentToPaymentDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getOrderId(), payment.getPrice(), payment.getPaymentStatus());
    }
}