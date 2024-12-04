package com.parking.main.repositories;

import com.parking.main.models.Notifications;
import com.parking.main.models.NotificationsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, UUID> {

    Notifications findByType(NotificationsType type);
}
