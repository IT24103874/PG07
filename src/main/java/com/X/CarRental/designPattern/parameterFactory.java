package com.X.CarRental.designPattern;

import java.util.HashMap;
import java.util.Map;

public class parameterFactory {
    private static final Map<String, parameterStrategy> strategies = new HashMap<>();

    static {
        strategies.put("Total Income", new totalIncome());
        strategies.put("Total Bookings", new totalBookings());
        strategies.put("Most used cars", new mostUsedCars());
        strategies.put("Least used cars", new leastUsedCars());
        strategies.put("Cancellations", new cancellations());
    }

    public static parameterStrategy getStrategy(String parameter) {
        return strategies.getOrDefault(parameter, null);
    }
}
