package com.parking.main.services;

import com.parking.main.services.DTO.UsersDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UsersService {

    UsersDTO createUser(UsersDTO usersDTO);

    UsersDTO updateUser(UUID id, UsersDTO usersDTO);

    void deleteUser(UUID id);

    UsersDTO getUserByPhone(String phone);

    UsersDTO getUserById(UUID id);

    List<UsersDTO> getAllUsers();

    List<UsersDTO> findAllByIsActiveTrue();

    List<UsersDTO> findUsersWithBookingsBetweenDates(LocalDateTime startTime, LocalDateTime endTime);
}
