package com.parking.grpcvalidationservice.configuration;

import com.parking.grpcvalidationservice.service.ValidationService;
import com.parking.grpcvalidationservice.service.grpc.ValidationGrpcService;
import com.parking.grpcvalidationservice.util.PaymentMapper;
import io.grpc.ServerBuilder;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {
    private final ValidationService validationService;
    private final PaymentMapper paymentMapper;

    @Autowired
    public GrpcServerConfig(ValidationService validationService, PaymentMapper paymentMapper) {
        this.validationService = validationService;
        this.paymentMapper = paymentMapper;
    }

    @Bean
    public GrpcServerConfigurer grpcServerConfigurer() {
        return serverBuilder -> {
            serverBuilder.addService(new ValidationGrpcService(validationService, paymentMapper));
        };
    }
}
