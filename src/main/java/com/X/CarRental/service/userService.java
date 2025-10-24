package com.X.CarRental.service;

import com.X.CarRental.model.customer;
import com.X.CarRental.model.employee;
import com.X.CarRental.repo.usersRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class userService {
    private final usersRepo usersRepo;

    public userService(usersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public customer checkCusLogIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return null;
        }
        ArrayList<customer> allCustomers = usersRepo.getCustomers();
        for (customer customer : allCustomers) {
            if (customer.getNic().equals(email) && customer.getPassword().equals(password)){
                return customer;
            }
        }
        return null;
    }

    public employee checkEmpLogIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return null;
        }
        ArrayList<employee> allEmployees = usersRepo.getEmployees();
        for (employee employee : allEmployees) {
            if (employee.getEid() == Integer.parseInt(email) && employee.getPassword().equals(password)){
                return employee;
            }
        }
        return null;
    }

    public void registerUser(String firstName, String lastName, String nic, String password, String phoneNumber, String accountNumber, String bank, String branch) {
        if (!validateCustomerNull(nic)){
            return;
        }
        customer customer = new customer(0, nic, firstName, lastName, phoneNumber, password, accountNumber, bank, branch);
        usersRepo.registerCustomer(customer);
    }

    public boolean validateCustomerNull(String nic){
        ArrayList<customer> allCustomers = usersRepo.getCustomers();
        for (customer customer : allCustomers) {
            if (customer.getNic().equals(nic)){
                return false;
            }
        }
        return true;
    }

    public void updateCurrentUser(String firstName, String lastName, String nic, String password, String phoneNumber, String accountNumber, String bank, String branch){
        customer customer = new customer(nic, firstName, lastName, phoneNumber, password, "lol");
        customer.setAccount(accountNumber);
        customer.setBank(bank);
        customer.setBranch(branch);
        customer.printDetails();

        usersRepo.updateCustomer(customer);
    }

    public void deleteUser(String nic){
        usersRepo.deleteUser(nic);
    }

    public void deleteEmployee(int eid){
        usersRepo.deleteEmployee(eid);
    }

    public void createEmployee(String firstName, String lastName, String password, String phoneNumber, String email, String employee_type) {
        employee employee = new employee(0, firstName, lastName, phoneNumber, password, email, employee_type);
        usersRepo.createEmployee(employee);
    }

    public void updateEmployee(String firstName, String lastName, String eid, String password, String phoneNumber, String email) {
        employee employee = new employee(Integer.parseInt(eid), firstName, lastName, phoneNumber, password, email," ");
        usersRepo.updateEmployee(employee);
    }

}
