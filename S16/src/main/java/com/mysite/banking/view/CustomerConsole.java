package com.mysite.banking.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.facade.CustomerFacade;
import com.mysite.banking.facade.impl.CustomerFacadeImpl;
import com.mysite.banking.model.CustomerType;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.view.component.AbstractCustomerUI;

import java.util.List;
import java.util.function.Function;

public class CustomerConsole extends BaseConsole {
    final CustomerFacade customerFacade;

    public CustomerConsole() {
        customerFacade = CustomerFacadeImpl.getInstance();
    }

    private void printCustomerMenu() {
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println("1. Add Customer");
        System.out.println("2. Print All Customers");
        System.out.println("3. Search and print customers by name");
        System.out.println("4. Search and print customers by family");
        System.out.println("5. Edit customer by id");
        System.out.println("6. Delete customer by id");
        System.out.println("7. Print All Deleted Customers");
        System.out.println("8. Export data as JSON");
        System.out.println("11. Login");
        System.out.println();
    }
    public void menu(){
        int choice;
        do {
            printCustomerMenu();
            choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
            try {
                switch (choice) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        printAllCustomers();
                        break;
                    case 0:
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
                    case 8:
                        exportJson();
                        break;
                    case 11:
                        login();
                        break;
                    default:
                        System.out.println("Invalid number.");
                }
            }catch (CustomerNotFindException | EmptyCustomerException ex){
                System.out.println(ex.getMessage());
            }
        } while (choice != 0);
    }

    private void exportJson() {
        String fileName = scannerWrapper.getUserInput("Enter the file name: ", Function.identity());
        customerFacade.exportJson(fileName);
    }

    private void login(){
        String username = scannerWrapper.getUserInput("Enter your email: ", Function.identity());
        String password = scannerWrapper.getUserInput("Enter your password: ", Function.identity());
        Boolean validate = customerFacade.login(username, password);
        if(validate){
            System.out.println("Welcome to the system.");
        }else{
            System.out.println("User or pass is wrong!");
        }
    }

    private void addCustomer() {
        System.out.println("Customer Type:");
        System.out.println("1. Real");
        System.out.println("2. Legal");
        int choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);

        try {
            customerFacade.addCustomer(AbstractCustomerUI
                    .fromCustomerType(CustomerType
                            .fromValue(choice))
                    .generateCustomer());
        } catch (DuplicateCustomerException e) {
            System.out.println("It's not possible to select duplicate name and family.");
            addCustomer();
        } catch (InvalidType e) {
            System.out.println("Invalid customer type!");
            addCustomer();
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            addCustomer();
        }
    }
    private void printAllCustomers() throws EmptyCustomerException {
        List<CustomerDto> allCustomers = customerFacade.getActiveCustomers();
        System.out.println("All Customers:");
        for (CustomerDto customer : allCustomers) {
            try {
                System.out.println(objectMapper.writeValueAsString(customer));
            } catch (JsonProcessingException e) {
                System.out.println("Error on print customer id " + customer.getId());
            }
        }
    }
    private void printAllDeletedCustomers() throws EmptyCustomerException {
        List<CustomerDto> allCustomers = customerFacade.getDeletedCustomers();
        System.out.println("All Deleted Customers:");
        for (CustomerDto customer : allCustomers) {
            System.out.println(customer);
        }
    }
    private void searchAndPrintCustomersByName() {
        String name = scannerWrapper.getUserInput("Enter the name: ", Function.identity());
        List<CustomerDto> customers = customerFacade.searchCustomersByName(name);
        customers.forEach(System.out::println);
    }

    private void searchAndPrintCustomersByFamily() {
        String family = scannerWrapper.getUserInput("Enter the family: ", Function.identity());
        List<CustomerDto> customers = customerFacade.searchCustomersByFamily(family);
        customers.forEach(System.out::println);
    }
    private void editCustomerById() throws CustomerNotFindException {
        String id = scannerWrapper.getUserInput("Enter the customer id: ", Function.identity());
        CustomerDto customerDto = customerFacade.getCustomerById(Integer.valueOf(id));
        System.out.println(customerDto);

        AbstractCustomerUI
                .fromCustomerType(customerDto.getType())
                .editCustomer(customerDto);

        try {
            customerFacade.updateCustomer(customerDto);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            editCustomerById();
        }
    }
    private void deleteCustomerById() throws CustomerNotFindException {
        String id = scannerWrapper.getUserInput("Enter the customer id: ", Function.identity());
        customerFacade.deleteCustomerById(Integer.valueOf(id));
    }
}
