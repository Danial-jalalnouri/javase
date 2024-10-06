
package com.banking;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class CustomerManager {
    private Connection connection;

    public CustomerManager() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:bankingdb", "sa", "");
            String createTable = "CREATE TABLE IF NOT EXISTS customers (" +
                                 "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                 "name VARCHAR(255), " +
                                 "email VARCHAR(255), " +
                                 "is_deleted BOOLEAN DEFAULT FALSE)";
            Statement stmt = connection.createStatement();
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void customerMenu(Scanner scanner) {
        while (true) {
            System.out.println("Customer Menu:");
            System.out.println("0. Back");
            System.out.println("1. Add customer");
            System.out.println("2. Print all customers");
            System.out.println("3. Search by name");
            System.out.println("4. Edit customer by ID");
            System.out.println("5. Delete customer by ID");
            System.out.println("6. Print all deleted customers");
            System.out.println("7. Export data as JSON");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                addCustomer(scanner);
            } else if (choice == 2) {
                printAllCustomers(false);
            } else if (choice == 3) {
                searchCustomerByName(scanner);
            } else if (choice == 4) {
                editCustomerById(scanner);
            } else if (choice == 5) {
                deleteCustomerById(scanner);
            } else if (choice == 6) {
                printAllCustomers(true);
            } else if (choice == 7) {
                exportCustomersToJson(scanner);
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void addCustomer(Scanner scanner) {
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter customer email:");
        String email = scanner.nextLine();

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        try {
            String insertSQL = "INSERT INTO customers (name, email) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("Customer added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printAllCustomers(boolean showDeleted) {
        try {
            String query = showDeleted ? "SELECT * FROM customers WHERE is_deleted = TRUE"
                                       : "SELECT * FROM customers WHERE is_deleted = FALSE";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                                   ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchCustomerByName(Scanner scanner) {
        System.out.println("Enter customer name to search:");
        String name = scanner.nextLine();

        try {
            String query = "SELECT * FROM customers WHERE name LIKE ? AND is_deleted = FALSE";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                                   ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editCustomerById(Scanner scanner) {
        System.out.println("Enter customer ID to edit:");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.println("Enter new customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter new customer email:");
        String email = scanner.nextLine();

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        try {
            String updateSQL = "UPDATE customers SET name = ?, email = ? WHERE id = ? AND is_deleted = FALSE";
            PreparedStatement pstmt = connection.prepareStatement(updateSQL);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer updated.");
            } else {
                System.out.println("Customer not found or already deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomerById(Scanner scanner) {
        System.out.println("Enter customer ID to delete:");
        int id = scanner.nextInt();

        try {
            String deleteSQL = "UPDATE customers SET is_deleted = TRUE WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteSQL);
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer deleted.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void exportCustomersToJson(Scanner scanner) {
        System.out.println("Enter filename for JSON export:");
        String filename = scanner.nextLine();

        try {
            String query = "SELECT * FROM customers WHERE is_deleted = FALSE";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Gson gson = new Gson();
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            while (rs.next()) {
                if (sb.length() > 1) sb.append(", ");
                sb.append(gson.toJson(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"))));
            }

            sb.append("]");

            try (FileWriter writer = new FileWriter(filename)) {
                writer.write(sb.toString());
                System.out.println("Data exported as JSON to " + filename);
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        return true;
    }
}

class Customer {
    private int id;
    private String name;
    private String email;

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
