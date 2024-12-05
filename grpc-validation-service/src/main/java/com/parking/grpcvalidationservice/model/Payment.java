package com.parking.grpcvalidationservice.model;


public class Payment {
    private String id;
    private String booking;
    private double amount;
    private String paymentDate;
    private String status;

    public Payment() {

    }

    public String getBooking() {
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

    public String getId() {
        return id;
    }

    public void setBooking(String booking) {
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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nUUID аренды: " + getBooking() + "\nСтоимость аренды: " + getAmount() +
                "\nДата аренды: " + getPaymentDate() + "\nСтатус аренды: " + getStatus();
    }
}
