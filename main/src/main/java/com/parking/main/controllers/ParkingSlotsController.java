package com.parking.main.controllers;

import com.parking.main.mappers.impl.ParkingSlotsReMapper;
import com.parking.main.services.DTO.ParkingSlotsDTO;
import com.parking.main.services.ParkingSlotsService;
import org.example.controllers.ParkingSlotsApi;
import org.example.dtos.request.ParkingSlotsRequest;
import org.example.dtos.response.ParkingSlotsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ParkingSlotsController implements ParkingSlotsApi {
    private ParkingSlotsService parkingSlotsService;
    private ParkingSlotsReMapper parkingSlotsReMapper;

    @Autowired
    public void setParkingSlotsService(ParkingSlotsService parkingSlotsService, ParkingSlotsReMapper parkingSlotsReMapper) {
        this.parkingSlotsService = parkingSlotsService;
        this.parkingSlotsReMapper = parkingSlotsReMapper;
    }

    @Override
    public ResponseEntity<EntityModel<ParkingSlotsResponse>> createParkingSlot(ParkingSlotsRequest parkingSlotsRequest) {
        ParkingSlotsDTO createdParkingSlot = parkingSlotsService.createParkingSlot(parkingSlotsReMapper.toDto(parkingSlotsRequest));
        ParkingSlotsResponse response = parkingSlotsReMapper.toResponse(createdParkingSlot);

        EntityModel<ParkingSlotsResponse> parkingModel = EntityModel.of(response,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(response.getId())).withRel("parkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(response.getNumber())).withRel("parkingSlotByNumber"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(response.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots"));

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingModel);
    }

    @Override
    public ResponseEntity<EntityModel<ParkingSlotsResponse>> updateParkingSlot(UUID id, ParkingSlotsRequest parkingSlotsRequest) {
        ParkingSlotsDTO updatedParkingSlot = parkingSlotsService.updateParkingSlot(id, parkingSlotsReMapper.toDto(parkingSlotsRequest));
        ParkingSlotsResponse response = parkingSlotsReMapper.toResponse(updatedParkingSlot);

        EntityModel<ParkingSlotsResponse> parkingSlotModel = EntityModel.of(response,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(response.getId())).withRel("parkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(response.getNumber())).withRel("parkingSlotByNumber"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(response.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots"));

        return ResponseEntity.ok(parkingSlotModel);
    }

    @Override
    public ResponseEntity<EntityModel<ParkingSlotsResponse>> getParkingSlotById(@PathVariable UUID id) {
        ParkingSlotsDTO parkingSlot = parkingSlotsService.getParkingSlotById(id);
        ParkingSlotsResponse response = parkingSlotsReMapper.toResponse(parkingSlot);

        EntityModel<ParkingSlotsResponse> parkingSlotModel = EntityModel.of(response,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(id)).withSelfRel(),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(parkingSlot.getNumber())).withRel("parkingSlotByNumber"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(parkingSlot.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots"));

        return ResponseEntity.ok(parkingSlotModel);
    }

    @Override
    public ResponseEntity<CollectionModel<ParkingSlotsResponse>> getParkingSlotsByUserPhone(String phone) {
        List<ParkingSlotsDTO> parkingSlots = parkingSlotsService.findParkingSlotsByUserPhone(phone);

        List<ParkingSlotsResponse> responses = parkingSlots.stream()
                .map(parkingSlotsReMapper::toResponse)
                .collect(Collectors.toList());

        responses.forEach(response -> {
            response.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(response.getId())).withRel("parkingSlotById"));
            response.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(response.getNumber())).withRel("parkingSlotByNumber"));
            response.add(linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(response.getId())).withRel("deleteParkingSlotById"));
        });

        Link selfLink = linkTo(methodOn(ParkingSlotsController.class).getParkingSlotsByUserPhone(phone)).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(responses, selfLink));
    }


    @Override
    public ResponseEntity<EntityModel<ParkingSlotsResponse>> getParkingSlotByNumber(@PathVariable short number) {
        ParkingSlotsDTO parkingSlot = parkingSlotsService.getParkingSlotByNumber(number);
        ParkingSlotsResponse response = parkingSlotsReMapper.toResponse(parkingSlot);

        EntityModel<ParkingSlotsResponse> parkingSlotModel = EntityModel.of(response,
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(number)).withSelfRel(),
                linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(parkingSlot.getId())).withRel("parkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(parkingSlot.getId())).withRel("deleteParkingSlotById"),
                linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withRel("freeParkingSlots"));

        return ResponseEntity.ok(parkingSlotModel);
    }

    @Override
    public ResponseEntity<CollectionModel<ParkingSlotsResponse>> getFreeParkingSlots() {
        List<ParkingSlotsDTO> freeParkingSlots = parkingSlotsService.getFreeParkingSlots();
        List<ParkingSlotsResponse> responses = freeParkingSlots.stream()
                .map(parkingSlotsReMapper::toResponse)
                .collect(Collectors.toList());

        responses.forEach(response -> {
            response.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotById(response.getId())).withRel("parkingSlotById"));
            response.add(linkTo(methodOn(ParkingSlotsController.class).getParkingSlotByNumber(response.getNumber())).withRel("parkingSlotByNumber"));
            response.add(linkTo(methodOn(ParkingSlotsController.class).deleteParkingSlot(response.getId())).withRel("deleteParkingSlotById"));
        });

        Link selfLink = linkTo(methodOn(ParkingSlotsController.class).getFreeParkingSlots()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(responses, selfLink));
    }

    @Override
    public ResponseEntity<Void> deleteParkingSlot(@PathVariable UUID id) {
        parkingSlotsService.deleteParkingSlot(id);
        return ResponseEntity.noContent().build();
    }
}

