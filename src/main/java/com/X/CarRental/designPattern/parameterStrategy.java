package com.X.CarRental.designPattern;

import com.X.CarRental.model.booking;

import java.sql.SQLException;
import java.util.ArrayList;

public interface parameterStrategy {
    String compute(int rid, ArrayList<booking> bookings) throws SQLException;
}
