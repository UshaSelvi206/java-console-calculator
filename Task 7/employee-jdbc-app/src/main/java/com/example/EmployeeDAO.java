package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmployeeDAO {

    // Add new employee
    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employee (name, age, department, salary, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emp.getName());
            ps.setInt(2, emp.getAge());
            ps.setString(3, emp.getDepartment());
            ps.setDouble(4, emp.getSalary());
            ps.setString(5, emp.getEmail());

            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) emp.setId(keys.getInt(1));
            }
            return true;

        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT id, name, age, department, salary, email FROM employee ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("email")
                );
                list.add(e);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    // Get employee by ID
    public Employee getEmployeeById(int id) {
        String sql = "SELECT id, name, age, department, salary, email FROM employee WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("department"),
                            rs.getDouble("salary"),
                            rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    // Update employee
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employee SET name = ?, age = ?, department = ?, salary = ?, email = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setInt(2, emp.getAge());
            ps.setString(3, emp.getDepartment());
            ps.setDouble(4, emp.getSalary());
            ps.setString(5, emp.getEmail());
            ps.setInt(6, emp.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }

    // Delete employee
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }

    // Print detailed SQL exception
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException sqlEx) {
                sqlEx.printStackTrace();
                System.err.println("SQLState: " + sqlEx.getSQLState());
                System.err.println("Error Code: " + sqlEx.getErrorCode());
                System.err.println("Message: " + sqlEx.getMessage());
            }
        }
    }
}
