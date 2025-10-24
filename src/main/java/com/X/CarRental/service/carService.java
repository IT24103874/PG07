package com.X.CarRental.service;

import com.X.CarRental.model.car;
import com.X.CarRental.model.customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class carService {

    @Autowired
    private final DataSource dataSource;

    public carService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createCar(car car) {
        String query = "INSERT INTO Car (vin, ownerID, image, description, type, license, feePerHour, colour, pickUp, dropOff) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, car.getVin());
            stmt.setInt(2, car.getOwnerId());
            stmt.setBytes(3, car.getImage());
            stmt.setString(4, car.getDescription());
            stmt.setString(5, car.getType());
            stmt.setString(6, car.getLicense());
            stmt.setFloat(7, car.getFeePerHour());
            stmt.setString(8, car.getColour());
            stmt.setString(9, car.getPickUp());
            stmt.setString(10, car.getDropOff());
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("SQL error at create rating: " + e.getMessage());
        }
    }

    public ArrayList<car> GetCars() {
        String query = "SELECT * FROM Car";
        ArrayList<car> cars = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                car car = new car(
                        (long) rs.getInt("id"),
                        rs.getString("vin"),
                        rs.getInt("ownerID"),
                        rs.getBytes("image"),
                        rs.getString("colour"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("license"),
                        rs.getString("pickUp"),
                        rs.getString("dropOff"),
                        rs.getFloat("rating"),
                        rs.getFloat("feePerHour")
                );
                cars.add(car);
            }
        } catch (SQLException e){
            System.out.println("SQL error at getAllCars: " + e.getMessage());
        }
        return cars;
    }

    public ArrayList<car> GetCarByOwnerId(customer customer) {
        int id = customer.getId();
        ArrayList<car> cars = GetCars();
        ArrayList<car> filteredCars = new ArrayList<>();
        for (car car : cars){
            if (car.getOwnerId() == id){
                filteredCars.add(car);
            }
        }
        return filteredCars;
    }

    public car GetCarById(long id) {
        car filteredCar = null;
        ArrayList<car> cars = GetCars();
        for (car car : cars){
            if (car.getId() == id){
                filteredCar = car;
            }
        }
        return filteredCar;
    }

    public ArrayList<car> getAvailableCars(LocalDateTime startTime, LocalDateTime endTime) {
        ArrayList<car> cars = new ArrayList<>();
        String query = """
        SELECT * FROM Car c
        WHERE c.id NOT IN (
            SELECT b.carID
            FROM Booking b
            WHERE b.start < ? 
            AND b.end > ?
            AND b.status != 'Cancelled'
        )
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(endTime));
            stmt.setTimestamp(2, Timestamp.valueOf(startTime));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                car c = new car(
                        rs.getLong("id"),
                        rs.getString("vin"),
                        rs.getInt("ownerID"),
                        rs.getBytes("image"),
                        rs.getString("colour"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("license"),
                        rs.getString("pickUp"),
                        rs.getString("dropOff"),
                        rs.getFloat("rating"),
                        rs.getFloat("feePerHour")
                );
                cars.add(c);
            }

        } catch (SQLException e) {
            System.out.println("SQL error at getAvailableCars: " + e.getMessage());
        }
        return cars;
    }

    public void updateCar(car car) {
        String query = "UPDATE Car SET vin = ?, ownerID = ?, image = ?, description = ?, " +
                "type = ?, license = ?, feePerHour = ?, colour = ?, pickUp = ?, dropOff = ? " +
                "WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, car.getVin());
            stmt.setInt(2, car.getOwnerId());
            stmt.setBytes(3, car.getImage());
            stmt.setString(4, car.getDescription());
            stmt.setString(5, car.getType());
            stmt.setString(6, car.getLicense());
            stmt.setFloat(7, car.getFeePerHour());
            stmt.setString(8, car.getColour());
            stmt.setString(9, car.getPickUp());
            stmt.setString(10, car.getDropOff());
            stmt.setLong(11, car.getId());

            stmt.executeUpdate();
            System.out.println("Car with ID " + car.getId() + " updated successfully.");

        } catch (SQLException e) {
            System.out.println("SQL error at updateCar: " + e.getMessage());
        }
    }

    public void deleteCar(long id) {
        String query = "DELETE FROM Car WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("Car with ID " + id + " deleted successfully.");

        } catch (SQLException e) {
            System.out.println("SQL error at deleteCar: " + e.getMessage());
        }
    }
}
