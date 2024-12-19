package org.example.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingsRequest(
        @NotNull(message = "Пользователь обязателен.")
        UUID user,

        @NotNull(message = "Парковочное место обязательно.")
        UUID parkingSlot,

        @NotNull(message = "Время начала парковки обязательно.")
        LocalDateTime startTime,

        @NotNull(message = "Время завершения парковки обязательно.")
        LocalDateTime endTime
) {}
