package com.X.CarRental.dto;

import com.X.CarRental.model.car;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class bookingRequestDTO {
    @Column(name = "buyerID")
    private Long buyerID;
    @Column(name = "carID")
    private Long carID;
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;
    private String pickup;
    private String dropOff;
    private boolean driver;
    @Transient
    private float totalPrice;

    public bookingRequestDTO(long buyerID, long carID, LocalDateTime pickUpTime, LocalDateTime dropOffTime, String status, String pickupLocation, String dropOffLocation, boolean driver) {
        this.buyerID = buyerID;
        this.carID = carID;
        this.start = pickUpTime;
        this.end = dropOffTime;
        this.status = status;
        this.pickup = pickupLocation;
        this.dropOff = dropOffLocation;
        this.driver = driver;
    }
}
