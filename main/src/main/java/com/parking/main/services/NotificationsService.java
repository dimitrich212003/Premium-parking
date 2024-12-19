package com.parking.main.services;

import org.example.enumClass.NotificationsType;
import com.parking.main.services.DTO.NotificationsDTO;

import java.util.UUID;

public interface NotificationsService {

    NotificationsDTO createNotification(NotificationsDTO notificationsDTO);

    NotificationsDTO updateNotification(UUID id, NotificationsDTO notificationsDTO);

    void deleteNotification(UUID id);

    NotificationsDTO getNotificationById(UUID id);

    NotificationsDTO getNotificationByType(NotificationsType type);
}
