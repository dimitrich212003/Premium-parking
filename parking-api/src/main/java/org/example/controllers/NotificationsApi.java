package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dtos.request.NotificationsRequest;
import org.example.dtos.response.NotificationsResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.enumClass.NotificationsType;
import org.springframework.http.MediaType;



import java.util.UUID;

@Tag(name = "notifications", description = "API для управления уведомлениями")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface NotificationsApi {

    @Operation(summary = "Создать уведомление")
    @PostMapping(value = "/api/notification/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<NotificationsResponse>> createNotification(@Valid @RequestBody NotificationsRequest notificationsRequest);

    @Operation(summary = "Обновить уведомление")
    @PutMapping(value = "/api/notification/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<NotificationsResponse>> updateNotification(@PathVariable("id") UUID id, @Valid @RequestBody NotificationsRequest notificationsRequest);

    @Operation(summary = "Удалить уведомление")
    @DeleteMapping(value = "/api/notification/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteNotification(@PathVariable("id") UUID id);

    @Operation(summary = "Получить уведомление по ID")
    @GetMapping(value = "/api/notification/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<NotificationsResponse>> getNotificationById(@PathVariable("id") UUID id);

    @Operation(summary = "Получить уведомление по типу")
    @GetMapping(value = "/api/notifications/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<NotificationsResponse>> getNotificationByType(@PathVariable("type") NotificationsType type);
}

