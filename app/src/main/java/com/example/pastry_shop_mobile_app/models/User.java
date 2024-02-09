package com.example.pastry_shop_mobile_app.models;

public class User {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String username;
    private String password;
    private String type;

    public User() {
        this.name = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.address = "";
        this.username = "";
        this.password = "";
        this.type = "kupac";
    }

    public User(String name, String lastName, String phoneNumber, String address, String username, String password, String type) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
