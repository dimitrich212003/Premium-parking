package org.example.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Schema(description = "Ответ, представляющий парковочное место")
public class ParkingSlotsResponse extends RepresentationModel<ParkingSlotsResponse> {

    @Schema(description = "Идентификатор парковочного места", example = "4b60f5df-8b60-4c6a-ae88-e841587b89bc")
    private UUID id;

    @Schema(description = "Идентификатор бронирования, связанного с этим местом", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID booking;

    @Schema(description = "Номер парковочного места", example = "42")
    private Short number;

    public ParkingSlotsResponse(UUID id, UUID booking, Short number) {
        this.id = id;
        this.booking = booking;
        this.number = number;
    }

    public ParkingSlotsResponse() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getBooking() {
        return booking;
    }

    public Short getNumber() {
        return number;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBooking(UUID booking) {
        this.booking = booking;
    }

    public void setNumber(Short number) {
        this.number = number;
    }
}

