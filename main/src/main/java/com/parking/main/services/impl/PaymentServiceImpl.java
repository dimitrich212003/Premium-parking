package com.parking.main.services.impl;

import com.parking.main.mappers.impl.PaymentMapper;
import com.parking.main.models.Payment;
import com.parking.main.repositories.PaymentRepository;
import com.parking.main.services.DTO.PaymentsDTO;
import com.parking.main.services.PaymentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, RabbitTemplate rabbitTemplate) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<PaymentsDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> paymentMapper.toDTO(payment))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentsDTO getPaymentById(UUID id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        return paymentMapper.toDTO(payment);
    }

    @Override
    public PaymentsDTO createPayment(PaymentsDTO paymentsDTO) {
        Payment payment = paymentMapper.toModel(paymentsDTO);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("PENDING");

        Payment createdPayment = paymentRepository.saveAndFlush(payment);
        PaymentsDTO createdPaymentDTO = paymentMapper.toDTO(createdPayment);

        createdPaymentDTO.setPaymentDate(createdPayment.getPaymentDate().toString());

        rabbitTemplate.convertAndSend("parkingExchange", "payment.key", createdPaymentDTO);

        return createdPaymentDTO;
    }

    @Override
    public PaymentsDTO updatePayment(UUID id, PaymentsDTO paymentsDTO) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(paymentsDTO.getStatus());

        Payment updatePayment = paymentRepository.saveAndFlush(payment);
        return paymentMapper.toDTO(updatePayment);
    }

    @Override
    public void deletePayment(UUID id) {
        paymentRepository.deleteById(id);
    }
}
