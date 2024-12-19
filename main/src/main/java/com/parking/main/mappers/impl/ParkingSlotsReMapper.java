package com.parking.main.mappers.impl;

import com.parking.main.mappers.ToDtoMapper;
import com.parking.main.mappers.ToResponseMapper;
import com.parking.main.services.DTO.ParkingSlotsDTO;
import org.example.dtos.request.ParkingSlotsRequest;
import org.example.dtos.response.ParkingSlotsResponse;
import org.springframework.stereotype.Component;

@Component
public class ParkingSlotsReMapper implements ToResponseMapper<ParkingSlotsResponse, ParkingSlotsDTO>, ToDtoMapper<ParkingSlotsRequest, ParkingSlotsDTO> {
    @Override
    public ParkingSlotsDTO toDto(ParkingSlotsRequest request) {
        ParkingSlotsDTO dto = new ParkingSlotsDTO();
        dto.setNumber(request.number());
        dto.setBooking(request.booking());
        return dto;
    }

    @Override
    public ParkingSlotsResponse toResponse(ParkingSlotsDTO dto) {
        ParkingSlotsResponse response = new ParkingSlotsResponse();
        response.setId(dto.getId());
        response.setNumber(dto.getNumber());
        response.setBooking(dto.getBooking());
        return response;
    }
}

