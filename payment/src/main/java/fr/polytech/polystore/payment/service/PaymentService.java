package fr.polytech.polystore.payment.service;

import fr.polytech.polystore.payment.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
}