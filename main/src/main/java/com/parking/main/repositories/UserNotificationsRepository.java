package com.parking.main.repositories;

import com.parking.main.models.UserNotifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserNotificationsRepository extends JpaRepository<UserNotifications, UUID> {

}
