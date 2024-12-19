package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dtos.request.BookingsRequest;
import org.example.dtos.response.BookingsResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "bookings", description = "API для управления бронированиями")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface BookingApi {

    @Operation(summary = "Создать бронирование")
    @PostMapping(value = "/api/booking/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<BookingsResponse>> createBooking(@Valid @RequestBody BookingsRequest bookingsRequest);

    @Operation(summary = "Обновить бронирование")
    @PutMapping(value = "/api/booking/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<BookingsResponse>> updateBooking(@PathVariable("id") UUID id, @Valid @RequestBody BookingsRequest bookingsRequest);

    @Operation(summary = "Удалить бронирование")
    @DeleteMapping(value = "/api/booking/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteBooking(@PathVariable("id") UUID id);

    @Operation(summary = "Получить список бронирований пользователя")
    @GetMapping(value = "/api/bookings/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<BookingsResponse>> getBookingsByUserId(@PathVariable("userId") UUID userId);

    @Operation(summary = "Получить бронирование по ID")
    @GetMapping(value = "/api/booking/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<BookingsResponse>> getBookingById(@PathVariable("id") UUID id);
}

