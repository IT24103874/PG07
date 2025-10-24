package com.X.CarRental.repo;

import com.X.CarRental.model.notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class notificationsRepo {

    @Autowired
    private DataSource dataSource;

    public ArrayList<notification> getEmpNotifications(int id, String type) {
        ArrayList<notification> notifications = new ArrayList<>();
        String query = "SELECT id, title, message, createdAt, completed FROM Notification n WHERE (n.`to`= 'employee' AND n.employee = ?) OR (n.`to` LIKE CONCAT('%', ?, '%')) ORDER BY createdAt DESC";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new notification(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getBoolean("completed")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error at getEmpNotifications: " + e.getMessage());
        }
        return notifications;
    }

    public ArrayList<notification> getCusNotifications(int id) {
        ArrayList<notification> notifications = new ArrayList<>();
        String query = "SELECT id, title, message, createdAt, completed FROM Notification n WHERE n.to = 'customer' AND n.employee = ? ORDER BY createdAt DESC";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new notification(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getBoolean("completed")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error at getCusNotifications: " + e.getMessage());
        }
        return notifications;
    }

    public void completeNotification(int id) {
        String query = "UPDATE Notification SET completed = 1 WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error at completeNotification: " + e.getMessage());
        }
    }

    public void deleteNotification(int id) {
        String query = "DELETE FROM Notification WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("SQL error at deleteNotification: " + e.getMessage());
        }
    }

    public void createNotification(String title, String message, String to, int employee, int customer, LocalDateTime createdAt) {
        String query = "INSERT INTO Notification (title, message, `to`, employee, customer, createdAt, completed) VALUES (?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, message);
            stmt.setString(3, to);
            stmt.setInt(4, employee);
            stmt.setInt(5, customer);
            stmt.setTimestamp(6, Timestamp.valueOf(createdAt));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error at createNotification: " + e.getMessage());
        }
    }
}
