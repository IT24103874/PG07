package com.X.CarRental.designPattern;

import com.X.CarRental.model.booking;
import com.X.CarRental.service.reportService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class totalBookings implements parameterStrategy{

    @Override
    public String compute(int rid, ArrayList<booking> bookings){
        return getTotalBookings(bookings);
    }

    public String getTotalBookings(ArrayList<booking> bookings){
        return Integer.toString(bookings.size());
    }
}
