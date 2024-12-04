package com.parking.main.services.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingsDTO extends RepresentationModel<BookingsDTO> {

    private UUID user;
    private UUID parkingSlot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID id;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public BookingsDTO() {

    }

    public UUID getUser() {
        return user;
    }

    public UUID getParkingSlot() {
        return parkingSlot;
    }

    @NotNull
    @NotEmpty(message = "время начала парковки не может быть пустым!")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @NotNull
    @NotEmpty(message = "время завершения парковки не может быть пустым!")
    public LocalDateTime getEndTime() {
        return endTime;
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
