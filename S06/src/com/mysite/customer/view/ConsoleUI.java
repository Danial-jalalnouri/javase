package com.mysite.customer.view;

import com.mysite.customer.model.Customer;
import com.mysite.customer.model.CustomerType;
import com.mysite.customer.service.CustomerService;
import com.mysite.customer.service.impl.CustomerServiceImpl;
import com.mysite.customer.util.ScannerWrapper;
import com.mysite.customer.view.component.AbstractCustomerUI;

import java.util.List;
import java.util.function.Function;

public class ConsoleUI implements AutoCloseable {
    private final ScannerWrapper scannerWrapper;
    private final CustomerService customerService;

    public ConsoleUI(){
        scannerWrapper  = ScannerWrapper.getInstance();
        customerService = CustomerServiceImpl.getInstance();
    }

    public void startMenu(){
        int choice;
        do {
            printMenu();
            choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    printAllCustomers();
                    break;
                case 0:
                    System.out.println("Exit!");
                    break;
                case 3:
                    searchAndPrintCustomersByName();
                    break;
                case 4:
                    searchAndPrintCustomersByFamily();
                    break;
                case 5:
                    editCustomerById();
                    break;
                case 6:
                    deleteCustomerById();
                    break;
                case 7:
                    printAllDeletedCustomers();
                    break;
                default:
                    System.out.println("Invalid number.");
            }
        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("0. Exit");
        System.out.println("1. Add Customer");
        System.out.println("2. Print All Customers");
        System.out.println("3. Search and print customers by name");
        System.out.println("4. Search and print customers by family");
        System.out.println("5. Edit customer by id");
        System.out.println("6. Delete customer by id");
        System.out.println("7. Print All Deleted Customers");
        System.out.println();
    }

    @Override
    public void close() {
        scannerWrapper.close();
    }

    private void addCustomer() {
        System.out.println("Customer Type:");
        System.out.println("1. Real");
        System.out.println("2. Legal (2 or other numbers)");
        int choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);

        Customer customer = AbstractCustomerUI
                .fromCustomerType(CustomerType
                        .fromValue(choice))
                .generateCustomer();
        customerService.addCustomer(customer);
    }

    private void printAllCustomers() {
        List<Customer> allCustomers = customerService.getActiveCustomers();
        if(allCustomers.isEmpty()){
            System.out.println("There is no customer!");
        }else{
            System.out.println("All Customers:");
            for (Customer customer : allCustomers) {
                System.out.println(customer);
            }
        }
    }

    private void printAllDeletedCustomers() {
        List<Customer> allCustomers = customerService.getDeletedCustomers();
        if(allCustomers.isEmpty()){
            System.out.println("There is no deleted customer!");
        }else{
            System.out.println("All Deleted Customers:");
            for (Customer customer : allCustomers) {
                System.out.println(customer);
            }
        }
    }

    private void searchAndPrintCustomersByName() {
        String name = scannerWrapper.getUserInput("Enter the name: ", Function.identity());
        List<Customer> customers = customerService.searchCustomersByName(name);
        customers.forEach(System.out::println);
    }

    private void searchAndPrintCustomersByFamily() {
        String family = scannerWrapper.getUserInput("Enter the family: ", Function.identity());
        List<Customer> customers = customerService.searchCustomersByFamily(family);
        customers.forEach(System.out::println);
    }

    private void editCustomerById() {
        String id = scannerWrapper.getUserInput("Enter the customer id: ", Function.identity());
        Customer customer = customerService.getCustomerById(Integer.valueOf(id));
        System.out.println(customer);

        AbstractCustomerUI
                .fromCustomerType(customer.getType())
                .editCustomer(customer);
    }

    private void deleteCustomerById() {
        String id = scannerWrapper.getUserInput("Enter the customer id: ", Function.identity());
        customerService.deleteCustomerById(Integer.valueOf(id));
    }
}
