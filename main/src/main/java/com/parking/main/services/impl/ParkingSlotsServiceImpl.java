package com.parking.main.services.impl;

import com.parking.main.mappers.impl.ParkingSlotsMapper;
import com.parking.main.models.ParkingSlots;
import com.parking.main.repositories.ParkingSlotsRepository;
import com.parking.main.services.DTO.ParkingSlotsDTO;
import com.parking.main.services.ParkingSlotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingSlotsServiceImpl implements ParkingSlotsService {

    private final ParkingSlotsRepository parkingSlotsRepository;

    private final ParkingSlotsMapper parkingSlotsMapper;

    @Autowired
    public ParkingSlotsServiceImpl(ParkingSlotsRepository parkingSlotsRepository, ParkingSlotsMapper parkingSlotsMapper) {
        this.parkingSlotsRepository = parkingSlotsRepository;
        this.parkingSlotsMapper = parkingSlotsMapper;
    }

    @Override
    public ParkingSlotsDTO createParkingSlot(ParkingSlotsDTO parkingSlotsDTO) {
        ParkingSlots parkingSlots = parkingSlotsMapper.toModel(parkingSlotsDTO);
        ParkingSlots createdParkingSlots = parkingSlotsRepository.saveAndFlush(parkingSlots);
        return parkingSlotsMapper.toDTO(createdParkingSlots);
    }

    @Override
    public ParkingSlotsDTO updateParkingSlot(UUID id, ParkingSlotsDTO parkingSlotsDTO) {
        ParkingSlots parkingSlots = parkingSlotsRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));

        parkingSlots.setNumber(parkingSlotsDTO.getNumber());

        ParkingSlots updateParkingSlots = parkingSlotsRepository.saveAndFlush(parkingSlots);
        return parkingSlotsMapper.toDTO(updateParkingSlots);
    }

    @Override
    public void deleteParkingSlot(UUID id) {
        parkingSlotsRepository.deleteById(id);
    }

    @Override
    public ParkingSlotsDTO getParkingSlotByNumber(short number) {
        ParkingSlots parkingSlot = parkingSlotsRepository.findByNumber(number).orElseThrow(() -> new RuntimeException("Parking slot not found"));
        return parkingSlotsMapper.toDTO(parkingSlot);
    }

    @Override
    public ParkingSlotsDTO getParkingSlotById(UUID id) {
        ParkingSlots parkingSlots = parkingSlotsRepository.findById(id).orElseThrow(() -> new RuntimeException("Parking slots not found"));
        return parkingSlotsMapper.toDTO(parkingSlots);
    }

    @Override
    public List<ParkingSlotsDTO> findParkingSlotsByUserPhone(String phone) {
        List<ParkingSlots> parkingSlots = parkingSlotsRepository.findParkingSlotsByUsersPhone(phone);
        return parkingSlots.stream()
                .map(parkingSlot -> parkingSlotsMapper.toDTO(parkingSlot))
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingSlotsDTO> getFreeParkingSlots() {
        List<ParkingSlots> parkingSlots = parkingSlotsRepository.findAll();
        return parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getBooking() == null)
                .map(parkingSlot -> parkingSlotsMapper.toDTO(parkingSlot))
                .collect(Collectors.toList());
    }
}
