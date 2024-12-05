package com.parking.grpcclient.service;

import com.parking.grpcclient.model.Payment;
import com.parking.grpcclient.util.PaymentMapper;
import com.premium.grpcclient.PaymentCheck;
import com.premium.grpcclient.PaymentValidatorGrpc;
import io.grpc.StatusRuntimeException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    static final String paymentQueue = "paymentQueue";
    static final String paymentExchange = "parkingExchange";
    static final String resultKey = "payment.result.key";
    private final PaymentValidatorGrpc.PaymentValidatorBlockingStub stub;
    private final PaymentMapper paymentMapper;
    private final RabbitTemplate rabbitTemplate;


    public RabbitMQService(PaymentValidatorGrpc.PaymentValidatorBlockingStub stub, PaymentMapper paymentMapper, RabbitTemplate rabbitTemplate) {
        this.stub = stub;
        this.paymentMapper = paymentMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = paymentQueue)
    public void listen(Payment payment) {
        System.out.println("Транзакция получена из paymentQueue: " + payment);


        PaymentCheck.ValidatePaymentRequest request = PaymentCheck.ValidatePaymentRequest.newBuilder()
                .setPayment(paymentMapper.toProtoPayment(payment))
                .build();

        try {
            payment.setStatus(stub.validatePayment(request).getPayment().getStatus());
            System.out.println("Ответ от gRPC: " + payment);
            rabbitTemplate.convertAndSend(paymentExchange, resultKey, payment);
        } catch (StatusRuntimeException e) {
            System.err.println("Ошибка при вызове gRPC: " + e.getStatus() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}

