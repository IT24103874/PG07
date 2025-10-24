package com.X.CarRental.controller;

import com.X.CarRental.model.customer;
import com.X.CarRental.model.employee;
import com.X.CarRental.service.bookingService;
import com.X.CarRental.service.notificationService;
import com.X.CarRental.service.ratingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class notificationController {

    @Autowired
    notificationService notificationService;
    @Autowired
    ratingService ratingService;
    @Autowired
    private bookingService bookingService;

    @GetMapping("/notifications")
    public String activity(Model model, HttpSession session) {
        int id = 0;
        String type = "";
        if (session.getAttribute("customer") != null){
            customer customer = (customer) session.getAttribute("customer");
            id = customer.getId();
            type = "customer";
        } else if (session.getAttribute("employee") != null){
            employee employee = (employee) session.getAttribute("employee");
            id = employee.getEid();
            type = employee.getType();
        }
        model.addAttribute("notifications", notificationService.getNotifications(id, type));
        return "/notifications";
    }

    @PostMapping("/updateNotification")
    public String updateNotification(@RequestParam int id, @RequestParam String action_type){
        if (action_type.equals("complete")){
            notificationService.complete(id);
        } else {
            notificationService.delete(id);
        }
        return "redirect:/notifications";
    }

    @GetMapping("/rateBooking")
    public String rateBooking(@RequestParam long bookingId,
                              HttpSession session) {
        session.setAttribute("booking", bookingService.getBookingById(bookingId));
        return "/rateBooking";}

    @PostMapping("/submitRating")
    public String submitRating(@RequestParam String bookingId,
                               @RequestParam int stars,
                               @RequestParam String comment){
        ratingService.create(Integer.parseInt(bookingId), Math.max(1, Math.min(5, stars)), comment == null ? "" : comment.trim());
        return "redirect:/getFilteredBookings";
    }
}
