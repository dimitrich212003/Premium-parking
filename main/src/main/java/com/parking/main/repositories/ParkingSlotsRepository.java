package com.parking.main.repositories;

import com.parking.main.models.ParkingSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkingSlotsRepository extends JpaRepository<ParkingSlots, UUID> {


    // Список парковочных мест, которые занимал конкретный пользователь
    @Query("SELECT DISTINCT ps FROM parking_slots ps JOIN ps.booking b JOIN b.user u " +
            "WHERE u.phone = :userPhone")
    List<ParkingSlots> findParkingSlotsByUsersPhone(@Param("userPhone") String userPhone);

    Optional<ParkingSlots> findByNumber(Short number);
}
