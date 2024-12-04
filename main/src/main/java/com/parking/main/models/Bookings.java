package com.parking.main.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "bookings")
public class Bookings extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parking_slot_id", nullable = false)
    private ParkingSlots parkingSlot;

    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    public Users getUser() {
        return user;
    }

    public ParkingSlots getParkingSlot() {
        return parkingSlot;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setParkingSlot(ParkingSlots parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
