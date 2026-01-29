/*
 * File : EmployeeController.java
 * Package : com.clarix.employeemanagement.controller
 * Description : Manages employee details like add, update, view and delete
 * Author : sakthi
 * Email : sakthi@gmail.com
 * Created on : 21-12-2025
 * Version : 1.1.0
 *
 * Copyright : © 2025 clarivium technologies. All rights reserved
 */

package com.clarix.employeemanagement.controller;

import java.time.LocalDate;

import com.clarix.employeemanagement.service.EmployeeService;

/**
 * Manages employee details such as adding, updating, viewing and deleting
 *
 * @see com.clarix.employeemanagement.service
 */
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeService getEmployeeService() {
        if (null == employeeService) {
            employeeService = new EmployeeService();
        }
        return employeeService;
    }
    /**
     * Adds a new employee
     * 
     * @param name employee name
     * @param dateOfBirth employee birth date
     * @param address employee address details
     * @param mobile employee mobile number
     * @param salary employee salary
     * @param email employee email id
     * @param pinCode address pincode
     * @return employee or error if not added
     */
    public String addEmployee(String name, LocalDate dateOfBirth, 
            String address, long mobile, double salary, String email, 
            int pinCode) {
   
        return getEmployeeService().addEmployee(name, dateOfBirth, address, 
                mobile, salary, email, pinCode);
    }

    /**
     * Updates employee email
     *
     * @param id employee identification number
     * @param newEmail to update the old email
     * @return Success message or error if id not found
     */
    public String updateEmployee(int id, String email) {
        return getEmployeeService().updateEmployee(id, email);
    }

    /**
     * Deletes an employee
     * 
     * @param id employee identification number
     * @return Success message or error if id not found
     */
    public String deleteEmployee(int id) {
        return getEmployeeService().deleteEmployee(id);
    }

    /**
     * Shows employee details by id
     *
     * @param id employee identification number
     * @return employee or error if id not found
     */
    public String viewEmployeeById(int id) {
        return getEmployeeService().viewEmployeeById(id);
    }

    /**
     * Shows details of all employees
     *
     * @return employees or error if not found
     */
    public String viewAllEmployees() {
        return getEmployeeService().viewAllEmployees();
    }

    /**
     * Checks employee availability
     *
     * @param id employee identification number
     * @return true if employee exists and active
     */
    public boolean isEmployeePresent(int id) {
        return getEmployeeService().isEmployeePresent(id);
    }

    /**
     * Validates employee name
     * 
     * @param name employee name
     * @return validation message or null if valid
     */
    public String validateName(String name) {
        return getEmployeeService().validateName(name);
    }   

    /**
     * Validates employee email
     *
     * @param email employee email address
     * @return validation message or null if valid
     */
    public String validateEmail(String email) {
        return getEmployeeService().validateEmail(email);
    }

    /**
     * Validates employee date of birth
     *
     * @param dateOfBirth employee date of birth
     * @return validation message or null if valid
     */
    public String validateDateOfBirth(LocalDate dateOfBirth) {
        return getEmployeeService().validateDateOfBirth(dateOfBirth);
    }

}