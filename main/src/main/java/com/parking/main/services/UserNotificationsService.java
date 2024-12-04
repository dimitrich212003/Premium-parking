package com.parking.main.services;

import com.parking.main.services.DTO.UserNotificationsDTO;

import java.util.UUID;

public interface UserNotificationsService {
    UserNotificationsDTO createUserNotification(UserNotificationsDTO userNotificationsDTO);

    UserNotificationsDTO updateUserNotification(UUID id,UserNotificationsDTO userNotificationsDTO);

    void deleteUserNotification(UUID id);
}
