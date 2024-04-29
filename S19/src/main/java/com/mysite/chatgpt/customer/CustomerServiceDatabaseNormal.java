package com.mysite.chatgpt.customer;

import java.sql.*;
import java.util.Scanner;

public class CustomerServiceDatabaseNormal {
    private static final String DATABASE_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String H2_CONSOLE_URL = "http://localhost:8082";
    private static Connection connection = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            openConnection();
            startH2WebServer();
            createTables();
            printMenu();
            handleMenuChoice();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private static void openConnection() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL);
        System.out.println("Connected to database.");
    }

    private static void startH2WebServer() throws SQLException {
        org.h2.tools.Server server = org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "8082");
        server.start();
        System.out.println("H2 Web Server started at " + H2_CONSOLE_URL);
    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() throws SQLException {
        String createCustomerTableSQL = "CREATE TABLE IF NOT EXISTS customers ("
                + "id INTEGER AUTO_INCREMENT PRIMARY KEY,"
                + "name TEXT NOT NULL,"
                + "number TEXT NOT NULL,"
                + "type TEXT NOT NULL,"
                + "family TEXT,"
                + "address TEXT"
                + ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createCustomerTableSQL);
            System.out.println("Customer table created.");
        }
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Real Customer");
        System.out.println("2. Add Legal Customer");
        System.out.println("3. Show All Customers");
        System.out.println("4. Find Customer By Name");
        System.out.println("5. Exit");
    }

    private static void handleMenuChoice() throws SQLException {
        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addRealCustomer();
                    break;
                case 2:
                    addLegalCustomer();
                    break;
                case 3:
                    showAllCustomers();
                    break;
                case 4:
                    findCustomerByName();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);
    }

    private static void showAllCustomers() throws SQLException {
        String selectCustomersSQL = "SELECT * FROM customers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectCustomersSQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String number = resultSet.getString("number");
                String typeString = resultSet.getString("type");
                CustomerType type = CustomerType.valueOf(typeString);
                String family = resultSet.getString("family");
                String address = resultSet.getString("address");

                Customer customer;
                if (type == CustomerType.REAL) {
                    customer = new RealCustomer(id, name, number, family);
                } else {
                    customer = new LegalCustomer(id, name, number, address);
                }
                System.out.println(customer.toString());
            }
        }
    }

    private static void addCustomer(Customer customer) throws SQLException {
        String insertCustomerSQL = "INSERT INTO customers (name, number, type, family, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerSQL)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getNumber());
            preparedStatement.setString(3, customer.getType().toString());
            if (customer instanceof RealCustomer) {
                RealCustomer realCustomer = (RealCustomer) customer;
                preparedStatement.setString(4, realCustomer.getFamily());
                preparedStatement.setString(5, null);
            } else if (customer instanceof LegalCustomer) {
                LegalCustomer legalCustomer = (LegalCustomer) customer;
                preparedStatement.setString(4, null);
                preparedStatement.setString(5, legalCustomer.getAddress());
            }
            preparedStatement.executeUpdate();
            System.out.println("Customer added to database.");
        }
    }

    private static void addRealCustomer() throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter number: ");
        String number = scanner.nextLine();
        System.out.print("Enter family: ");
        String family = scanner.nextLine();
        RealCustomer realCustomer = new RealCustomer(0, name, number, family);
        addCustomer(realCustomer);
    }

    private static void addLegalCustomer() throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter number: ");
        String number = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        LegalCustomer legalCustomer = new LegalCustomer(0, name, number, address);
        addCustomer(legalCustomer);
    }

    private static void printAllCustomers() throws SQLException {
        String selectCustomersSQL = "SELECT * FROM customers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectCustomersSQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String number = resultSet.getString("number");
                String typeString = resultSet.getString("type");
                CustomerType type = CustomerType.valueOf(typeString);
                String family = resultSet.getString("family");
                String address = resultSet.getString("address");

                Customer customer;
                if (type == CustomerType.REAL) {
                    customer = new RealCustomer(id, name, number, family);
                } else {
                    customer = new LegalCustomer(id, name, number, address);
                }
                System.out.println(customer.toString());
            }
        }
    }

    private static void findCustomerByName() throws SQLException {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();
        String selectCustomerByNameSQL = "SELECT * FROM customers WHERE name LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCustomerByNameSQL)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String customerName = resultSet.getString("name");
                    String number = resultSet.getString("number");
                    String typeString = resultSet.getString("type");
                    CustomerType type = CustomerType.valueOf(typeString);
                    String family = resultSet.getString("family");
                    String address = resultSet.getString("address");

                    Customer customer;
                    if (type == CustomerType.REAL) {
                        customer = new RealCustomer(id, customerName, number, family);
                    } else {
                        customer = new LegalCustomer(id, customerName, number, address);
                    }
                    System.out.println(customer.toString());
                }
            }
        }
    }

}
