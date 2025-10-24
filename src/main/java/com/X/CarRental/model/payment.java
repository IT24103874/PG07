package com.X.CarRental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class payment {
    @Id
    int id;
    int from;
    String status;
    String reference;
    float amount;
    @Transient
    String sender;
    int bookingID;
}
