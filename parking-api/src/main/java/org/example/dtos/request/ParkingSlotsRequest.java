package org.example.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ParkingSlotsRequest(
        @NotNull(message = "UUID бронирования обязателен.")
        UUID booking,

        @NotNull(message = "Номер парковочного места обязателен.")
        @Min(value = 1, message = "Номер парковочного места должен быть больше 0.")
        Short number
) {}

