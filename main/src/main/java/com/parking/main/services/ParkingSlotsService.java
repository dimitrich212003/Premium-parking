package com.parking.main.services;

import com.parking.main.services.DTO.ParkingSlotsDTO;

import java.util.List;
import java.util.UUID;

public interface ParkingSlotsService {

    ParkingSlotsDTO createParkingSlot(ParkingSlotsDTO parkingSlotsDTO);

    ParkingSlotsDTO updateParkingSlot(UUID id, ParkingSlotsDTO parkingSlotsDTO);

    void deleteParkingSlot(UUID id);

    ParkingSlotsDTO getParkingSlotByNumber(short number);

    ParkingSlotsDTO getParkingSlotById(UUID id);

    List<ParkingSlotsDTO> findParkingSlotsByUserPhone(String phone);

    List<ParkingSlotsDTO> getFreeParkingSlots();
}
