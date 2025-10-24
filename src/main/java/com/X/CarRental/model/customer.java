package com.X.CarRental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class customer {
    int id;
    String nic;
    String firstname;
    String lastname;
    String phone;
    String password;
    String account;
    String bank;
    String branch;

    public customer(String nic, String name, String phone, String paymentInfo, String password) {
        this.nic = nic;
        String[] parts = name.split(" ");
        this.firstname = parts[0];
        this.lastname = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
        this.phone = phone;
        setPaymentInfo(paymentInfo);
        this.password = password;
    }

    public customer(String nic, String firstname, String lastname, String phone, String password, String lol) {
        this.nic = nic;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.password = password;
    }

    public void setPaymentInfo(String paymentInfo) {
        String json = paymentInfo;

        this.account = json.replaceAll(".*\"accountNumber\"\\s*:\\s*\"([^\"]*)\".*", "$1");
        this.bank    = json.replaceAll(".*\"bank\"\\s*:\\s*\"([^\"]*)\".*", "$1");
        this.branch  = json.replaceAll(".*\"branch\"\\s*:\\s*\"([^\"]*)\".*", "$1");

    }

    public void printDetails() {
        System.out.println("NIC: " + nic + "first name: " + firstname + "last name: " + lastname + "phone: " + phone + "password: " + password);
    }
}
