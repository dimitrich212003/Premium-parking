package com.parking.main.controllers;

import com.parking.main.services.DTO.ParkingSlotsDTO;
import com.parking.main.services.ParkingSlotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ParkingSlotsController {
    private ParkingSlotsService parkingSlotsService;

    @Autowired
    public void setParkingSlotsService(ParkingSlotsService parkingSlotsService) {
        this.parkingSlotsService = parkingSlotsService;
    }

    @GetMapping("/api/parkingSlot/id/{id}")
    public ResponseEntity<EntityModel<ParkingSlotsDTO>> getParkingSlotById(@PathVariable UUID id) {
        ParkingSlotsDTO parkingSlot = parkingSlotsService.getParkingSlotById(id);

        EntityModel<ParkingSlotsDTO> parkingSlotModel = EntityModel.of(parkingSlot,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(id)).withSelfRel(),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(parkingSlot.getNumber())).withRel("parkingSlotByNumber"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(parkingSlot.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots")
                );

        return ResponseEntity.ok(parkingSlotModel);
    }

    @GetMapping("/api/parkingSlot/number/{number}")
    public ResponseEntity<EntityModel<ParkingSlotsDTO>> getParkingSlotByNumber(@PathVariable short number) {
        ParkingSlotsDTO parkingSlot = parkingSlotsService.getParkingSlotByNumber(number);

        EntityModel<ParkingSlotsDTO> parkingSlotModel = EntityModel.of(parkingSlot,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(number)).withSelfRel(),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(parkingSlot.getId())).withRel("parkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(parkingSlot.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots"));
        return ResponseEntity.ok(parkingSlotModel);
    }

    @GetMapping("/api/parkingSlots/phone/{phone}")
    public ResponseEntity<CollectionModel<ParkingSlotsDTO>> getParkingSlotsByUserPhone(@PathVariable String phone) {
        List<ParkingSlotsDTO> parkingSlots = parkingSlotsService.findParkingSlotsByUserPhone(phone);

        parkingSlots.forEach(parkingSlot -> {
            parkingSlot.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(parkingSlot.getNumber())).withRel("parkingSlotByNumber"));
            parkingSlot.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(parkingSlot.getId())).withRel("parkingSlotById"));
            parkingSlot.add(linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(parkingSlot.getId())).withRel("deleteParkingSlotById"));
            parkingSlot.add(linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots"));

        });

        Link selfLink = linkTo(methodOn(ParkingSlotsController.class).getParkingSlotsByUserPhone(phone)).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(parkingSlots, selfLink));
    }

    @GetMapping("/api/parkingSlots/free")
    public ResponseEntity<CollectionModel<ParkingSlotsDTO>> getFreeParkingSlots() {
        List<ParkingSlotsDTO> freeParkingSlots = parkingSlotsService.getFreeParkingSlots();

        freeParkingSlots.forEach(parkingSlot -> {
            parkingSlot.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(parkingSlot.getNumber())).withRel("parkingSlotByNumber"));
            parkingSlot.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(parkingSlot.getId())).withRel("parkingSlotById"));
            parkingSlot.add(linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(parkingSlot.getId())).withRel("deleteParkingSlotById"));
        });

        Link selfLink = linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(freeParkingSlots, selfLink));
    }

    @PostMapping("/api/parkingSlot/create")
    public ResponseEntity<EntityModel<ParkingSlotsDTO>> createParkingSlot(@RequestBody ParkingSlotsDTO parkingSlotsDTO) {
        ParkingSlotsDTO createParkingSlot = parkingSlotsService.createParkingSlot(parkingSlotsDTO);

        EntityModel<ParkingSlotsDTO> parkingModel = EntityModel.of(createParkingSlot,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(createParkingSlot.getId())).withRel("parkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(createParkingSlot.getNumber())).withRel("parkingSlotByNumber"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(createParkingSlot.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots"));

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingModel);
    }

    @PutMapping("/api/parkingSlot/update/{id}")
    public ResponseEntity<EntityModel<ParkingSlotsDTO>> updateParkingSlot(@PathVariable UUID id, @RequestBody ParkingSlotsDTO parkingSlotsDTO) {
        ParkingSlotsDTO updateParking = parkingSlotsService.updateParkingSlot(id, parkingSlotsDTO);

        EntityModel<ParkingSlotsDTO> ParkingSlotModel = EntityModel.of(updateParking,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(updateParking.getId())).withRel("parkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(updateParking.getNumber())).withRel("parkingSlotByNumber"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(updateParking.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots")
        );
        return ResponseEntity.ok(ParkingSlotModel);
    }

    @DeleteMapping("/api/parkingSlot/delete/{id}")
    public ResponseEntity<Void> deleteParkingSlot(@PathVariable UUID id) {
        parkingSlotsService.deleteParkingSlot(id);
        return ResponseEntity.noContent().build();
    }



}
