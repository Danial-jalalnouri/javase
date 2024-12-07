package com.mysite.customermanagement.service;

import com.mysite.customermanagement.model.BusinessCustomer;
import com.mysite.customermanagement.model.Customer;
import com.mysite.customermanagement.model.PersonalCustomer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    // Step 1: Create a static instance of the CustomerService
    private static CustomerService instance;

    // Private constructor to prevent instantiation from outside
    private CustomerService() {
        // private constructor to prevent instantiation
    }

    // Step 2: Provide a public method to access the instance
    public static CustomerService getInstance() {
        if (instance == null) {
            synchronized (CustomerService.class) {
                if (instance == null) {
                    instance = new CustomerService();
                }
            }
        }
        return instance;
    }

    private List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Customer getCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    public void removeCustomerByName(String name) {
        customers.removeIf(customer -> customer.getName().equalsIgnoreCase(name));
    }

    public List<Customer> getCustomersByFamily(String family) {
        List<Customer> familyCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer instanceof PersonalCustomer) {
                PersonalCustomer personalCustomer = (PersonalCustomer) customer;
                if (personalCustomer.getFamily().equalsIgnoreCase(family)) {
                    familyCustomers.add(customer);
                }
            }
        }
        return familyCustomers;
    }
}
