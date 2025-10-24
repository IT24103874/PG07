package com.X.CarRental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    private String name;
    private LocalDate date;
    private LocalDateTime start;
    private LocalDateTime end;
    private String parameters;
    @Transient
    private ArrayList<String> parametersList;
    @Transient
    private ArrayList<String> parametersAnswers;

    public report(int rid, String name, LocalDate date, Timestamp startTime, Timestamp endTime, ArrayList<String> parameters, ArrayList<String> setParametersAnswers) {
        this.id = rid;
        this.name = name;
        this.date = date;
        this.start = startTime.toLocalDateTime();
        this.end = endTime.toLocalDateTime();
        this.parametersList = parameters;
        this.parameters = parametersList.toString();
        this.parametersAnswers = setParametersAnswers;
        cleanUpAnswers();
    }


    public report(String name, LocalDate date, Timestamp startTime, Timestamp endTime, ArrayList<String> parameters) {
        this.name = name;
        this.date = date;
        this.start = startTime.toLocalDateTime();
        this.end = endTime.toLocalDateTime();
        this.parametersList = parameters;
        this.parameters = parametersList.toString();
    }

    public report(String name, Timestamp startTime, Timestamp endTime, ArrayList<String> parametersN, LocalDate date) {
        this.name = name;
        this.date = date;
        this.start = startTime.toLocalDateTime();
        this.end = endTime.toLocalDateTime();
        this.parametersList = parametersN;
        this.parameters = parametersN.toString();
    }

    public report() {

    }

    public void cleanUpAnswers(){
        for (int i = 0; i < parametersAnswers.size(); i++){
            String cleaned = parametersAnswers.get(i).replace("[", "").replace("]", "");
            this.parametersAnswers.set(i, cleaned);
        }
    }
}
