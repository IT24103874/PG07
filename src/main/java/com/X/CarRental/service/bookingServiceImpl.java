package com.X.CarRental.service;

import com.X.CarRental.dto.bookingRequestDTO;
import com.X.CarRental.dto.bookingResponseDTO;
import com.X.CarRental.exception.bookingNotFoundException;
import com.X.CarRental.model.booking;
import com.X.CarRental.model.car;
import com.X.CarRental.model.rating;
import com.X.CarRental.repo.bookingRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class bookingServiceImpl implements bookingService {

    private final bookingRepository bookingRepository;
    private final carService carService;
    private final ratingService ratingService;

    public bookingServiceImpl(bookingRepository bookingRepository, carService carService, ratingService ratingService) {
        this.bookingRepository = bookingRepository;
        this.carService = carService;
        this.ratingService = ratingService;
    }

    @Override
    public booking createBooking(bookingRequestDTO requestDTO) {
        booking booking = new booking(
                requestDTO.getBuyerID(),
                requestDTO.getCarID(),
                requestDTO.getStart(),
                requestDTO.getEnd(),
                requestDTO.getStatus(),
                requestDTO.getPickup(),
                requestDTO.getDropOff(),
                requestDTO.isDriver()
        );
        booking saved = bookingRepository.save(booking);
        mapToResponse(saved);
        return booking;
    }

    @Override
    public bookingResponseDTO getBookingById(Long id) {
        booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new bookingNotFoundException("Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    @Override
    public List<booking> getAllBookings() {
        List<bookingResponseDTO> list = bookingRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
        List<booking> bookings = new ArrayList<>();
        for (bookingResponseDTO bookingResponseDTO : list) {
            booking booking = new booking();
            booking.setId(bookingResponseDTO.getId());
            booking.setBuyerID(bookingResponseDTO.getBuyerID());
            booking.setCarID(bookingResponseDTO.getCarID());
            booking.setStart(bookingResponseDTO.getStart());
            booking.setEnd(bookingResponseDTO.getEnd());
            booking.setStatus(bookingResponseDTO.getStatus());
            booking.setPickup(bookingResponseDTO.getPickup());
            booking.setDropOff(bookingResponseDTO.getDropOff());
            booking.setDriver(bookingResponseDTO.isDriver());
            car car = carService.GetCarById(bookingResponseDTO.getCarID());
            booking.setType(car.getType());
            booking.setDescription(car.getDescription());
            float amount = Duration.between(booking.getStart(), booking.getEnd()).toMinutes() * car.getFeePerHour() / 60;
            booking.setTotalPrice(amount);
            booking.setCar(car);
            rating rating = ratingService.getByBooking(Math.toIntExact(bookingResponseDTO.getId()));
            booking.setRating(rating);
            booking.printDetails();
            bookings.add(booking);
        }
        return bookings;
    }

    @Override
    public List<booking> getBookingsByOwnerId(Long ownerId) {
        List<booking> bookings = getAllBookings();
        bookings.removeIf(booking -> !Objects.equals(booking.getBuyerID(), ownerId));
        return bookings;
    }

    @Override
    public bookingResponseDTO updateBooking(Long id, bookingRequestDTO requestDTO) {
        booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new bookingNotFoundException("Booking not found with id: " + id));

        booking.setStart(requestDTO.getStart());
        booking.setEnd(requestDTO.getEnd());
        booking.setStatus(requestDTO.getStatus());

        booking updated = bookingRepository.save(booking);
        return mapToResponse(updated);
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new bookingNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    private bookingResponseDTO mapToResponse(booking booking) {
        bookingResponseDTO dto = new bookingResponseDTO();
        dto.setId(booking.getId());
        dto.setBuyerID(booking.getBuyerID());
        dto.setCarID(booking.getCarID());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setStatus(booking.getStatus());

        dto.setPickup(booking.getPickup());
        dto.setDropOff(booking.getDropOff());
        dto.setDriver(booking.isDriver());

        return dto;
    }

}

