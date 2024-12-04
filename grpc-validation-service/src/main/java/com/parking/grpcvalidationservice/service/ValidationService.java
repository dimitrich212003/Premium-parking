package com.parking.grpcvalidationservice.service;

import com.parking.grpcvalidationservice.model.Payment;

public interface ValidationService {
    boolean validatePayment(Payment payment);
}
