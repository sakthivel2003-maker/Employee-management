/*
 * File : Employee.java
 * Package : com.clarix.employeemanagement.model
 * Description : stores personal, contact and salary related information
 * Author : sakthi
 * Email : sakthi@gmail.com
 * Created on : 21-12-2025
 * Version : 1.1.0
 *
 * Copyright : © 2025 clarivium technologies. All rights reserved
 */

package com.clarix.employeemanagement.model;

import java.time.LocalDate;

/**
 * stores personal, contact and salary related information
 */
public class Employee {

    private boolean activeStatus;
    private int age;
    private int id;
    private int pinCode;
    private long phoneNumber;
    private double salary;
    private String address;
    private String email;
    private String name;
    private LocalDate dateOfBirth;
    
    public Employee(String name, LocalDate dateOfBirth, String address,
            long phoneNumber, double salary, String email, int pinCode) {
        
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.email = email;
        this.pinCode = pinCode;    
    }

    public Employee(int id, String name, LocalDate dateOfBirth, 
            String address, long phoneNumber, double salary, String email,
            int pinCode, int age, boolean activeStatus) {
    
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.email = email;
        this.pinCode = pinCode;
        this.age = age;
        this.activeStatus = activeStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }   

    public boolean isActive() {
        return activeStatus;
    }

    public void setActive(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Id: ").append(id).append("\n")
                .append("Name: ").append(name).append("\n")
                .append("DOB: ").append(dateOfBirth).append("\n")
                .append("Age: ").append(age).append("\n")
                .append("Address: ").append(address).append("\n")
                .append("Phone number: ").append(phoneNumber).append("\n")
                .append("Salary: ").append(salary).append("\n")
                .append("Email: ").append(email).append("\n")
                .append("Pincode: ").append(pinCode).append("\n")
                .append("Active: ").append(activeStatus).append("\n")
                .toString();	
    }
}