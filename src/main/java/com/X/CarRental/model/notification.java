package com.X.CarRental.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class notification {
    private int id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private boolean completed;

    public notification(int id, String title, String message, LocalDateTime createdAt, boolean completed) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.completed = completed;
    }
}


