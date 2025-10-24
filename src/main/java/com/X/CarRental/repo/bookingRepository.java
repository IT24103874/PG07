package com.X.CarRental.repo;

import com.X.CarRental.model.booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bookingRepository extends JpaRepository<booking, Long> {
    // you can add custom queries if needed
}
