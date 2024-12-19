package com.parking.main.mappers.impl;

import com.parking.main.mappers.ToDtoMapper;
import com.parking.main.mappers.ToResponseMapper;
import com.parking.main.services.DTO.UsersDTO;
import org.example.dtos.request.UsersRequest;
import org.example.dtos.response.UsersResponse;
import org.springframework.stereotype.Component;

@Component
public class UsersReMapper implements ToResponseMapper<UsersResponse, UsersDTO>, ToDtoMapper<UsersRequest, UsersDTO> {
    @Override
    public UsersDTO toDto(UsersRequest request) {
        UsersDTO dto = new UsersDTO();
        dto.setFirstName(request.firstName());
        dto.setLastName(request.lastName());
        dto.setPhone(request.phone());
        dto.setHotelCode(request.hotelCode());
        dto.setPassword(request.password());
        dto.setActive(request.isActive());
        return dto;
    }

    @Override
    public UsersResponse toResponse(UsersDTO dto) {
        UsersResponse response = new UsersResponse();
        response.setId(dto.getId());
        response.setFirstName(dto.getFirstName());
        response.setLastName(dto.getLastName());
        response.setPhone(dto.getPhone());
        response.setHotelCode(dto.getHotelCode());
        response.setActive(dto.getIsActive());
        return response;
    }
}

