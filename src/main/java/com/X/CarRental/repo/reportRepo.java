package com.X.CarRental.repo;

import com.X.CarRental.model.report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface reportRepo extends JpaRepository<report, Integer> {
    List<report> findByNameContainingIgnoreCase(String name);
}
