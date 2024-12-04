package com.parking.grpcclient.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private UUID booking;
    private double amount;
    private String paymentDate;
    private String status;

    public Payment() {

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
        return "\nUUID аренды: " + getBooking() + "\nСтоимость аренды: " + getAmount() +
                "\nДата аренды: " + getPaymentDate() + "\nСтатус аренды: " + getStatus();
    }
}
