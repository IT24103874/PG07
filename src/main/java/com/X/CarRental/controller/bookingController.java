package com.X.CarRental.controller;

import com.X.CarRental.dto.bookingRequestDTO;
import com.X.CarRental.dto.bookingResponseDTO;
import com.X.CarRental.model.booking;
import com.X.CarRental.model.car;
import com.X.CarRental.model.customer;
import com.X.CarRental.model.employee;
import com.X.CarRental.service.bookingService;
import com.X.CarRental.service.carService;
import com.X.CarRental.service.notificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.Notification;


@Controller
public class bookingController {

    private final bookingService bookingService;
    private final carService carService;
    private final notificationService notificationService;

    public bookingController(bookingService bookingService, carService carService, notificationService notificationService) {
        this.bookingService = bookingService;
        this.carService = carService;
        this.notificationService = notificationService;
    }

    @PostMapping("/createBooking")
    public String createBooking(bookingRequestDTO bookingRequestDTO,
                                @RequestParam String carId,
                                HttpSession session,
                                Model model) {
        customer customer = (customer) session.getAttribute("customer");
        car car = carService.GetCarById(Integer.parseInt(carId));
        booking booking = bookingService.createBooking(bookingRequestDTO);
        booking.setCar(carService.GetCarById(Integer.parseInt(carId)));
        session.setAttribute("booking", booking);
        model.addAttribute("car", car);
        notificationService.create("Booking created", "Booking for " + booking.getCar().getType() + " from " + booking.getStart() + " to " + booking.getEnd() + "(pick up at " + booking.getPickup() + ", drop off at " + booking.getDropOff() + ") Confirmed.", "customer",1, Math.toIntExact(booking.getBuyerID()));
        System.out.println("createBooking completed.");
        return "/bookingPayment";
    }

    @GetMapping("/createPayment")
    public String creatPayment(Model model,
                               HttpSession session) {
        return "redirect:/getFilteredBookings";
    }

    @GetMapping("/{id}")
    public bookingResponseDTO getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/getAllBookings")
    public String getAllBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "/bookingsUser";
    }

    @GetMapping("/getFilteredBookings")
    public String getFilteredBookings(Model model,
                                      HttpSession session) {
        long Id;
        if (session.getAttribute("customer") != null) {
            customer customer = (customer) session.getAttribute("customer");
            Id = customer.getId();
        } else {
            employee employee = (employee) session.getAttribute("employee");
            Id = employee.getEid();
        }
        model.addAttribute("bookings", bookingService.getBookingsByOwnerId(Id));
        return "/bookingsUser";
    }

    @PutMapping("/{id}")
    public bookingResponseDTO updateBooking(@PathVariable Long id, @RequestBody bookingRequestDTO requestDTO) {
        return bookingService.updateBooking(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking deleted successfully.";
    }

    @GetMapping("/bookCar")
    public String bookCar(@RequestParam long carId,
                          Model model) {
        model.addAttribute("car", carService.GetCarById(carId));
        return "/bookingPreferences";
    }

    @GetMapping("bookingComplete")
    public String bookingComplete(Model model,
                                  HttpSession session) {
        return "/bookingCompletion"; }
}

