/*
 * File : EmployeeService.java
 * Package : com.clarix.employeemanagement.service
 * Description :  Manages employee details and related rules
 * Author : sakthi
 * Email : sakthi@gmail.com
 * Created on : 21-12-2025
 * Version : 1.1.0
 *
 * Copyright : © 2025 clarivium technologies. All rights reserved
 */

package com.clarix.employeemanagement.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.clarix.employeemanagement.model.Employee;
import com.clarix.employeemanagement.repository.EmployeeRepository;

/**
 * Manages employee information and related validations
 * 
 * @see com.clarix.employeemanagement.model.Employee
 */
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeRepository getEmployeeRepository() {
        if (null == employeeRepository) {
            employeeRepository = new EmployeeRepository();
        }
        return employeeRepository;
    }

    /**
     * Adds a new employee
     *
     * @param name employee name
     * @param dateOfBirth employee birth date
     * @param address employee address details
     * @param phoneNumber employe mobile number
     * @param salary employee salary
     * @param email employee email id
     * @param pinCode address pincode
     * @return status message after adding the employee
     */
    public String addEmployee(String name, LocalDate dateOfBirth,
            String address, long phoneNumber, double salary, String email,
            int pinCode) {
        
        String statusMessage;
        Employee employee = new Employee(name, dateOfBirth, address,
                phoneNumber, salary, email, pinCode);
        employee.setAge(calculateAge(dateOfBirth));
        employee.setActive(true);

        try {
            Employee addedEmployee = getEmployeeRepository()
                    .addEmployee(employee);
            statusMessage = (null != addedEmployee) 
                    ? String.format("%nadded successful%n%n%s", addedEmployee)
                    : "Failed to add employee";
        } catch (SQLException sqlException) {
            statusMessage = String.format("Database error: %s", 
                    sqlException.getMessage());
        } catch (ClassNotFoundException missingClassException) {
            statusMessage = String.format("driver class not found: %s",
                    missingClassException.getMessage());
        }
        
        return statusMessage;        
    }	

    /**
     * Updates employee email
     *
     * @param id employee identification number
     * @param email to update the old email
     * @return status message or not found message
     */
    public String updateEmployee(int id, String email) {
        String statusMessage;

        try {
            Employee updatedEmployee = getEmployeeRepository()
                    .updateEmployee(id, email);
            statusMessage = (null != updatedEmployee) 
                    ? String.format("%nupdate successful%n%n%s", 
                    updatedEmployee)
                    : "Invalid email format";
        } catch (SQLException sqlException) {
            statusMessage = String.format("Database error: %s",
                    sqlException.getMessage());
        } catch (ClassNotFoundException missingClassException) {
            statusMessage = String.format("driver class not found: %s",
                    missingClassException.getMessage());
        }

        return statusMessage;
    }

    /**
     * Marks an employee as inactive
     *
     * @param id employee identification number
     * @return status message or not found message
     */
    public String deleteEmployee(int id) {
        String statusMessage;

        try {
            Employee deletedEmployee = getEmployeeRepository()
                    .deleteEmployee(id);
            statusMessage = (null != deletedEmployee) 
                    ? String.format("%nDelete successful%n%n%s",
                    deletedEmployee)
                    : "Failed to delete employee";
        } catch (SQLException sqlException) {
            statusMessage = String.format("Database error: %s",
                    sqlException.getMessage());
        } catch (ClassNotFoundException missingClassException) {
            statusMessage = String.format("driver class not found: %s",
                    missingClassException.getMessage());
        }

        return statusMessage;
    }

    /**
     * Shows employee details by id
     *
     * @param id employee identification number
     * @return employee details or not found message
     */
    public String viewEmployeeById(int id) {
        String statusMessage;

        try {
            Employee employee = getEmployeeRepository().viewById(id);
            statusMessage = (null != employee && employee.isActive())
                    ? String.format("%nEmployee details%n%n%s", employee)
                    : "Employee not found";
        } catch (SQLException sqlException) {
            statusMessage = String.format("Database error: %s",
                    sqlException.getMessage());
        } catch (ClassNotFoundException missingClassException) {
            statusMessage = String.format("driver class not found: %s",
                    missingClassException.getMessage());
        }

        return statusMessage;
    }

    /**
     * Shows details of all employees
     * 
     * @return employee or error if not found
     */
    public String viewAllEmployees() {
        StringBuilder statusMessage = new StringBuilder();

        try {
            List<Employee> employees = getEmployeeRepository().viewAll();
            if (employees.isEmpty()) {
                statusMessage.append("No active employees found");
            } else {
                for (Employee employee : employees) {
                    statusMessage.append(employee).append("\n\n");
                }
            }
        } catch (SQLException sqlException) {
            statusMessage = new StringBuilder(
                    String.format("Database error: %s", sqlException
                    .getMessage()));
        } catch (ClassNotFoundException missingClassException) {
            statusMessage = new StringBuilder(
                    String.format("driver class not found: %s",
                    missingClassException.getMessage()));
        }

        return statusMessage.toString();
    }

    /**
     * Validates employee name
     *
     * @param name employee name
     * @return valildation message or null if valid
     */
    public String validateName(String name) {
        return name.matches("^[A-Za-z ]{3,30}$") ? null 
                : "Invalid name (3-30 letters only)";
    }

    /**
     * Validates employee date of birth
     *
     * @param dateOfBirth employee date of birth
     * @return validation message or null if valid
     */
    public String validateDateOfBirth(LocalDate dateOfBirth) {
        int age = calculateAge(dateOfBirth);
        return (age >= 18 && age <= 60) ? null : "Employee age must be 18-60";
    }
    
    /**
     * Validates employee email
     * 
     * @param email employee email address
     * @return validation message or null if valid
     */
    public String validateEmail(String email) {
        String statusMessage = null;

        try {
            statusMessage = !email.matches("^[A-Za-z0-9_.]+@[A-Za-z0-9.]+$") 
                    ? "Invalid email format"
                    : getEmployeeRepository().isEmailExist(email)
                    ? "Email already exists"
                    : null;
        } catch (SQLException sqlException) {
            statusMessage = String.format(
                    "Database error: %s", sqlException.getMessage());
        } catch (ClassNotFoundException missingClassException) {
            statusMessage = String.format(
                    "Driver class not found: %s",missingClassException
                            .getMessage());
        }

        return statusMessage;
    }

    /**
     * Checks employee availability 
     * 
     * @param id employee identification number
     * @return true if employee exists and is active
     */
    public boolean isEmployeePresent(int id) {
        boolean present;

        try {
            present = getEmployeeRepository().isEmployeePresent(id);
        } catch (SQLException sqlException) {
            present = false;
        } catch (ClassNotFoundException missingClassException) {
            present = false;
        }

        return present;
    }

    public int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}