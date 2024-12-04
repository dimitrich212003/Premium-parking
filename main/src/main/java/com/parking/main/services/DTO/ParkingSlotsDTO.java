package com.parking.main.services.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class ParkingSlotsDTO extends RepresentationModel<BookingsDTO> {

    private UUID booking;
    private Short number;
    private UUID id;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBooking() {
        return booking;
    }

    @NotNull
    @NotEmpty(message = "Номер парковочного места не может быть пустым!")
    public Short getNumber() {
        return number;
    }

    public void setBooking(UUID booking) {
        this.booking = booking;
    }

    public void setNumber(Short number) {
        this.number = number;
    }
}
