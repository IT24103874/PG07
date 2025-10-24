package com.X.CarRental.controller;


import com.X.CarRental.model.car;
import com.X.CarRental.model.customer;
import com.X.CarRental.model.employee;
import com.X.CarRental.repo.usersRepo;
import com.X.CarRental.service.carService;
import com.X.CarRental.service.notificationService;
import com.X.CarRental.service.ratingService;
import com.X.CarRental.service.userService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;

@Controller
public class userController {

    private final userService userService;
    private final usersRepo usersRepo;
    private final carService carService;

    public userController(userService userService, usersRepo usersRepo, carService carService) {
        this.userService = userService;
        this.usersRepo = usersRepo;
        this.carService = carService;
    }

    @GetMapping("/employeeLogIn")
    public String employeeLogIn(Model model, HttpSession session) {
        model.addAttribute("type", "employee");
        session.setAttribute("employee", null);
        session.setAttribute("customer", null);
        return "logIn";
    }

    @GetMapping("/customerLogIn")
    public String customerLogIn(Model model, HttpSession session) {
        model.addAttribute("type", "customer");
        session.setAttribute("employee", null);
        session.setAttribute("customer", null);
        return "logIn";
    }

    @GetMapping("/employeeProfile")
    public String employeeProfile() {
        return "employeeProfile";
    }

    @PostMapping("/handleEmployeeLogIn")
    public String handleEmployeeLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        employee employee = userService.checkEmpLogIn(username, password);
        if (employee != null) {
            session.setAttribute("employee", employee);
            return "/employeeProfile";
        }
        return "redirect:/employeeLogIn";
    }

    @PostMapping("/handleCustomerLogIn")
    public String handleCustomerLogin(@RequestParam String username, @RequestParam String password, HttpSession session, @RequestParam String type, Model model) {
        if (type.equals("register")) {
            model.addAttribute("type", "register");
            return "/registerUser";
        } else {
            customer customer = userService.checkCusLogIn(username, password);
            if (customer != null) {
                session.setAttribute("customer", customer);
                return "redirect:/userDashboard";
            }
        }
        return "redirect:/customerLogIn";
    }

    @GetMapping("/customerProfile")
    public String customerProfile(Model model, HttpSession session) {
        ArrayList<car> cars = carService.GetCarByOwnerId((customer) session.getAttribute("customer"));
        cars.forEach(car -> {
            if (car.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(car.getImage());
                car.setBase64Image(base64Image); // add this transient field
            }
        });
        model.addAttribute("cars", cars);
        return "/userProfile";
    }

    @GetMapping("/userEditProfile")
    public String userEditProfile(@RequestParam String type, Model model) {
        if (type.equals("edit")) {
            model.addAttribute("type", "edit");
            return "/registerUser";
        } else {
            return "redirect:/customerLogIn";
        }
    }

    @PostMapping("/registerOrEditUser")
    public String registerOrEditUser(@RequestParam(required = false) String firstName,
                                     @RequestParam(required = false) String lastName,
                                     @RequestParam(required = false) String nic,
                                     @RequestParam(required = false) String password,
                                     @RequestParam(required = false) String phoneNumber,
                                     @RequestParam(required = false) String accountNumber,
                                     @RequestParam(required = false) String bank,
                                     @RequestParam(required = false) String branch,
                                     @RequestParam String type,
                                     HttpSession session) {
        if (type.equals("Register")){
            userService.registerUser(firstName, lastName, nic, password, phoneNumber, accountNumber, bank, branch);
            return "redirect:/customerLogIn";
        } else if (type.equals("Edit")){
            customer customer = (customer) session.getAttribute("customer");
            if (!(customer.getNic().equals(nic))) {
                System.out.println("Does not equal.");
            }
            userService.updateCurrentUser(firstName, lastName, nic, password, phoneNumber, accountNumber, bank, branch);
            return "redirect:/customerLogIn";
        }
        return "redirect:/customerLogIn";
    }

    @GetMapping("/createEmployees")
    public String createEmployees() {
        return "/createUser";
    }

    @GetMapping("/employeeList")
    public String employeeList(Model model) {
        ArrayList<employee> employees = usersRepo.getEmployees();
        model.addAttribute("employees", employees);
        return "/viewEmployees";
    }

    @PostMapping("/deleteOrEditEmployees")
    public String deleteOrEditEmployees(@RequestParam int eid,
                                        @RequestParam String type,
                                        Model model) {
        if (type.equals("delete")) {
            userService.deleteEmployee(eid);
            return "redirect:/employeeList";
        } else {
            employee employee = usersRepo.getEmployee(eid);
            model.addAttribute("employee", employee);
            model.addAttribute("type", "edit");
            return "/createUser";
        }
    }

    @GetMapping("/createUsers")
    public String createUsers(Model model) {
        model.addAttribute("type", "create");
        return "/createUser";
    }

    @PostMapping("/createOrEditEmployee")
    public String createOrEditEmployee(@RequestParam(required = false) String firstName,
                                       @RequestParam(required = false) String lastName,
                                       @RequestParam(required = false) String eid,
                                       @RequestParam(required = false) String password,
                                       @RequestParam(required = false) String phoneNumber,
                                       @RequestParam(required = false) String email,
                                       @RequestParam(required = false) String employee_type,
                                       @RequestParam String type){
        if (type.equals("Create")) {
            userService.createEmployee(firstName, lastName, password, phoneNumber, email, employee_type);
        } else {
            userService.updateEmployee(firstName, lastName, eid, password, phoneNumber, email);
        }
        return "redirect:/employeeList";
    }

    @GetMapping("/userDashboard")
    public String userDashboard(Model model) {
        ArrayList<car> cars = new ArrayList<>();
        model.addAttribute("cars", cars);
        return "userDashboard";
    }
}
