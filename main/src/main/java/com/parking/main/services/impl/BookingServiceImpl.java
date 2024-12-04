package com.parking.main.services.impl;

import com.parking.main.mappers.impl.BookingMapper;
import com.parking.main.models.Bookings;
import com.parking.main.repositories.BookingsRepository;
import com.parking.main.repositories.UsersRepository;
import com.parking.main.services.BookingService;
import com.parking.main.services.DTO.BookingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingsRepository bookingsRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(BookingsRepository bookingsRepository, BookingMapper bookingMapper, UsersRepository usersRepository) {
        this.bookingsRepository = bookingsRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingsDTO createBooking(BookingsDTO bookingsDTO) {
        Bookings booking = bookingMapper.toModel(bookingsDTO);
        booking.setStartTime(LocalDateTime.now());
        Bookings createdBooking = bookingsRepository.saveAndFlush(booking);
        return bookingMapper.toDTO(createdBooking);
    }

    @Override
    public BookingsDTO updateBooking(UUID id, BookingsDTO bookingsDTO) {
        Bookings booking = bookingsRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setEndTime(bookingsDTO.getEndTime());

        Bookings updateBooking = bookingsRepository.saveAndFlush(booking);
        return bookingMapper.toDTO(updateBooking);
    }

    @Override
    public void deleteBooking(UUID id) {
        bookingsRepository.deleteById(id);
    }

    @Override
    public List<BookingsDTO> findAllByUserId(UUID userId) {
        List<Bookings> bookings = bookingsRepository.findAllByUserId(userId);
        return bookings.stream()
                .map(booking -> bookingMapper.toDTO(booking))
                .collect(Collectors.toList());
    }

    @Override
    public BookingsDTO findBookingById(UUID id) {
        Bookings booking = bookingsRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        return bookingMapper.toDTO(booking);
    }
}
