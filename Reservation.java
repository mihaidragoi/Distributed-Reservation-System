package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String reservationDate;

    private Long slotId;

    public Reservation() {
    }

    public Reservation(Long id, String customerName, String reservationDate) {
        this.id = id;
        this.customerName = customerName;
        this.reservationDate = reservationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", reservationDate='" + reservationDate + '\'' +
                '}';
    }

    public void setSlotId(Long id) {
        this.slotId =  id;
    }

    public Long getSlotId() {
        return slotId;
    }
}
