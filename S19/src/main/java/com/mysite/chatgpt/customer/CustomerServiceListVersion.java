package com.mysite.chatgpt.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerServiceListVersion {
    private static final String DEFAULT_EXPORT_FILE = "customer_data.json";

    private static ArrayList<Customer> customers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String DATABASE_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static Connection connection = null;
    private static final String H2_CONSOLE_URL = "http://localhost:8082";

    public static void main(String[] args) {
        try {
            openConnection();
            startH2WebServer();
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        loadCustomerList();
        displayMenu();
        saveCustomerList();
    }

    private static void startH2WebServer() throws SQLException {
        org.h2.tools.Server server = org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "8082");
        server.start();
        System.out.println("H2 Web Server started at " + H2_CONSOLE_URL);
    }

    private static void openConnection() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL);
        System.out.println("Connected to database.");
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
                + "id INTEGER PRIMARY KEY AUTO_INCREMENT,"
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

    private static void saveCustomerList() {
        try {
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(new File(DEFAULT_EXPORT_FILE), customers);
            System.out.println("Customer list saved to " + DEFAULT_EXPORT_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while saving customer list.");
        }
    }

    private static void loadCustomerList() {
        try {
            File file = new File(DEFAULT_EXPORT_FILE);
            if (file.exists()) {
                CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, Customer.class);
                customers = objectMapper.readValue(file, listType);
                System.out.println("Customer list loaded from " + DEFAULT_EXPORT_FILE);
            } else {
                System.out.println("No customer data file found. Starting with an empty list.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while loading customer list.");
        }
    }
    public static void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Customer Service Menu:");
            System.out.println("1. Add Real Customer");
            System.out.println("2. Add Legal Customer");
            System.out.println("3. Find Customer by Name");
            System.out.println("4. Show All Customers");
            System.out.println("5. Export Customer List to JSON");
            System.out.println("6. Import Customer List from JSON");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addRealCustomer();
                    break;
                case 2:
                    addLegalCustomer();
                    break;
                case 3:
                    findCustomerByName();
                    break;
                case 4:
                    showAllCustomers();
                    break;
                case 5:
                    exportCustomerList();
                    break;
                case 6:
                    importCustomerList();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting the customer service. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
        scanner.close();
    }

    private static void importCustomerList() {
        System.out.print("Enter the name of the JSON file to import: ");
        String fileName = scanner.nextLine() + ".json";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File not found: " + fileName);
                return;
            }

            CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, Customer.class);

            List<Customer> importedCustomers = objectMapper.readValue(file, listType);
            customers.addAll(importedCustomers);

            System.out.println("Customer list imported from " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while importing customer list.");
        }
    }

    private static void exportCustomerList() {
        if (customers.isEmpty()) {
            System.out.println("No customers to export.");
            return;
        }

        System.out.print("Enter a name for the JSON file: ");
        String fileName = scanner.nextLine() + ".json";

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(fileWriter, customers);
            System.out.println("Customer list exported to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while exporting customer list.");
        }
    }

    private static void findCustomerByName() {
        System.out.print("Enter customer name to search: ");
        String name = scanner.nextLine();
        boolean found = false;
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                System.out.println("Customer found:");
                try {
                    String json = objectMapper.writeValueAsString(customer);
                    System.out.println(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                found = true;
            }
        }
        if (!found) {
            System.out.println("Customer with name \"" + name + "\" not found.");
        }
    }

    private static void addRealCustomer() {
        System.out.print("Enter real customer name: ");
        String name = scanner.nextLine();

        if (checkDuplicateName(name)) {
            return;
        }

        System.out.print("Enter real customer family: ");
        String family = scanner.nextLine();
        System.out.print("Enter real customer number: ");
        String number = scanner.nextLine();
        RealCustomer customer = new RealCustomer(name, family, number);
        customers.add(customer);
        System.out.println("Real Customer added successfully.");
    }

    private static void addLegalCustomer() {
        System.out.print("Enter legal customer name: ");
        String name = scanner.nextLine();

        if (checkDuplicateName(name)) {
            return;
        }

        System.out.print("Enter legal customer address: ");
        String address = scanner.nextLine();
        System.out.print("Enter legal customer number: ");
        String number = scanner.nextLine();
        LegalCustomer customer = new LegalCustomer(name, address, number);
        customers.add(customer);
        System.out.println("Legal Customer added successfully.");
    }

    private static void showAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("All Customers:");
            for (Customer customer : customers) {
                try {
                    String json = objectMapper.writeValueAsString(customer);
                    System.out.println(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean checkDuplicateName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                System.out.println("Customer with this name already exists. Please enter a different name.");
                return true;
            }
        }
        return false;
    }

}
