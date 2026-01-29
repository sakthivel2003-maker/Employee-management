/**
 * File : EmployeeRepository.java
 * Package : com.clarix.employeemanagement.repository
 * Description : Performs employee related database operations
 * Author : sakthi
 * Email : sakthi@gmail.com
 * Created on : 09-01-2026
 * Version 
 */
package com.clarix.employeemanagement.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.clarix.employeemanagement.model.Employee;

public class EmployeeRepository {

    public Connection getConnection() throws ClassNotFoundException,
            SQLException {
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (null == url || null == username || null == password) 
                throw new SQLException("Db environment variables not set");

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    public Employee addEmployee(Employee employee) throws SQLException,
            ClassNotFoundException {
        String query = """
                INSERT INTO employee (name, birthDate, age, address, 
                phoneNumber, salary, email, pinCode, active)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement(query, PreparedStatement
                .RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setDate(2, Date.valueOf(employee
                    .getDateOfBirth()));
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getAddress());
            preparedStatement.setLong(5, employee.getPhoneNumber());
            preparedStatement.setDouble(6, employee.getSalary());
            preparedStatement.setString(7, employee.getEmail());
            preparedStatement.setInt(8, employee.getPinCode());
            preparedStatement.setBoolean(9, employee.isActive());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) 
                    employee.setId(resultSet.getInt(1));
            }
        } 

        return employee;
    }

    public Employee updateEmployee(int id, String email) throws SQLException, 
            ClassNotFoundException {
        String query = """
                UPDATE employee SET email = ? WHERE id = ? AND active = true
                """;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);

            int updatedEmployee = preparedStatement.executeUpdate();
            return updatedEmployee > 0 ? viewById(id) : null;
        }
    }

    public Employee deleteEmployee(int id) throws SQLException, 
            ClassNotFoundException {
        Employee employee = viewById(id);
    
        if (null == employee) 
            return null;

        String query = "UPDATE employee SET active = false WHERE id = ?";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        return employee;
    }

    public Employee viewById(int id) throws SQLException,
            ClassNotFoundException {
        String query = """
                SELECT id, name, birthDate, age, address, phoneNumber,
                        salary, email, pinCode, active
                FROM employee WHERE id = ? AND active = true
                """;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? mapEmployee(resultSet) : null;
            }
        }
    }

    public List<Employee> viewAll() throws SQLException,
            ClassNotFoundException {
        String query = """
                SELECT id, name, birthDate, age, address, phoneNumber,
                        salary, email, pinCode, active
                FROM employee
                WHERE id = ? AND active = true
                """;
        
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
  
            while (resultSet.next()) {
                employees.add(mapEmployee(resultSet));
            }
        } 
        return employees;
    }

    public boolean isEmailExist(String email) throws SQLException,
            ClassNotFoundException {
        boolean employeExists = false;
        String query = "SELECT id FROM employee WHERE email = ?";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement(query)) {
            
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                employeExists = resultSet.next();
            }
        } 
        return employeExists;
    }

    public boolean isEmployeePresent(int id) throws SQLException, 
            ClassNotFoundException {
        boolean employePresent = false;
        String query = """
                SELECT id FROM employee WHERE id = ? AND active = true
                """;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                employePresent = resultSet.next();
            }
        } catch (SQLException databaseException) {
            databaseException.printStackTrace();
        }

        return employePresent;
    }

    public Employee mapEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("birthDate").toLocalDate(),
                resultSet.getString("address"),
                resultSet.getLong("phoneNumber"),
                resultSet.getDouble("salary"),
                resultSet.getString("email"),
                resultSet.getInt("pinCode"),
                resultSet.getInt("age"),
                resultSet.getBoolean("active"));

        return employee;
    }
}