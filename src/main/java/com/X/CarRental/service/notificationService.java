package com.X.CarRental.service;

import com.X.CarRental.model.notification;
import com.X.CarRental.repo.notificationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class notificationService {

    private final notificationsRepo notificationsRepo;

    @Autowired
    public notificationService(notificationsRepo notificationsRepo) {
        this.notificationsRepo = notificationsRepo;
    }

    public ArrayList<notification> getNotifications(int id, String type) {
        if (type.equals("customer")) {
            return notificationsRepo.getCusNotifications(id);
        } else {
            return notificationsRepo.getEmpNotifications(id, type);
        }
    }

    public void complete(int id){
        notificationsRepo.completeNotification(id);
    }

    public void delete(int id){ notificationsRepo.deleteNotification(id); }

    public void create(String title, String message, String to, int employee, int customer){
        System.out.println(title);
        notificationsRepo.createNotification(title, message, to, employee, customer, LocalDateTime.now());
    }
}


