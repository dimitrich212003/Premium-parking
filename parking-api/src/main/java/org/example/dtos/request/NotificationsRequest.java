package org.example.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.example.enumClass.NotificationsType;


public record NotificationsRequest(
        @NotNull(message = "Тип уведомления обязателен.")
        NotificationsType type,

        @NotEmpty(message = "Сообщение не может быть пустым.")
        @Size(max = 255, message = "Сообщение не может быть длиннее 255 символов.")
        String message
) {}

