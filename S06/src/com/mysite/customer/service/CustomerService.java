package com.mysite.customer.service;

import com.mysite.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    void deleteCustomerById(Integer id);
    List<Customer> searchCustomersByFamily(String family);
    List<Customer> searchCustomersByName(String name);
    List<Customer> getActiveCustomers();
    List<Customer> getDeletedCustomers();
    Customer getCustomerById(Integer id);
    void addCustomer(Customer customer);
}
