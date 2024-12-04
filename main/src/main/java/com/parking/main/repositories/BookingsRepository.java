package com.parking.main.repositories;


import com.parking.main.models.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, UUID> {

    List<Bookings> findAllByUserId(UUID userId);
}
