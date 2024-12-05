package com.parking.main.services.impl;

import com.parking.main.mappers.impl.PaymentMapper;
import com.parking.main.models.Payment;
import com.parking.main.repositories.PaymentRepository;
import com.parking.main.services.DTO.PaymentsDTO;
import com.parking.main.services.PaymentService;
import com.parking.main.services.RabbitMQService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {
    private final String paymentResultQueue = "paymentResultQueue";
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    @Autowired
    public RabbitMQServiceImpl(PaymentMapper paymentMapper, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @Override
    @RabbitListener(queues = paymentResultQueue)
    public void listen(PaymentsDTO paymentsDTO) {
        System.out.println("Транзакция получена на main-сервисе из paymentResultQueue: " + paymentsDTO);
        PaymentsDTO updatePayment = paymentService.updatePayment(paymentsDTO.getId(), paymentsDTO);
        System.out.println("Сохранили в базу данных: " + updatePayment);
    }
}
