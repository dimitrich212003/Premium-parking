package com.parking.main.services;

import com.parking.main.models.Payment;
import com.parking.main.services.DTO.PaymentsDTO;

public interface RabbitMQService {
    void listen(PaymentsDTO payment);
}
