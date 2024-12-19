package com.parking.main.mappers.impl;

import com.parking.main.mappers.ToDtoMapper;
import com.parking.main.mappers.ToResponseMapper;
import org.example.dtos.request.NotificationsRequest;
import org.example.dtos.response.NotificationsResponse;
import com.parking.main.services.DTO.NotificationsDTO;
import org.springframework.stereotype.Component;

@Component
public class NotificationsReMapper implements ToResponseMapper<NotificationsResponse, NotificationsDTO>, ToDtoMapper<NotificationsRequest, NotificationsDTO> {

    @Override
    public NotificationsDTO toDto(NotificationsRequest request) {
        NotificationsDTO dto = new NotificationsDTO();
        dto.setType(request.type());
        dto.setMessage(request.message());
        return dto;
    }

    @Override
    public NotificationsResponse toResponse(NotificationsDTO dto) {
        NotificationsResponse response = new NotificationsResponse();
        response.setId(dto.getId());
        response.setType(dto.getType());
        response.setMessage(dto.getMessage());
        return response;
    }
}

