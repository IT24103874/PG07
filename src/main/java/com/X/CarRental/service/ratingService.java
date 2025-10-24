package com.X.CarRental.service;

import com.X.CarRental.model.rating;
import com.X.CarRental.repo.ratingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ratingService {

    private final ratingsRepo ratingsRepo;

    @Autowired
    public ratingService(ratingsRepo ratingsRepo) {
        this.ratingsRepo = ratingsRepo;
    }

    public void create(int bookingId, int stars, String comment){
        ratingsRepo.create(bookingId, stars, comment);
    }

    public rating getByBooking(int bookingId){
        ArrayList<rating> ratingList = ratingsRepo.getByBooking(bookingId);
        if (!ratingList.isEmpty()){
            return ratingList.get(0);
        } else {
            return null;
        }
    }
}


