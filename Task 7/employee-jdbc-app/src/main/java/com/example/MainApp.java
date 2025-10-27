package com.example;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final EmployeeDAO dao = new EmployeeDAO();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> addEmployeeFlow();
                case "2" -> viewAllFlow();
                case "3" -> viewByIdFlow();
                case "4" -> updateFlow();
                case "5" -> deleteFlow();
                case "6" -> {
                    System.out.println("Bye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Employee App ===");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. View Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Exit");
        System.out.print("Choose: ");
    }

    // Add Employee
    private static void addEmployeeFlow() {
        try {
            System.out.print("Name: "); String name = sc.nextLine();
            System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine());
            System.out.print("Department: "); String dept = sc.nextLine();
            System.out.print("Salary: "); double salary = Double.parseDouble(sc.nextLine());
            System.out.print("Email: "); String email = sc.nextLine();

            Employee e = new Employee(name, age, dept, salary, email);
            if (dao.addEmployee(e)) System.out.println("Added! ID = " + e.getId());
            else System.out.println("Add failed.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number input. Try again.");
        }
    }

    // View all Employees
    private static void viewAllFlow() {
        List<Employee> list = dao.getAllEmployees();
        if (list.isEmpty()) System.out.println("No employees found.");
        else list.forEach(System.out::println);
    }

    // View Employee by ID
    private static void viewByIdFlow() {
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(sc.nextLine());
            Employee e = dao.getEmployeeById(id);
            System.out.println(e == null ? "Not found." : e);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input.");
        }
    }

    // Update Employee
    private static void updateFlow() {
        try {
            System.out.print("Enter ID to update: "); 
            int id = Integer.parseInt(sc.nextLine());
            Employee e = dao.getEmployeeById(id);

            if (e == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.print("Name (" + e.getName() + "): ");
            String name = sc.nextLine();
            if (!name.isBlank()) e.setName(name);

            System.out.print("Age (" + e.getAge() + "): ");
            String ageStr = sc.nextLine();
            if (!ageStr.isBlank()) e.setAge(Integer.parseInt(ageStr));

            System.out.print("Department (" + e.getDepartment() + "): ");
            String dept = sc.nextLine();
            if (!dept.isBlank()) e.setDepartment(dept);

            System.out.print("Salary (" + e.getSalary() + "): ");
            String salStr = sc.nextLine();
            if (!salStr.isBlank()) e.setSalary(Double.parseDouble(salStr));

            System.out.print("Email (" + e.getEmail() + "): ");
            String email = sc.nextLine();
            if (!email.isBlank()) e.setEmail(email);

            if (dao.updateEmployee(e)) System.out.println("Updated.");
            else System.out.println("Update failed.");

        } catch (NumberFormatException ex) {
            System.out.println("Invalid number input. Update canceled.");
        }
    }

    // Delete Employee
    private static void deleteFlow() {
        try {
            System.out.print("Enter ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            if (dao.deleteEmployee(id)) System.out.println("Deleted.");
            else System.out.println("Delete failed (maybe ID not found).");

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input.");
        }
    }
}
