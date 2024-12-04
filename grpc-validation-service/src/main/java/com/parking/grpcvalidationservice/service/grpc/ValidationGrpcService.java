package com.parking.grpcvalidationservice.service.grpc;

import com.parking.grpcvalidationservice.model.Payment;
import com.parking.grpcvalidationservice.service.ValidationService;
import com.parking.grpcvalidationservice.util.PaymentMapper;
import com.premium.grpcvalidationservice.PaymentCheck;
import com.premium.grpcvalidationservice.PaymentValidatorGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@GrpcService
public class ValidationGrpcService extends PaymentValidatorGrpc.PaymentValidatorImplBase {
    private final ValidationService validationService;
    private final PaymentMapper paymentMapper;

    public ValidationGrpcService(ValidationService validationService, PaymentMapper paymentMapper) {
        this.validationService = validationService;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public void validatePayment(PaymentCheck.ValidatePaymentRequest request, StreamObserver<PaymentCheck.ValidatePaymentResponse> responseObserver) {
        Payment payment = paymentMapper.toPayment(request.getPayment());
        boolean validationResult = validationService.validatePayment(payment);

        if(validationResult) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        PaymentCheck.ValidatePaymentResponse response = PaymentCheck.ValidatePaymentResponse
                .newBuilder()
                .setPayment(request.getPayment())
                .build();
        responseObserver.onNext(response); // отправляем ответ клиенту
        responseObserver.onCompleted(); // Закрываем поток после отправки ответа
    }
}
