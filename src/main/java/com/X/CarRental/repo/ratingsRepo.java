package com.X.CarRental.repo;

import com.X.CarRental.model.rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class ratingsRepo{
    @Autowired
    private DataSource dataSource;

    public void create(int bookingId, int stars, String comment){
        String query = "INSERT INTO Rating (bookingId, stars, comment, createdAt) VALUES (?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, bookingId);
            stmt.setInt(2, stars);
            stmt.setString(3, comment);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("SQL error at create rating: " + e.getMessage());
        }
    }

    public ArrayList<rating> getByBooking(int bookingId){
        ArrayList<rating> list = new ArrayList<>();
        String query = "SELECT id, bookingId, stars, comment, createdAt FROM Rating WHERE bookingId = ? ORDER BY createdAt DESC";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                list.add(new rating(
                        rs.getInt("id"),
                        rs.getInt("bookingId"),
                        rs.getInt("stars"),
                        rs.getString("comment"),
                        rs.getTimestamp("createdAt").toLocalDateTime()
                ));
            }
        } catch (SQLException e){
            System.out.println("SQL error at getByBooking: " + e.getMessage());
        }
        return list;
    }
}


