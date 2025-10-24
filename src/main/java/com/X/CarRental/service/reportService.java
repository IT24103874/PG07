package com.X.CarRental.service;

import com.X.CarRental.designPattern.parameterFactory;
import com.X.CarRental.designPattern.parameterStrategy;
import com.X.CarRental.model.booking;
import com.X.CarRental.model.report;
import com.X.CarRental.repo.reportRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class reportService {
    reportRepo reportRepo;
    bookingService bookingService;

    @Autowired
    public reportService(reportRepo reportsRepo, bookingService bookingService) {
        this.reportRepo = reportsRepo;
        this.bookingService = bookingService;
    }

    public ArrayList<report> loadViewReport() {
        return (ArrayList<report>) reportRepo.findAll();
    }

    public List<booking> getAllBookings(int rid) {
        List<booking> bookings = new ArrayList<>();
        report report = reportRepo.findById(rid).get();
        LocalDateTime startDate = report.getStart();
        LocalDateTime endDate = report.getEnd();
        try {
            bookings = bookingService.getAllBookings().stream()
                    .filter(b -> !b.getStart().isBefore(startDate) && !b.getEnd().isAfter(endDate))
                    .toList();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return bookings;
    }

    public void createReport(report report) {
        reportRepo.save(report);
    }

    public void deleteReport(int rid) {
        reportRepo.delete(reportRepo.findById(rid).get());
    }

    public ArrayList<report> filterReport(ArrayList<report> reports, String filter) {
        ArrayList<report> filteredReports = new ArrayList<>();
        for (report report : reports) {
            if (report.getName().toLowerCase().contains(filter.toLowerCase())) {
                filteredReports.add(report);
            }
        }
        return filteredReports;
    }

    public ArrayList<String> setParametersAnswers(
            int rid) throws SQLException{
        report report = reportRepo.findById(rid).get();
        ArrayList<String> parameters = report.getParametersList();

        ArrayList<String> answers = new ArrayList<>();

        for (String parameter : parameters) {
            parameterStrategy strategy = parameterFactory.getStrategy(parameter);
            if (strategy != null) {
                answers.add(strategy.compute(rid, new ArrayList<>(getAllBookings(rid))));
            } else {
                answers.add("Null");
            }
        }
        report.setParametersAnswers(answers);
        return answers;
    }


}
