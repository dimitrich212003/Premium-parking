package com.parking.main.services;

import com.parking.main.services.DTO.BookingsDTO;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingsDTO createBooking(BookingsDTO bookingsDTO);

    BookingsDTO updateBooking(UUID id, BookingsDTO bookingsDTO);

    void deleteBooking(UUID id);

    List<BookingsDTO> findAllByUserId(UUID userId);

    BookingsDTO findBookingById(UUID id);
}
