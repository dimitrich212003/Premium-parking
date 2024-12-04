package com.parking.main.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity(name = "user_notifications")
public class UserNotifications extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false)
    private Notifications notification;

    @Column(nullable = false)
    private LocalDateTime notificationTime;

    public Users getUser() {
        return user;
    }

    public Notifications getNotification() {
        return notification;
    }

    public LocalDateTime getNotificationDate() {
        return notificationTime;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setNotification(Notifications notification) {
        this.notification = notification;
    }

    public void setNotificationDate(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }
}
