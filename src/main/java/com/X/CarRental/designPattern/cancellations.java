package com.X.CarRental.designPattern;

import com.X.CarRental.model.booking;
import com.X.CarRental.service.reportService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class cancellations implements parameterStrategy{
    reportService reportService;

    @Override
    public String compute(int rid, ArrayList<booking> bookings) throws SQLException {
        return Cancellations(bookings);
    }

    public String Cancellations(List<booking> bookings) {
        if (bookings == null || bookings.isEmpty()) {
            return "No bookings available";
        }
        for (booking booking : bookings) {
            if (!booking.getStatus().equals("Cancelled")) {
                bookings.remove(booking);
            }
        }
        return Integer.toString(bookings.size());
    }
}
