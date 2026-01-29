package com.clarix.employeemanagement.model;

public class Address {

    private int id;
    private int pinCode;
    private String city;
    private String doorNo;
    private String street;
    private Employee employee;

    public Address() {}

    public Address(int pinCode, String city, String doorNo, String street) {
        this.pinCode = pinCode;
        this.city = city;
        this.doorNo = doorNo;
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Door No: ").append(doorNo).append("\n")
                .append("Street: ").append(street).append("\n")
                .append("City: ").append(city).append("\n")
                .append("Pincode: ").append(pinCode)
                .toString();
    }
}