package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dtos.request.UsersRequest;
import org.example.dtos.response.UsersResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.UUID;

@Tag(name = "users", description = "API для управления пользователями")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface UsersApi {

    @Operation(summary = "Создать пользователя")
    @PostMapping(value = "/api/user/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<UsersResponse>> createUser(@Valid @RequestBody UsersRequest usersRequest);

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping(value = "/api/user/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<UsersResponse>> updateUser(@PathVariable("id") UUID id, @Valid @RequestBody UsersRequest usersRequest);

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping(value = "/api/user/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id);

    @Operation(summary = "Получить пользователя по номеру телефона")
    @GetMapping(value = "/api/user/phone/{phone}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<UsersResponse>> getUserByPhone(@PathVariable("phone") String phone);

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping(value = "/api/user/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<UsersResponse>> getUserById(@PathVariable("id") UUID id);

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<UsersResponse>> getAllUsers();

    @Operation(summary = "Получить список активных пользователей")
    @GetMapping(value = "/api/users/active", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<UsersResponse>> findAllByIsActiveTrue();

    ResponseEntity<CollectionModel<UsersResponse>> getAllUsersByIsActiveTrue();

    @Operation(summary = "Получить пользователей с бронированиями за указанный период")
    @GetMapping(value = "/api/users/bookingsBetween/{startTime}/{endTime}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<UsersResponse>> getAllUsersWithBookingsBetweenDates(
            @PathVariable("startTime") String startTimeStr,
            @PathVariable("endTime") String endTimeStr);
}

