package com.mysite.banking.service;

import com.mysite.banking.model.Customer;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.CustomerNotFindException;
import com.mysite.banking.service.exception.DuplicateCustomerException;
import com.mysite.banking.service.exception.EmptyCustomerException;
import com.mysite.banking.service.exception.FileException;

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
    void saveData(String name, FileType type) throws FileException;
    void loadData(String name, FileType fileType) throws FileException;

    void initData();

    void saveOnExit();

    void addData(String name) throws FileException;

    Boolean login(String username, String password);
}
