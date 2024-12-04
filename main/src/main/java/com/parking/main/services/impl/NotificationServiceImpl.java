package com.parking.main.services.impl;

import com.parking.main.models.Notifications;
import com.parking.main.models.NotificationsType;
import com.parking.main.repositories.NotificationsRepository;
import com.parking.main.services.DTO.NotificationsDTO;
import com.parking.main.services.NotificationsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationsService {

    private final NotificationsRepository notificationsRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public NotificationServiceImpl(NotificationsRepository notificationsRepository, ModelMapper modelMapper) {
        this.notificationsRepository = notificationsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationsDTO createNotification(NotificationsDTO notificationsDTO) {
        Notifications notification = modelMapper.map(notificationsDTO, Notifications.class);
        Notifications createdNotification = notificationsRepository.saveAndFlush(notification);
        return modelMapper.map(createdNotification, NotificationsDTO.class);
    }

    @Override
    public NotificationsDTO updateNotification(UUID id, NotificationsDTO notificationsDTO) {
        Notifications notification = notificationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Notifications not found"));

        notification.setMessage(notificationsDTO.getMessage());

        Notifications updateNotifications = notificationsRepository.saveAndFlush(notification);
        return modelMapper.map(updateNotifications, NotificationsDTO.class);
    }

    @Override
    public void deleteNotification(UUID id) {
        notificationsRepository.deleteById(id);
    }

    @Override
    public NotificationsDTO getNotificationById(UUID id) {
        Notifications notification = notificationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        return modelMapper.map(notification, NotificationsDTO.class);
    }

    @Override
    public NotificationsDTO getNotificationByType(NotificationsType type) {
        Notifications notification = notificationsRepository.findByType(type);
        return modelMapper.map(notification, NotificationsDTO.class);
    }


}
