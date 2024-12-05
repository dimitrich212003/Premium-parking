package com.parking.grpcvalidationservice.service.grpc;

import com.parking.grpcvalidationservice.model.Payment;
import com.parking.grpcvalidationservice.service.ValidationService;
import com.parking.grpcvalidationservice.util.PaymentMapper;
import com.premium.grpcclient.PaymentCheck;
import com.premium.grpcclient.PaymentValidatorGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@GrpcService
@Service
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

        if (validationResult) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        PaymentCheck.ValidatePaymentResponse response = PaymentCheck.ValidatePaymentResponse
                .newBuilder()
                .setPayment(PaymentCheck.Payment.newBuilder()
                        .setId(payment.getId())
                        .setBooking(payment.getBooking())
                        .setAmount(payment.getAmount())
                        .setPaymentDate(payment.getPaymentDate())
                        .setStatus(payment.getStatus())
                        .build())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
