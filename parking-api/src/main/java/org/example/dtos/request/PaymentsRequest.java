package org.example.dtos.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record PaymentsRequest(
        @NotNull(message = "UUID бронирования обязателен.")
        UUID booking,

        @NotNull(message = "Сумма оплаты обязательна.")
        @Positive(message = "Сумма оплаты должна быть положительным числом.")
        double amount,

        @NotEmpty(message = "Дата оплаты обязательна.")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате YYYY-MM-DD.")
        String paymentDate,

        @NotEmpty(message = "Статус оплаты обязателен.")
        @Size(max = 50, message = "Статус оплаты не может быть длиннее 50 символов.")
        String status
) {}

