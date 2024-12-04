package com.parking.main.mappers.impl;

import com.parking.main.mappers.Mapper;
import com.parking.main.models.Bookings;
import com.parking.main.models.ParkingSlots;
import com.parking.main.models.Users;
import com.parking.main.repositories.ParkingSlotsRepository;
import com.parking.main.repositories.UsersRepository;
import com.parking.main.services.DTO.BookingsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper implements Mapper<Bookings, BookingsDTO> {


    private final ModelMapper modelMapper;

    private final UsersRepository usersRepository;

    private final ParkingSlotsRepository parkingSlotsRepository;

    @Autowired
    public BookingMapper(ModelMapper modelMapper, UsersRepository usersRepository, ParkingSlotsRepository parkingSlotsRepository) {
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository;
        this.parkingSlotsRepository = parkingSlotsRepository;
    }

    @Override
    public Bookings toModel(BookingsDTO dto) {
        Bookings booking = modelMapper.map(dto, Bookings.class);
        if(dto.getUser() != null && dto.getParkingSlot() != null) {
            Users user = usersRepository.findById(dto.getUser()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));
            booking.setUser(user);
            ParkingSlots parkingSlot = parkingSlotsRepository.findById(dto.getParkingSlot()).orElseThrow(() -> new IllegalArgumentException("Invalid parking slot Id"));
            booking.setParkingSlot(parkingSlot);
        }
        return booking;
    }

    @Override
    public BookingsDTO toDTO(Bookings booking) {
        BookingsDTO dto = modelMapper.map(booking, BookingsDTO.class);
        if(booking.getUser() != null && booking.getParkingSlot() != null) {
            dto.setUser(booking.getUser().getId());
            dto.setParkingSlot(booking.getParkingSlot().getId());
        }
        return dto;
    }
}
