package org.example.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import java.util.UUID;
import org.example.enumClass.NotificationsType;


@Schema(description = "Ответ, представляющий уведомление")
public class NotificationsResponse extends RepresentationModel<NotificationsResponse> {

    @Schema(description = "Идентификатор уведомления", example = "a374ef12-3456-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "Тип уведомления", example = "REMINDER")
    private NotificationsType type;

    @Schema(description = "Текст сообщения", example = "Ваше бронирование начнется через час.")
    private String message;

    public NotificationsResponse(UUID id, NotificationsType type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public NotificationsResponse() {
    }

    public UUID getId() {
        return id;
    }

    public NotificationsType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setType(NotificationsType type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

