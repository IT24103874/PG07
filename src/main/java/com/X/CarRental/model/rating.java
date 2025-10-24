package com.X.CarRental.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class rating {
    private int id;
    private int bookingId;
    private int stars; // 1-5
    private String comment;
    private LocalDateTime createdAt;

    public rating(int id, int bookingId, int stars, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.stars = stars;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}


