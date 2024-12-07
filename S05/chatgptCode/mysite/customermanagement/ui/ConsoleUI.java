package com.mysite.customermanagement.ui;

import com.mysite.customermanagement.model.BusinessCustomer;
import com.mysite.customermanagement.model.Customer;
import com.mysite.customermanagement.model.PersonalCustomer;
import com.mysite.customermanagement.service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private CustomerService customerService = CustomerService.getInstance(); // Get the Singleton instance

    public void startMenu() {
        int choice;
        do {
            choice = getMenuChoice();
            switch (choice) {
                case 1: // Add Customer
                    addCustomer();
                    break;
                case 2: // Print All Customers
                    printAllCustomers();
                    break;
                case 3: // Search and Print Customers by Name
                    searchAndPrintCustomerByName();
                    break;
                case 4: // Search and Print Customers by Family
                    searchAndPrintCustomersByFamily();
                    break;
                case 5: // Search and Edit Customers by Name
                    searchAndEditCustomerByName();
                    break;
                case 6: // Search and Delete Customers by Name
                    searchAndDeleteCustomerByName();
                    break;
                case 0: // Exit
                    printMessage("Exiting...");
                    break;
                default:
                    printMessage("Invalid option.");
            }
        } while (choice != 0);
    }

    private String getUserInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    private int getMenuChoice() {
        System.out.println("Menu:");
        System.out.println("0. Exit");
        System.out.println("1. Add Customer");
        System.out.println("2. Print All Customers");
        System.out.println("3. Search and Print Customers by Name");
        System.out.println("4. Search and Print Customers by Family");
        System.out.println("5. Search and Edit Customers by Name");
        System.out.println("6. Search and Delete Customers by Name");
        System.out.println();

        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private void addCustomer() {
        printMessage("Choose Customer Type:");
        printMessage("1. Personal");
        printMessage("2. Business");

        int typeChoice = Integer.parseInt(getUserInput("Enter your choice:"));

        String name = getUserInput("Enter customer name:");
        String number = getUserInput("Enter customer number:");

        if (typeChoice == 1) {
            String family = getUserInput("Enter family name:");
            PersonalCustomer personalCustomer = new PersonalCustomer(name, number);
            personalCustomer.setFamily(family);
            customerService.addCustomer(personalCustomer);
        } else {
            String fax = getUserInput("Enter fax number:");
            BusinessCustomer businessCustomer = new BusinessCustomer(name, number);
            businessCustomer.setFax(fax);
            customerService.addCustomer(businessCustomer);
        }
    }

    private void printAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            printMessage("No customers in the system.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private void searchAndPrintCustomerByName() {
        String name = getUserInput("Enter customer name to search:");
        Customer customer = customerService.getCustomerByName(name);
        if (customer != null) {
            printMessage(customer.toString());
        } else {
            printMessage("Customer not found.");
        }
    }

    private void searchAndPrintCustomersByFamily() {
        String family = getUserInput("Enter family name to search:");
        List<Customer> customers = customerService.getCustomersByFamily(family);
        if (customers.isEmpty()) {
            printMessage("No customers found for the family.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private void searchAndEditCustomerByName() {
        String name = getUserInput("Enter customer name to edit:");
        Customer customer = customerService.getCustomerByName(name);

        if (customer != null) {
            String newNumber = getUserInput("Enter new phone number:");
            customer.setNumber(newNumber);

            if (customer instanceof PersonalCustomer) {
                String newFamily = getUserInput("Enter new family name:");
                ((PersonalCustomer) customer).setFamily(newFamily);
            } else if (customer instanceof BusinessCustomer) {
                String newFax = getUserInput("Enter new fax number:");
                ((BusinessCustomer) customer).setFax(newFax);
            }

            printMessage("Customer updated: " + customer);
        } else {
            printMessage("Customer not found.");
        }
    }

    private void searchAndDeleteCustomerByName() {
        String name = getUserInput("Enter customer name to delete:");
        customerService.removeCustomerByName(name);
        printMessage("Customer deleted.");
    }
}
