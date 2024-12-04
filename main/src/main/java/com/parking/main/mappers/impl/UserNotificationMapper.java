package com.parking.main.mappers.impl;

import com.parking.main.mappers.Mapper;
import com.parking.main.models.Notifications;
import com.parking.main.models.UserNotifications;
import com.parking.main.models.Users;
import com.parking.main.repositories.NotificationsRepository;
import com.parking.main.repositories.UsersRepository;
import com.parking.main.services.DTO.UserNotificationsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationMapper implements Mapper<UserNotifications, UserNotificationsDTO> {

    private final ModelMapper modelMapper;

    private final UsersRepository usersRepository;

    private final NotificationsRepository notificationsRepository;

    @Autowired
    public UserNotificationMapper(ModelMapper modelMapper, UsersRepository usersRepository, NotificationsRepository notificationsRepository) {
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @Override
    public UserNotifications toModel(UserNotificationsDTO dto) {
        UserNotifications userNotifications = modelMapper.map(dto, UserNotifications.class);
        if(dto.getUser() != null) {
            Users user = usersRepository.findById(dto.getUser()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));
            userNotifications.setUser(user);
        }

        if(dto.getNotification() != null) {
            Notifications notification = notificationsRepository.findById(dto.getNotification()).orElseThrow(() -> new IllegalArgumentException("Invalid notification Id"));
            userNotifications.setNotification(notification);
        }
        return userNotifications;
    }

    @Override
    public UserNotificationsDTO toDTO(UserNotifications userNotification) {
        UserNotificationsDTO dto = modelMapper.map(userNotification, UserNotificationsDTO.class);
        if(userNotification.getUser() != null) {
            dto.setUser(userNotification.getUser().getId());
        }

        if(userNotification.getNotification() != null) {
            dto.setNotification(userNotification.getNotification().getId());
        }
        return dto;
    }
}

