package com.X.CarRental.repo;

import com.X.CarRental.model.customer;
import com.X.CarRental.model.employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class usersRepo {
    @Autowired
    private DataSource dataSource;

    public ArrayList<customer> getCustomers() {
        ArrayList<customer> customers = new ArrayList<>();
        String query = "select * from customer";
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customer customer = new customer(
                        rs.getInt("id"),
                        rs.getString("nic"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("account"),
                        rs.getString("branch"),
                        rs.getString("bank")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error at getCustomer in usersRepo -> " + e.getMessage());
        }
        return customers;
    }

    public ArrayList<employee> getEmployees() {
        ArrayList<employee> employees = new ArrayList<>();
        String query = "select * from employee";
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employee employee = new employee(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("type")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Error at getEmployees in usersRepo -> " + e.getMessage());
        }
        return employees;
    }

    public void registerCustomer(customer customer){
        String query = "insert into customer(nic, firstname, lastname, phone, password, account, bank, branch) values(?,?,?,?,?,?,?,?)";

        try{
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, customer.getNic().trim());
                stmt.setString(2, customer.getFirstname().trim());
                stmt.setString(3, customer.getLastname().trim());
                stmt.setString(4, customer.getPhone().trim());
                stmt.setString(5, customer.getPassword().trim());
                stmt.setString(6, customer.getAccount().trim());
                stmt.setString(7, customer.getBranch().trim());
                stmt.setString(8, customer.getBank().trim());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Error at registerCustomer in usersRepo -> " + e.getMessage());
        }
    }

    public void deleteUser(String nic){
        String query = "delete from customer where nic = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, String.valueOf(nic));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error at deleteUser in usersRepo -> " + e.getMessage());
        }
    }

    public void deleteEmployee(int eid){
        String query = "delete from employee where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, eid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error at deleteEmployee in usersRepo -> " + e.getMessage());
        }
    }

    public void updateCustomer(customer customer){
        String query = "update customer set firstname = ?, lastname = ?, phone = ?, password = ?, account = ?, branch = ?, bank = ? where id = ?";

        customer.printDetails();
        try{
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, customer.getFirstname().trim());
                stmt.setString(2, customer.getLastname().trim());
                stmt.setString(3, customer.getPhone().trim());
                stmt.setString(4, customer.getPassword().trim());
                stmt.setString(5, customer.getAccount().trim());
                stmt.setString(6, customer.getBranch().trim());
                stmt.setString(7, customer.getBank().trim());
                stmt.setInt(8, customer.getId());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Error at updateCustomer in usersRepo -> " + e.getMessage());
        }
    }

    public void createEmployee(employee employee){
        String query = "insert into employee(firstname, lastname, phone, password, email, type) values(?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, employee.getFirstname().trim());
            stmt.setString(2, employee.getLastname().trim());
            stmt.setString(3, employee.getPhone().trim());
            stmt.setString(4, employee.getPassword().trim());
            stmt.setString(5, employee.getEmail().trim());
            stmt.setString(6, employee.getType().trim());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error at createEmployee in usersRepo -> " + e.getMessage());
        }
    }

    public void updateEmployee(employee employee){
        String query = "update employee set firstname = ?, lastname = ?, phone = ?, password = ?, email = ? where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, employee.getFirstname().trim());
            stmt.setString(2, employee.getLastname().trim());
            stmt.setString(3, employee.getPhone().trim());
            stmt.setString(4, employee.getPassword().trim());
            stmt.setString(5, employee.getEmail().trim());
            stmt.setInt(6, employee.getEid());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error at updateEmployee in usersRepo -> " + e.getMessage());
        }
    }

    public employee getEmployee(int eid){
        ArrayList<employee> employees = getEmployees();
        for (employee employee : employees) {
            if (employee.getEid() == eid) {
                return employee;
            }
        }
        return null;
    }
}
