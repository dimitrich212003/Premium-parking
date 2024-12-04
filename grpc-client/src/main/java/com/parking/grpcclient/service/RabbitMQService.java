package com.parking.grpcclient.service;

import com.parking.grpcclient.model.Payment;
import com.parking.grpcclient.util.PaymentMapper;
import com.premium.grpcclient.PaymentCheck;
import com.premium.grpcclient.PaymentValidatorGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    static final String paymentQueue = "paymentQueue";

    static final String paymentResultQueue = "paymentResultQueue";

    @GrpcClient("paymentValidator")
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
        System.out.println("Транзакция получена из paymentQueue: " + payment.toString());

        PaymentCheck.ValidatePaymentRequest request = PaymentCheck.ValidatePaymentRequest.newBuilder()
                .setPayment(paymentMapper.toProtoPayment(payment))
                .build();

        PaymentCheck.ValidatePaymentResponse response = null;
        try {
            response = stub.validatePayment(request);
        } catch (StatusRuntimeException e) {
            System.err.println("Ошибка при вызове gRPC: " + e.getStatus() + " - " + e.getMessage());
        }

        System.out.println("Результат валидации: " + response.getPayment());
        rabbitTemplate.convertAndSend(paymentResultQueue, "payment.result.key", payment);

        //todo сделать обработку результата в мейне чтобы он сохранял статус валидации + повесить слушателя на нотифаера чтобы он тоже был в курсе результатов проверки документов
    }
}

//package com.premium.grpcclient.service;
//
//import com.premium.grpcclient.model.Payment;
//import com.premium.grpcclient.proto.PaymentCheck;
//import com.premium.grpcclient.proto.PaymentValidatorGrpc;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitMQService {
//
//    private final PaymentValidatorGrpc.PaymentValidatorBlockingStub stub;
//
//    @Value("${grpc.routing-key.validation-result}")
//    private String validationResultRoutingKey;
//
//    @Value("${spring.rabbitmq.directExchangeName}")
//    private String exchangeName;
//
//    public RabbitMQService(PaymentValidatorGrpc.PaymentValidatorBlockingStub stub) {
//        this.stub = stub;
//    }
//
//    @RabbitListener(queues = "paymentQueue")
//    public void listen(Payment payment) {
//        System.out.println("Транзакция получена из paymentQueue: " + payment);
//
//        // Создание gRPC запроса
//        PaymentCheck.ValidatePaymentRequest request = PaymentCheck.ValidatePaymentRequest.newBuilder()
//                .setPayment(PaymentCheck.Payment.newBuilder()
//                        .setBooking(payment.getBooking().toString())
//                        .setAmount(payment.getAmount())
//                        .setPaymentDate(payment.getPaymentDate().toString())
//                        .setStatus(payment.getStatus())
//                        .build())
//                .build();
//
//        // Отправка gRPC запроса и получение результата
//        PaymentCheck.ValidatePaymentResponse response = stub.validatePayment(request);
//
//        // Обновление статуса транзакции
//        payment.setStatus(response.getIsValid() ? "VALIDATED" : "INVALID");
//
//        System.out.println("Результат валидации: " + response.getIsValid());
//
//        // Отправка результата в RabbitMQ
//        rabbitTemplate.convertAndSend(exchangeName, validationResultRoutingKey, payment);
//    }
//}
