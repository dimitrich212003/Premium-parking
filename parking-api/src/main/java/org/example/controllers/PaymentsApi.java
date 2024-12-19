package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dtos.request.PaymentsRequest;
import org.example.dtos.response.PaymentsResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.UUID;

@Tag(name = "payments", description = "API для управления платежами")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface PaymentsApi {

    @Operation(summary = "Получить список всех платежей")
    @GetMapping(value = "/api/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<PaymentsResponse>> getAllPayments();

    @Operation(summary = "Получить платеж по ID")
    @GetMapping(value = "/api/payment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<PaymentsResponse>> getPaymentById(@PathVariable("id") UUID id);

    @Operation(summary = "Создать платеж")
    @PostMapping(value = "/api/payment/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<PaymentsResponse>> createPayment(@Valid @RequestBody PaymentsRequest paymentsRequest);

    @Operation(summary = "Обновить платеж")
    @PutMapping(value = "/api/payment/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<PaymentsResponse>> updatePayment(@PathVariable("id") UUID id, @Valid @RequestBody PaymentsRequest paymentsRequest);

    @Operation(summary = "Удалить платеж")
    @DeleteMapping(value = "/api/payment/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deletePayment(@PathVariable("id") UUID id);
}

