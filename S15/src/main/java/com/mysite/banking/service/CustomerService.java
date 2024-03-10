package com.mysite.banking.service;

import com.mysite.banking.model.Customer;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;
import java.util.List;

public interface CustomerService {
    void deleteCustomerById(Integer id) throws CustomerNotFindException;
    List<Customer> searchCustomersByFamily(String family);

    Customer searchCustomersByEmail(String email) throws CustomerNotFindException;

    List<Customer> searchCustomersByName(String name);
    List<Customer> getActiveCustomers() throws EmptyCustomerException;
    List<Customer> getDeletedCustomers() throws EmptyCustomerException;
    Customer getCustomerById(Integer id) throws CustomerNotFindException;
    void addCustomer(Customer customer) throws DuplicateCustomerException;
    void updateCustomer(Customer customer);
    Boolean login(String username, String password);
}
