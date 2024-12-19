package org.example.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Ответ, представляющий бронирование")
public class BookingsResponse extends RepresentationModel<BookingsResponse> {

    @Schema(description = "Идентификатор бронирования", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID id;

    @Schema(description = "Идентификатор пользователя, сделавшего бронирование", example = "c1a6f0ff-79bf-4996-8728-8f73f9116e1d")
    private UUID user;

    @Schema(description = "Идентификатор парковочного места", example = "9a87f120-81b0-4ad1-a10d-91a2ab45d20c")
    private UUID parkingSlot;

    @Schema(description = "Дата и время начала бронирования", example = "2024-12-19T10:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Дата и время окончания бронирования", example = "2024-12-19T14:00:00")
    private LocalDateTime endTime;

    public BookingsResponse(UUID id, UUID user, UUID parkingSlot, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.user = user;
        this.parkingSlot = parkingSlot;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BookingsResponse() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getUser() {
        return user;
    }

    public UUID getParkingSlot() {
        return parkingSlot;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public void setParkingSlot(UUID parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}

//TODO добавить аннотации для схеммы swagger
