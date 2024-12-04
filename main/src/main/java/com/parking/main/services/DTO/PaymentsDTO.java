package com.parking.main.services.DTO;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentsDTO extends RepresentationModel<PaymentsDTO> {
    private UUID id;
    private UUID booking;
    private double amount;
    private String paymentDate;
    private String status;

    public PaymentsDTO() {

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
