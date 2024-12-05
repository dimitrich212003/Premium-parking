package com.parking.main.services;

import com.parking.main.services.DTO.PaymentsDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<PaymentsDTO> getAllPayments();
    PaymentsDTO getPaymentById(UUID id);
    PaymentsDTO createPayment(PaymentsDTO paymentsDTO);

    PaymentsDTO updatePayment(UUID id, PaymentsDTO paymentsDTO);

    void deletePayment(UUID id);
}
