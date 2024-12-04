package com.parking.main.mappers.impl;

import com.parking.main.mappers.Mapper;
import com.parking.main.models.Bookings;
import com.parking.main.models.ParkingSlots;
import com.parking.main.repositories.BookingsRepository;
import com.parking.main.services.DTO.ParkingSlotsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingSlotsMapper implements Mapper<ParkingSlots, ParkingSlotsDTO> {

    private final ModelMapper modelMapper;

    private final BookingsRepository bookingsRepository;

    @Autowired
    public ParkingSlotsMapper(ModelMapper modelMapper, BookingsRepository bookingsRepository) {
        this.modelMapper = modelMapper;
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public ParkingSlots toModel(ParkingSlotsDTO dto) {
        ParkingSlots parkingSlot = modelMapper.map(dto, ParkingSlots.class);
        if(dto.getBooking() != null) {
            Bookings booking = bookingsRepository.findById(dto.getBooking()).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id"));
            parkingSlot.setBooking(booking);
        }
        return parkingSlot;
    }

    @Override
    public ParkingSlotsDTO toDTO(ParkingSlots parkingSlots) {
        ParkingSlotsDTO dto = modelMapper.map(parkingSlots, ParkingSlotsDTO.class);
        if(parkingSlots.getBooking() != null) {
            dto.setBooking(parkingSlots.getBooking().getId());
        }
        return dto;
    }
}
