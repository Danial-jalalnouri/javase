package com.mysite.customer.service;

import com.mysite.customer.model.Customer;
import com.mysite.customer.service.exception.CustomerNotFindException;
import com.mysite.customer.service.exception.DuplicateCustomerException;
import com.mysite.customer.service.exception.EmptyCustomerException;

import java.util.List;

public interface CustomerService {
    void deleteCustomerById(Integer id) throws CustomerNotFindException;
    List<Customer> searchCustomersByFamily(String family);
    List<Customer> searchCustomersByName(String name);
    List<Customer> getActiveCustomers() throws EmptyCustomerException;
    List<Customer> getDeletedCustomers() throws EmptyCustomerException;
    Customer getCustomerById(Integer id) throws CustomerNotFindException;
    void addCustomer(Customer customer) throws DuplicateCustomerException;
}
