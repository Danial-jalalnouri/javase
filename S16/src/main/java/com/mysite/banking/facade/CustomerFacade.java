package com.mysite.banking.facade;

import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.service.exception.*;

import java.util.List;

public interface CustomerFacade {
    void deleteCustomerById(Integer id) throws CustomerNotFindException;
    List<CustomerDto> searchCustomersByFamily(String family);
    List<CustomerDto> searchCustomersByName(String name);
    CustomerDto searchCustomersByEmail(String email) throws CustomerNotFindException;
    List<CustomerDto> getActiveCustomers() throws EmptyCustomerException;
    List<CustomerDto> getDeletedCustomers() throws EmptyCustomerException;
    CustomerDto getCustomerById(Integer id) throws CustomerNotFindException;
    void addCustomer(CustomerDto customer) throws DuplicateCustomerException, ValidationException;
    void updateCustomer(CustomerDto customer) throws ValidationException, CustomerNotFindException;
    Boolean login(String username, String password);
    void exportJson(String fileName);
}
