package com.parking.main.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity(name = "parking_slots")
public class ParkingSlots extends BaseEntity {
    @Column(nullable = false, unique = true)
    private Short number;

    //orphanRemoval = false  в  ParkingSlots  не удаляет  Bookings,  если он больше не связан  с  ParkingSlots.
    // (Чтобы смотреть список тех, кто пользовался конкретным местом)
    @OneToOne(mappedBy = "parkingSlot", cascade = CascadeType.REMOVE, orphanRemoval = false)
    private Bookings booking;

    public Short getNumber() {
        return number;
    }

    public Bookings getBooking() {
        return booking;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    public void setBooking(Bookings booking) {
        this.booking = booking;
    }
}
