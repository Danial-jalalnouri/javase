package com.mysite.customer.facade;

import com.mysite.customer.dto.CustomerDto;
import com.mysite.customer.model.FileType;
import com.mysite.customer.service.exception.*;

import java.util.List;

public interface CustomerFacade {
    void deleteCustomerById(Integer id) throws CustomerNotFindException;
    List<CustomerDto> searchCustomersByFamily(String family);
    List<CustomerDto> searchCustomersByName(String name);
    List<CustomerDto> getActiveCustomers() throws EmptyCustomerException;
    List<CustomerDto> getDeletedCustomers() throws EmptyCustomerException;
    CustomerDto getCustomerById(Integer id) throws CustomerNotFindException;
    void addCustomer(CustomerDto customer) throws DuplicateCustomerException, ValidationException;
    void updateCustomer(CustomerDto customer) throws ValidationException, CustomerNotFindException;
    void saveData(String name, FileType type) throws FileException;

    void loadData(String name, FileType fileType) throws FileException;

    void initData();

    void saveOnExit();

    void addData(String name) throws FileException;
}
