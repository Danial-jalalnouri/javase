package com.mysite.customer.service;

import com.mysite.customer.model.Customer;
import com.mysite.customer.model.FileType;
import com.mysite.customer.service.exception.*;

import java.util.List;

public interface CustomerService {
    void deleteCustomerById(Integer id) throws CustomerNotFindException;
    List<Customer> searchCustomersByFamily(String family);
    List<Customer> searchCustomersByName(String name);
    List<Customer> getActiveCustomers() throws EmptyCustomerException;
    List<Customer> getDeletedCustomers() throws EmptyCustomerException;
    Customer getCustomerById(Integer id) throws CustomerNotFindException;
    void addCustomer(Customer customer) throws DuplicateCustomerException, ValidationException;
    void saveData(String name, FileType type) throws FileException;
    void loadData(String name, FileType fileType) throws FileException;

    void initData();

    void saveOnExit();

    void addData(String name) throws FileException;
}
