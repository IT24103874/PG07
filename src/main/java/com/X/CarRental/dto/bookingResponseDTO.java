package com.X.CarRental.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class bookingResponseDTO {
    private Long id;

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
}
