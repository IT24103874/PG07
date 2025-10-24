package com.X.CarRental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
@AllArgsConstructor
public class employee {
    int eid;
    String firstname;
    String lastname;
    String phone;
    String password;
    String email;
    String type;

    public employee(int eid, String name, String phone, String password, String email, String type) {
        this.eid = eid;
        String[] parts = name.split(" ");
        this.firstname = parts[0];
        this.lastname = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public void printDetails(){
        System.out.println(eid + " " + firstname + " " + lastname + " " + phone);
    }
}
