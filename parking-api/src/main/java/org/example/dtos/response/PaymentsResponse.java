package org.example.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Schema(description = "Ответ, представляющий платеж")
public class PaymentsResponse extends RepresentationModel<PaymentsResponse> {

    @Schema(description = "Идентификатор платежа", example = "f57fef12-49b1-4c4e-909e-1c2bf4567890")
    private UUID id;

    @Schema(description = "Идентификатор бронирования, связанного с платежом", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID booking;

    @Schema(description = "Сумма платежа", example = "49.99")
    private double amount;

    @Schema(description = "Дата платежа", example = "2024-12-19T12:34:56")
    private String paymentDate;

    @Schema(description = "Статус платежа", example = "COMPLETED")
    private String status;

    public PaymentsResponse(UUID id, UUID booking, double amount, String paymentDate, String status) {
        this.id = id;
        this.booking = booking;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public PaymentsResponse() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBooking(UUID booking) {
        this.booking = booking;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

