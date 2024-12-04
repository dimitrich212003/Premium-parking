package com.parking.grpcvalidationservice.configuration;

import com.parking.grpcvalidationservice.service.ValidationService;
import com.parking.grpcvalidationservice.service.grpc.ValidationGrpcService;
import com.parking.grpcvalidationservice.util.PaymentMapper;
import io.grpc.ServerBuilder;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {
    private final ValidationService validationService;
    private final PaymentMapper paymentMapper;

    public GrpcServerConfig(ValidationService validationService, PaymentMapper paymentMapper) {
        this.validationService = validationService;
        this.paymentMapper = paymentMapper;
        System.out.println("GrpcServerConfig загружен.");
    }

    @Bean
    public GrpcServerConfigurer grpcServerConfigurer() {
        return serverBuilder -> {
            ServerBuilder
                    .forPort(9090)
                    .addService(new ValidationGrpcService(validationService, paymentMapper));
        };
    }

//    @Bean
//    public Server grpcServerConfigurer() {
//        Server server = ServerBuilder
//                .forPort(9090)
//                .addService(new ValidationGrpcService(validationService, paymentMapper))
//                .build();
//
//        // Асинхронный запуск сервера
//        new Thread(() -> {
//            try {
//                server.start();
//                System.out.println("GRPC сервер настроен на порт 9090");
//                server.awaitTermination();
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        return server;
//    }
}
