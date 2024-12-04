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

    static final String paymentResultQueue = "paymentResultQueue";

    private final PaymentValidatorGrpc.PaymentValidatorBlockingStub stub;

    private final PaymentMapper paymentMapper;
    private final RabbitTemplate rabbitTemplate;
    private final GrpcConnectionTestService grpcConnectionTestService;


    public RabbitMQService(PaymentValidatorGrpc.PaymentValidatorBlockingStub stub, PaymentMapper paymentMapper, RabbitTemplate rabbitTemplate, GrpcConnectionTestService grpcConnectionTestService) {
        this.stub = stub;
        this.paymentMapper = paymentMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.grpcConnectionTestService = grpcConnectionTestService;
    }

    @RabbitListener(queues = paymentQueue)
    public void listen(Payment payment) {
        System.out.println("Транзакция получена из paymentQueue: " + payment);

        if (grpcConnectionTestService.testConnection()) {
            System.out.println("Соединение с сервером gRPC установлено.");
        } else {
            System.err.println("Не удалось подключиться к серверу gRPC.");
            return;
        }

        PaymentCheck.ValidatePaymentRequest request = PaymentCheck.ValidatePaymentRequest.newBuilder()
                .setPayment(paymentMapper.toProtoPayment(payment))
                .build();
        System.out.println("Отправляем запрос в gRPC: " + request);

        PaymentCheck.ValidatePaymentResponse response = null;
        try {
            response = stub.validatePayment(request);
            System.out.println("Ответ от gRPC: " + response);
        } catch (StatusRuntimeException e) {
            System.err.println("Ошибка при вызове gRPC: " + e.getStatus() + " - " + e.getMessage());
            e.printStackTrace();
        }

        if (response != null) {
            System.out.println("Результат валидации: " + response.getPayment());
        } else {
            System.err.println("Не удалось выполнить валидацию.");
        }

        rabbitTemplate.convertAndSend(paymentResultQueue, "payment.result.key", payment);
    }

}

