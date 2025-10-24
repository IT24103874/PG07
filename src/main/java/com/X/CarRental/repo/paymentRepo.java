package com.X.CarRental.repo;

import com.X.CarRental.model.payment;
import com.X.CarRental.model.report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface paymentRepo extends JpaRepository<payment, Integer> {
    //Custom query
}
