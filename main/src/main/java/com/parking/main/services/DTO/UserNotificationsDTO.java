package com.parking.main.services.DTO;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserNotificationsDTO extends RepresentationModel<UserNotificationsDTO> {

    private UUID id;

    private UUID user;

    private UUID notification;

    private LocalDateTime notificationTime;

    public UserNotificationsDTO() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUser() {
        return user;
    }

    public UUID getNotification() {
        return notification;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public void setNotification(UUID notification) {
        this.notification = notification;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }


}
