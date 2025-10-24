package com.X.CarRental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;


@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "cars")
@NoArgsConstructor
public class car {
    @Id
    private Long id;
    private String vin;
    private int ownerId;
    private byte[] image;
    private String colour;
    private String description;
    private String type;
    private String license;
    private String pickUp;
    private String dropOff;
    private float rating;
    private float feePerHour;

    @Transient
    private String base64Image;

    public car (Long id, String vin, int ownerId, byte[] image, String colour, String description, String type, String license, String pickUp, String dropOff, float rating, float feePerHour) {
        this.id = id;
        this.vin = vin;
        this.ownerId = ownerId;
        this.image = image;
        this.colour = colour;
        this.description = description;
        this.type = type;
        this.license = license;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.rating = rating;
        this.feePerHour = feePerHour;
    }

    public car(int ownerId, String vin, String carType, String license, String description, String pickUp, String dropOff, String color, float feePerHour, byte[] photo){
        this.ownerId = ownerId;
        this.vin = vin;
        this.colour = color;
        this.description = description;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.license = license;
        this.type = carType;
        this.rating = 0;
        this.feePerHour = feePerHour;
        this.image = photo;
    }

    public String getBase64Image() {
        if (image == null) return "";
        return Base64.getEncoder().encodeToString(image);
    }
}
