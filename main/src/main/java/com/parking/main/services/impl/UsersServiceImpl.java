package com.parking.main.services.impl;

import com.parking.main.models.Users;
import com.parking.main.repositories.UsersRepository;
import com.parking.main.services.DTO.UsersDTO;
import com.parking.main.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        Users user = modelMapper.map(usersDTO, Users.class);
        user.setActive(true);
        Users createdUsers = usersRepository.saveAndFlush(user);
        return modelMapper.map(createdUsers, UsersDTO.class);
    }

    @Override
    public UsersDTO updateUser(UUID id, UsersDTO usersDTO) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(usersDTO.getFirstName());
        user.setLastName(usersDTO.getLastName());
        user.setPhone(usersDTO.getPhone());
        user.setPassword(usersDTO.getPassword());
        user.setActive(usersDTO.getIsActive());

        Users updateUser = usersRepository.saveAndFlush(user);
        return modelMapper.map(updateUser, UsersDTO.class);
    }

    @Override
    public void deleteUser(UUID id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDTO getUserByPhone(String phone) {
        Users user = usersRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UsersDTO.class);
    }

    @Override
    public UsersDTO getUserById(UUID id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UsersDTO.class);
    }

    @Override
    public List<UsersDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsersDTO> findAllByIsActiveTrue() {
        List <Users> users = usersRepository.findAllByIsActiveTrue();
        return users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsersDTO> findUsersWithBookingsBetweenDates(LocalDateTime startTime, LocalDateTime endTime) {
        List<Users> users = usersRepository.findUsersWithBookingsBetweenDates(startTime, endTime);
        return users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());
    }
}
