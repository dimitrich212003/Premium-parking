package com.parking.grpcvalidationservice.service.impl;

import com.parking.grpcvalidationservice.model.Payment;
import com.parking.grpcvalidationservice.service.Validator;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ValidatorStubImpl implements Validator {

    @Override
    public Boolean validate(Payment payment) {
        boolean result = false;
        try {
            int delay = 1000 + new Random().nextInt(4000);
            Thread.sleep(delay);
            result = new Random().nextBoolean();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        return result;
    }
}
