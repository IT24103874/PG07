package com.X.CarRental.controller;

import com.X.CarRental.model.booking;
import com.X.CarRental.model.report;
import com.X.CarRental.repo.reportRepo;
import com.X.CarRental.service.reportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class reportController {
    private final reportService reportService;
    private final reportRepo reportRepo;

    @Autowired
    public reportController(reportService reportService, reportRepo reportsRepo) {
        this.reportService = reportService;
        this.reportRepo = reportsRepo;
    }

    @GetMapping("reportsList")
    public String reportsList(Model model) {
        ArrayList<report> reports = reportService.loadViewReport();
        model.addAttribute("reportList", reports);
        model.addAttribute("reportSize", reports.size());
        return "/reportsList";
    }

    @GetMapping("/viewReport")
    public String viewReport(@RequestParam String rid, Model model) throws SQLException, JsonProcessingException {
        int rId = Integer.parseInt(rid);
        report report = reportRepo.findById(rId).get();
        model.addAttribute("report", report);
        List<booking> bookings = reportService.getAllBookings(rId);
        report.setParametersAnswers(reportService.setParametersAnswers(report.id));
        model.addAttribute("bookings", bookings);
        model.addAttribute("parameters", report.getParametersList());
        model.addAttribute("parametersAnswers", report.getParametersAnswers());
        return "/viewReport";
    }

    @GetMapping("/editReport")
    public String editReport(@RequestParam String rid, Model model) {
        int rId = Integer.parseInt(rid);
        report report = reportRepo.findById(rId).get();
        model.addAttribute("report", report);
        model.addAttribute("type", "edit");
        model.addAttribute("rid", rId);
        return "/createReport";
    }

    @GetMapping("/viewOrEditReport")
    public String viewOrEditReport(@RequestParam String rid, @RequestParam String type, Model model) throws SQLException, JsonProcessingException {
        int rId = Integer.parseInt(rid);
        report report = reportRepo.findById(rId).get();
        String raw = report.getParameters().replace("[", "").replace("]", "");
        List<String> parameters = Arrays.stream(raw.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        report.setParametersList(new ArrayList<>(parameters));
        System.out.println("name" + report.getName() + "parameters" + report.getParametersList() + "paramtersString" + report.getParameters());
        reportService.setParametersAnswers(report.id);
        System.out.println("parametersAnswers" + report.getParametersAnswers());
        model.addAttribute("report", report);
        if (type.equals("edit")) {
            model.addAttribute("type", "edit");
            model.addAttribute("report", report);
            return "/createReport";
        } else {
            List<booking> bookings = reportService.getAllBookings(rId);
            model.addAttribute("bookings", bookings);
            model.addAttribute("parameters", report.getParametersList());
            System.out.println("parameterAnswers: " + report.getParametersAnswers());
            model.addAttribute("parametersAnswers", report.getParametersAnswers());
            return "/viewReport";
        }
    }

    @GetMapping("/api/reportBookings")
    @ResponseBody
    public List<booking> getReportBookings(@RequestParam int rid) {
        return reportService.getAllBookings(rid);
    }

    @GetMapping("/createReports")
    public String createReports(Model model) {
        model.addAttribute("type", "create");
        model.addAttribute("rid", -1);
        return "/createReport";
    }

    @PostMapping("/createReports")
    public String createReport(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                               @RequestParam String name,
                               @RequestParam List<String> parameters,
                               @RequestParam String type,
                               @RequestParam String rid) {
        int rId = Integer.parseInt(rid);
        if (type.equals("edit")) {
            report reportOg = reportRepo.findById(rId).get();
            if (startDate == null) {
                LocalDate localStart = reportOg.getStart().toLocalDate();
                startDate = Date.from(localStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
            if (endDate == null) {
                LocalDate localStart = reportOg.getEnd().toLocalDate();
                endDate = Date.from(localStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            if (name == null) {
                name = reportOg.getName();
                System.out.println("name : " + reportOg.getName());
            }
            reportService.deleteReport(rId);
            Timestamp ts1 = new Timestamp(startDate.getTime());
            Timestamp ts2 = new Timestamp(endDate.getTime());
            report report = new report(name, reportOg.getDate(), ts1, ts2, new ArrayList<>(parameters));
            reportService.createReport(report);
        } else if (type.equals("create")){
            if (startDate == null && endDate == null) {
                endDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.MONTH, -1);
                startDate = cal.getTime();

            } else if (startDate == null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.MONTH, -1);
                startDate = cal.getTime();

            } else if (endDate == null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.MONTH, 1);
                endDate = cal.getTime();
            }

            if (name == null) {
                name = "Report_" + new Timestamp(System.currentTimeMillis()).toString();
            }

            if (parameters == null) {
                parameters = new ArrayList<>();
                parameters.add("Total Income");
                parameters.add("Total Bookings");
                parameters.add("Cancellations");
            }
            Timestamp ts1 = new Timestamp(startDate.getTime());
            Timestamp ts2 = new Timestamp(endDate.getTime());
            report report = new report(name, LocalDate.now(), ts1, ts2, (ArrayList<String>) parameters);
            reportService.createReport(report);
        } else if (type.equals("delete")){
            reportService.deleteReport(rId);
        }
        return "redirect:/reportsList";
    }

    @PostMapping("/deleteReports")
    public String deleteReports(@RequestParam int rid) {
        reportRepo.deleteById(rid);
        return "redirect:/reportsList";
    }

    @GetMapping("/searchReports")
    public String searchReports(@RequestParam(required = false) String filter, Model model) {
        ArrayList<report> reports = reportService.loadViewReport();
        if (filter != null && !filter.equals("")) {
            reports = reportService.filterReport(reports, filter);
        }
        model.addAttribute("reportList", reports);
        model.addAttribute("reportSize", reports.size());
        return "/reportsList";
    }

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void favicon() {
        // do nothing, return 200 OK
    }

}
