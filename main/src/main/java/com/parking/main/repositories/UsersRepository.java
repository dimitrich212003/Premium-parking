package com.parking.main.repositories;

import com.parking.main.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByPhone(String phone);

    List<Users> findAllByIsActiveTrue();

    // Список пользователей, которые занимали конкретное парковочное место
    @Query("SELECT DISTINCT u FROM users u JOIN u.bookings b JOIN b.parkingSlot ps " +
            "WHERE ps.number = :parkingSlotNumber")
    List<Users> findUsersByParkingSlotNumber(@Param("parkingSlotNumber") Short number);

    @Query("SELECT DISTINCT u FROM users u JOIN u.bookings b " +
            "WHERE b.startTime <= :endTime AND b.endTime >= :startTime")
    List<Users> findUsersWithBookingsBetweenDates(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
