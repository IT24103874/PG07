package com.X.CarRental.controller;

import com.X.CarRental.model.car;
import com.X.CarRental.service.carService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class carController {

    @Autowired
    private final carService carService;

    public carController(carService carService) {
        this.carService = carService;
    }

    @GetMapping("/viewRegisterCar")
    public String viewRegisterCar(){
        return "/registerCar";
    }

    @PostMapping(path = "/registerCar")
    public String registerCar(
            @RequestParam int ownerId,
            @RequestParam(required = false) String carType,
            @RequestParam(required = false) String vin,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String colour,
            @RequestParam(required = false) String pickUp,
            @RequestParam(required = false) String dropOff,
            @RequestParam(required = false) Float feePerHour,
            @RequestParam(required = false) String license,
            @RequestPart(required = false) MultipartFile photo
    ) throws IOException {
        System.out.println("Info : " + carType + " " + vin + " " + description + " " + colour + " " + pickUp + " " + dropOff + " " + license + " " + photo);
        if (carType == null || vin == null || description == null || colour == null || feePerHour == null || license == null || photo == null) {
            return "redirect:/viewRegisterCar";
        }
        car createdCar = new car(ownerId, vin, carType, license, description, pickUp, dropOff, colour, feePerHour, photo.getBytes());
        carService.createCar(createdCar);
        return "redirect:/customerProfile";
    }

    @GetMapping("getAvailableCars")
    public String getAvailableCars(Model model,
                                   @RequestParam LocalDateTime startTime,
                                   @RequestParam LocalDateTime endTime){
        ArrayList<car> cars = carService.getAvailableCars(startTime, endTime);
        model.addAttribute("cars", cars);
        return "/userDashboard";
    }

    @GetMapping("/editCar")
    public String editCar(@RequestParam int carId, Model model){
        model.addAttribute("car", carService.GetCarById(carId));
        return "/editCar";
    }

    @PutMapping("/updateCar")
    public ResponseEntity<String> updateCar(@RequestBody car car) {
        carService.updateCar(car);
        return ResponseEntity.ok("Car updated successfully.");
    }

    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car deleted successfully.");
    }
}