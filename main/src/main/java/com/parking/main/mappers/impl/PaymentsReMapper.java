package com.parking.main.mappers.impl;

import com.parking.main.mappers.ToDtoMapper;
import com.parking.main.mappers.ToResponseMapper;
import com.parking.main.services.DTO.PaymentsDTO;
import org.example.dtos.request.PaymentsRequest;
import org.example.dtos.response.PaymentsResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentsReMapper implements ToResponseMapper<PaymentsResponse, PaymentsDTO>, ToDtoMapper<PaymentsRequest, PaymentsDTO> {
    @Override
    public PaymentsDTO toDto(PaymentsRequest request) {
        PaymentsDTO dto = new PaymentsDTO();
        dto.setBooking(request.booking());
        dto.setAmount(request.amount());
        dto.setPaymentDate(request.paymentDate());
        dto.setStatus(request.status());
        return dto;
    }

    @Override
    public PaymentsResponse toResponse(PaymentsDTO dto) {
        PaymentsResponse response = new PaymentsResponse();
        response.setId(dto.getId());
        response.setBooking(dto.getBooking());
        response.setAmount(dto.getAmount());
        response.setPaymentDate(dto.getPaymentDate());
        response.setStatus(dto.getStatus());
        return response;
    }
}

