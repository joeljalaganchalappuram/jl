/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main.java;
import java.sql.*;
import java.util.Scanner;

public class MainJava {
    static final String DB_URL = "jdbc:mysql://localhost:3306/company?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "Joel*123*abc"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            int choice;
            do {
                System.out.println("\n====== Employee Management ======");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addEmployee(conn, scanner);
                    case 2 -> viewEmployees(conn);
                    case 3 -> updateSalary(conn, scanner);
                    case 4 -> deleteEmployee(conn, scanner);
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice. Try again.");
                }

            } while (choice != 5);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception e) { }
            scanner.close();
        }
    }

    private static void addEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();

        String sql = "INSERT INTO employees (name, salary) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, salary);
            pstmt.executeUpdate();
            System.out.println("Employee added successfully.");
        }
    }

    private static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID | Name           | Salary");
            System.out.println("-------------------------------");
            while (rs.next()) {
                System.out.printf("%-3d| %-14s| %.2f\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary"));
            }
        }
    }

    private static void updateSalary(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new salary: ");
        double salary = scanner.nextDouble();

        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, salary);
            pstmt.setInt(2, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println("Salary updated successfully.");
            else
                System.out.println("Employee not found.");
        }
    }

    private static void deleteEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter employee ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println("Employee deleted successfully.");
            else
                System.out.println("Employee not found.");
        }
    }
}
