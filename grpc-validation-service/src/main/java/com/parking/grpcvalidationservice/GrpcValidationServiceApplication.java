package com.parking.grpcvalidationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcValidationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcValidationServiceApplication.class, args);
        System.out.println("Приложение запущено.");
    }

}
