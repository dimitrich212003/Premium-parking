package com.parking.main.services.DTO;

import org.example.enumClass.NotificationsType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class NotificationsDTO extends RepresentationModel<BookingsDTO> {
    private NotificationsType type;
    private String message;
    private UUID id;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public NotificationsDTO() {

    }

    public NotificationsType getType() {
        return type;
    }

    @NotNull
    @NotEmpty(message = "Сообщение не может быть пустым!")
    public String getMessage() {
        return message;
    }

    public void setType(NotificationsType type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
