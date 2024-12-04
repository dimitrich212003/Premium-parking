package com.parking.grpcvalidationservice.service;

import com.parking.grpcvalidationservice.model.Payment;

public interface Validator {
    Boolean validate(Payment payment);
}
