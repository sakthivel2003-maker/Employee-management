/*
 * File : EmployeeView.java
 * Package : com.clarix.employeemanagement.view
 * Description : Displays menu and perform operations
 * Author : sakthi
 * Email : sakthi@gmail.com
 * Created on : 21-12-2025
 * Version : 1.1.0
 *
 * Copyright : © 2025 clarivium technologies. All rights reserved
 */

package com.clarix.employeemanagement.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.clarix.employeemanagement.controller.EmployeeController;

/**
 * Provides options for managing employee information
 *
 * @see com.clarix.employeemanagement.controller.EmployeeController
 * @see com.clarix.employeemanagement.model.Employee
 */
public class EmployeeView {
    private Scanner scanner = new Scanner(System.in);
    private EmployeeController employeeController;

    public EmployeeController getEmployeeController() {       
        if (null == employeeController) 
            employeeController = new EmployeeController();
        return employeeController;
    }

    /**
     * Displays available options and performs the selected option
     */
    public void executeMenu() {
        boolean exit = false;

        do {
   	    displayMenu();
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1 -> addEmployee();

                case 2 -> updateEmployee();

                case 3 -> viewEmployeeById();

                case 4 -> viewAllEmployees();

                case 5 -> deleteEmployee();

                case 6 -> {
                    System.out.println("Are you want to exit? 1.Yes 2.No");
                    exit = (1 == scanner.nextInt());      
                }

                default -> System.out.println("Invalid option");
            }
        } while (!exit);
        
        System.out.println("Application exited...");
    }
    
    /**
     * Displays the available menu options
     */
    public void displayMenu() {
        System.out.println("""
                1.Add Employee
                2.Update Employee
                3.View Employee by id
                4.View All Employees
                5.Delete Employee
                6.Exit
                """);
        System.out.print("choose an option: ");
    }

    /**
     * Saves a new employee using provided details
     */
    public void addEmployee() {
        System.out.println("You chose to add employee");
        String name = getValidatedData("NAME", "Enter the name: ");

        LocalDate dateOfBirth = getValidatedBirthDate();
        
        System.out.print("Enter the phone number: ");
        long phoneNumber = scanner.nextLong();
        scanner.nextLine();

        String email = getValidatedData("EMAIL", "Enter the email: ");

        System.out.print("Enter the address: ");
        String address = scanner.nextLine();

        System.out.print("Enter the pincode: ");
        int pinCode = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        String statusMessage = getEmployeeController().addEmployee(name, 
                dateOfBirth, address, phoneNumber, salary, email, pinCode);
        System.out.println(statusMessage); 
    }

    /**
     * Modifies existing employee information
     */
    public void updateEmployee() {
        System.out.println("You chose to update employee");
        System.out.print("Enter employee id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String statusMessage = getEmployeeController().isEmployeePresent(id)
                ? getEmployeeController().updateEmployee(id, 
                getValidatedData("EMAIL", "Enter new email:"))
                : "Employee not found";

       System.out.println(statusMessage);
    }

    /**
     * Deletes an employee from the system 
     */
    public void deleteEmployee() {
        System.out.println("You chose to delete employee");
        System.out.print("Enter the id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String statusMessage = getEmployeeController().deleteEmployee(id);
        System.out.println(statusMessage);
    }

    /**
     * Display employee information by id
     */
    public void viewEmployeeById() {
        System.out.println("You chose to view employee by id");
        System.out.print("Enter employee id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
 
        String statusMessage = getEmployeeController().viewEmployeeById(id);
        System.out.println(statusMessage);
    }

    /**
     * Displays all the available employee information
     */
    public void viewAllEmployees() {
        System.out.println("You chose to view all employees");
        String statusMessage = getEmployeeController().viewAllEmployees();
        System.out.println(statusMessage);
    }

    /**
     * Validates the given details
     *
     * @param validationType type of the details
     * @prompt message to fill the detail
     * @return valid detail or error message if invalid format
     */
    public String getValidatedData(String validationType, String prompt) {
        while (true) {
            System.out.print(prompt);
            String userData = scanner.nextLine();

            String validationMessage = switch (validationType) {
                case "NAME" -> getEmployeeController().validateName(userData);

                case "EMAIL" -> getEmployeeController().validateEmail(userData);

                default -> "Invalid validation type";
                            
            };

            if (null == validationMessage) 
                return userData;

            System.out.println(validationMessage);
        }
    }
    /**
     * Validates employee birth date
     *
     * @return a birth date that is valid
     */
    public LocalDate getValidatedBirthDate() {
    
        while (true) {
            try {
                System.out.print("Enter the date of birth(dd/MM/yyyy): ");
                LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                String validationMessage = getEmployeeController()
                        .validateDateOfBirth(dateOfBirth);
                if (null == validationMessage) 
                    return dateOfBirth;

                System.out.println(validationMessage);
            } catch (DateTimeParseException invalidDateException) {
                System.out.println("Invalid date format. Use dd/MM/yyyy");
            }
        }
    }
}