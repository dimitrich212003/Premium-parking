package com.parking.grpcclient.config;

import com.premium.grpcclient.PaymentValidatorGrpc;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @GrpcClient("paymentValidator") // Имя клиента из настроек
    private Channel channel;

    @Bean
    public PaymentValidatorGrpc.PaymentValidatorBlockingStub paymentValidatorBlockingStub() {
        return PaymentValidatorGrpc.newBlockingStub(channel);
    }
}
