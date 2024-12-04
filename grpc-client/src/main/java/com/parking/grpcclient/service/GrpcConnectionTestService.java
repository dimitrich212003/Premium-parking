package com.parking.grpcclient.service;

import com.premium.grpcclient.PaymentCheck;
import com.premium.grpcclient.PaymentValidatorGrpc;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class GrpcConnectionTestService {

    private final PaymentValidatorGrpc.PaymentValidatorBlockingStub stub;

    public GrpcConnectionTestService(PaymentValidatorGrpc.PaymentValidatorBlockingStub stub) {
        this.stub = stub;
    }

    public boolean testConnection() {
        try {
            // Попытка вызвать метод для проверки соединения
            PaymentCheck.ValidatePaymentRequest request = PaymentCheck.ValidatePaymentRequest.newBuilder()
                    .setPayment(PaymentCheck.Payment.newBuilder().setBooking("test").setAmount(0).setStatus("PENDING").build())
                    .build();

            PaymentCheck.ValidatePaymentResponse response = stub.validatePayment(request);
            // Если ответ получен, значит, соединение работает
            System.out.println("Соединение установлено, ответ от сервера: " + response);
            return true;
        } catch (StatusRuntimeException e) {
            // Если возникает ошибка, значит, соединение или метод не доступны
            System.err.println("Ошибка при подключении: " + e.getStatus() + " - " + e.getMessage());
            return false;
        }
    }
}

