package com.X.CarRental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "booking")
public class booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyerID")
    private Long buyerID;
    @Column(name = "carID")
    private Long carID;       // Car being booked
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;
    private String pickup;
    private String dropOff;
    private boolean driver;
    @Transient
    private String type;
    @Transient
    private String description;
    @Transient
    private float totalPrice;
    @Transient
    private car car;
    @Transient
    private rating rating;

    public booking(Long buyerId, Long carId, LocalDateTime start, LocalDateTime end, String status) {
        this.buyerID = buyerId;
        this.carID = carId;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public booking(Long buyerId, Long carId, LocalDateTime start, LocalDateTime end, String status, String pickup, String dropOff, boolean driver) {
        this.buyerID = buyerId;
        this.carID = carId;
        this.start = start;
        this.end = end;
        this.status = status;
        this.pickup = pickup;
        this.dropOff = dropOff;
        this.driver = driver;
    }

    public booking() {

    }

    public void printDetails() {
        System.out.println("buyerID: " + buyerID + ", carID: " + carID + ", start: " + start + ", end: " + end + ", status: " + status + ", pickup: " + pickup + ", dropOff: " + dropOff + ", driver: " + driver + ", type: " + type + ", description: " + description + ", totalPrice: " + totalPrice);
    }
}
