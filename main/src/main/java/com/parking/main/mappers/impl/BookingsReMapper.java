package com.parking.main.mappers.impl;

import com.parking.main.mappers.ToDtoMapper;
import com.parking.main.mappers.ToResponseMapper;
import org.example.dtos.request.BookingsRequest;
import org.example.dtos.response.BookingsResponse;
import com.parking.main.services.DTO.BookingsDTO;
import org.springframework.stereotype.Component;

@Component
public class BookingsReMapper implements ToResponseMapper<BookingsResponse, BookingsDTO> , ToDtoMapper<BookingsRequest, BookingsDTO> {
    @Override
    public BookingsDTO toDto(BookingsRequest request) {
        BookingsDTO dto = new BookingsDTO();
        dto.setUser(request.user());
        dto.setParkingSlot(request.parkingSlot());
        dto.setStartTime(request.startTime());
        dto.setEndTime(request.endTime());
        return dto;
    }

    @Override
    public BookingsResponse toResponse(BookingsDTO dto) {
        BookingsResponse response = new BookingsResponse();
        response.setId(dto.getId());
        response.setUser(dto.getUser());
        response.setParkingSlot(dto.getParkingSlot());
        response.setStartTime(dto.getStartTime());
        response.setEndTime(dto.getEndTime());
        return null;
    }
}
