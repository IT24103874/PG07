package com.X.CarRental.service;



import com.X.CarRental.dto.bookingRequestDTO;
import com.X.CarRental.dto.bookingResponseDTO;
import com.X.CarRental.model.booking;

import java.util.List;

public interface bookingService{
    booking createBooking(bookingRequestDTO requestDTO);
    bookingResponseDTO getBookingById(Long id);
    List<booking> getAllBookings();
    List<booking> getBookingsByOwnerId(Long ownerId);
    bookingResponseDTO updateBooking(Long id, bookingRequestDTO requestDTO);
    void deleteBooking(Long id);
}
