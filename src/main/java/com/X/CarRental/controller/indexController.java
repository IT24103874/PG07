package com.X.CarRental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.X.CarRental.service.ratingService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class indexController {

    @Autowired
    private ratingService ratingService;

    @GetMapping("/")
    public String LogInGetMapping(Model model) {
        model.addAttribute("type", "customer");
        return ("/logIn.html");
    }
}
