package org.example.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsersRequest(
        @NotEmpty(message = "Имя пользователя обязательно.")
        @Size(min = 2, message = "Имя должно содержать минимум 2 символа.")
        String firstName,

        @NotEmpty(message = "Фамилия пользователя обязательна.")
        @Size(min = 2, message = "Фамилия должна содержать минимум 2 символа.")
        String lastName,

        @NotEmpty(message = "Номер телефона обязателен.")
        @Pattern(regexp = "\\d{11}", message = "Телефон должен содержать 11 цифр.")
        String phone,

        @NotEmpty(message = "Код отеля обязателен.")
        @Size(min = 5, max = 5, message = "Код отеля должен содержать 5 символов.")
        String hotelCode,

        @NotEmpty(message = "Пароль обязателен.")
        @Size(min = 5, message = "Пароль должен содержать минимум 5 символов.")
        String password,

        @NotNull(message = "Статус активности обязателен.")
        Boolean isActive
) {}

