package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dtos.request.ParkingSlotsRequest;
import org.example.dtos.response.ParkingSlotsResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.UUID;

@Tag(name = "parking-slots", description = "API для управления парковочными местами")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface ParkingSlotsApi {

    @Operation(summary = "Создать парковочное место")
    @PostMapping(value = "/api/parkingSlot/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<ParkingSlotsResponse>> createParkingSlot(@Valid @RequestBody ParkingSlotsRequest parkingSlotsRequest);

    @Operation(summary = "Обновить парковочное место")
    @PutMapping(value = "/api/parkingSlot/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<ParkingSlotsResponse>> updateParkingSlot(@PathVariable("id") UUID id, @Valid @RequestBody ParkingSlotsRequest parkingSlotsRequest);

    @Operation(summary = "Удалить парковочное место")
    @DeleteMapping(value = "/api/parkingSlot/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteParkingSlot(@PathVariable("id") UUID id);

    @Operation(summary = "Получить парковочное место по номеру")
    @GetMapping(value = "/api/parkingSlot/number/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<ParkingSlotsResponse>> getParkingSlotByNumber(@PathVariable("number") short number);

    @Operation(summary = "Получить парковочное место по ID")
    @GetMapping(value = "/api/parkingSlot/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<ParkingSlotsResponse>> getParkingSlotById(@PathVariable("id") UUID id);

    @Operation(summary = "Получить список парковочных мест по номеру телефона пользователя")
    @GetMapping(value = "/api/parkingSlots/phone/{phone}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<ParkingSlotsResponse>> getParkingSlotsByUserPhone(@PathVariable("phone") String phone);

    @Operation(summary = "Получить список свободных парковочных мест")
    @GetMapping(value = "/api/parkingSlots/free", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<ParkingSlotsResponse>> getFreeParkingSlots();
}

