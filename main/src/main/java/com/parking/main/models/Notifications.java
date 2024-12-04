package com.parking.main.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity(name = "notifications")
public class Notifications extends BaseEntity{

    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<UserNotifications> userNotifications;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private NotificationsType type;

    @Column(nullable = false)
    private String message;

    public NotificationsType getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }
    public List<UserNotifications> getUserNotifications() {
        return userNotifications;
    }
    public void setType(NotificationsType type) {
        this.type = type;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setUserNotifications(List<UserNotifications> userNotifications) {
        this.userNotifications = userNotifications;
    }
}
