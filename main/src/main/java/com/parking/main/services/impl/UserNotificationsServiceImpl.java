package com.parking.main.services.impl;

import com.parking.main.mappers.impl.UserNotificationMapper;
import com.parking.main.models.UserNotifications;
import com.parking.main.repositories.UserNotificationsRepository;
import com.parking.main.services.DTO.UserNotificationsDTO;
import com.parking.main.services.UserNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserNotificationsServiceImpl implements UserNotificationsService {

    private final UserNotificationsRepository userNotificationsRepository;
    private final UserNotificationMapper userNotificationMapper;

    @Autowired
    public UserNotificationsServiceImpl(UserNotificationsRepository userNotificationsRepository, UserNotificationMapper userNotificationMapper) {
        this.userNotificationsRepository = userNotificationsRepository;
        this.userNotificationMapper = userNotificationMapper;
    }

    @Override
    public UserNotificationsDTO createUserNotification(UserNotificationsDTO userNotificationsDTO) {
        UserNotifications userNotification = userNotificationMapper.toModel(userNotificationsDTO);
        userNotification.setNotificationDate(LocalDateTime.now());
        UserNotifications createUserNotifications = userNotificationsRepository.saveAndFlush(userNotification);
        return userNotificationMapper.toDTO(createUserNotifications);
    }

    @Override
    public UserNotificationsDTO updateUserNotification(UUID id, UserNotificationsDTO userNotificationsDTO) {
        UserNotifications userNotification = userNotificationsRepository.findById(id).orElseThrow(() -> new RuntimeException("UserNotification not found"));

        UserNotifications updateUserNotifications = userNotificationsRepository.saveAndFlush(userNotification);
        return userNotificationMapper.toDTO(updateUserNotifications);
    }

    @Override
    public void deleteUserNotification(UUID id) {
        userNotificationsRepository.deleteById(id);
    }
}
