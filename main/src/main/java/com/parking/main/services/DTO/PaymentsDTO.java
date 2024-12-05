package com.parking.main.services.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.parking.main.mappers.StringToUUIDDeserializer;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class PaymentsDTO extends RepresentationModel<PaymentsDTO> {
    @JsonDeserialize(using = StringToUUIDDeserializer.class)
    private UUID id;
    @JsonDeserialize(using = StringToUUIDDeserializer.class)
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

    @Override
    public String toString() {
        return "\nUUID платежа: " + getId() + "\nUUID аренды: " + getBooking() + "\nСтоимость аренды: " + getAmount() +
                "\nДата аренды: " + getPaymentDate() + "\nСтатус аренды: " + getStatus();
    }
}
