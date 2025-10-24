package com.X.CarRental.designPattern;

import com.X.CarRental.model.booking;
import com.X.CarRental.service.reportService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class totalIncome implements parameterStrategy {

    @Override
    public String compute(int rid, ArrayList<booking> bookings){
        return getTotalIncome(bookings);
    }

    private String getTotalIncome(ArrayList<booking> bookings) {
        double totalIncome = 0;
        for (booking booking : bookings) {
            totalIncome += booking.getTotalPrice();
        }
        return String.format("%.2f", totalIncome);
    }
}
