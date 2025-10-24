package com.X.CarRental.designPattern;

import com.X.CarRental.model.booking;
import com.X.CarRental.model.car;
import com.X.CarRental.service.reportService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class mostUsedCars implements parameterStrategy{
    reportService reportService;

    @Override
    public String compute(int rid, ArrayList<booking> bookings) throws SQLException {
        return mostUsedCars(bookings);
    }

    public String mostUsedCars(List<booking> bookings) {
        if (bookings == null || bookings.isEmpty()) {
            return "No bookings available";
        }

        Map<car, Long> carUsage = bookings.stream()
                .collect(Collectors.groupingBy(booking::getCar, Collectors.counting()));

        return carUsage.entrySet().stream()
                .sorted(Map.Entry.<car, Long>comparingByValue().reversed())
                .limit(3)
                .map(e -> e.getKey().getType() + " (" + e.getValue() + ")")
                .collect(Collectors.joining(", "));

    }

}
