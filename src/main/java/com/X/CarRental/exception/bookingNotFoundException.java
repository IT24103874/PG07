package com.X.CarRental.exception;

public class bookingNotFoundException extends RuntimeException {
    public bookingNotFoundException(String message) {
        super(message);
    }
}

