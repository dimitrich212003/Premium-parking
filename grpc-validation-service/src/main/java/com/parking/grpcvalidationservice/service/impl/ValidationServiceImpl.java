package com.parking.grpcvalidationservice.service.impl;

import com.parking.grpcvalidationservice.model.Payment;
import com.parking.grpcvalidationservice.service.ValidationService;
import com.parking.grpcvalidationservice.service.Validator;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    public ValidationServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean validatePayment(Payment payment) {
        boolean result = validator.validate(payment);
        return result;
    }
}

