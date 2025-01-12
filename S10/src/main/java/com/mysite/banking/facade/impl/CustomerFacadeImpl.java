package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.facade.CustomerFacade;
import com.mysite.banking.mapper.CustomerMapstruct;
import com.mysite.banking.model.Customer;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.CustomerService;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.service.impl.CustomerServiceImpl;
import com.mysite.banking.service.validation.ValidationContext;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class CustomerFacadeImpl implements CustomerFacade {
    private ValidationContext<CustomerDto> validationContext;
    private final CustomerService customerService;
    private final CustomerMapstruct customerMapstruct;
    private static final CustomerFacadeImpl INSTANCE;
    public static CustomerFacadeImpl getInstance(){
        return INSTANCE;
    }
    static {
        INSTANCE = new CustomerFacadeImpl();
    }
    private CustomerFacadeImpl() {
        this.customerMapstruct = Mappers.getMapper(CustomerMapstruct.class);
        this.customerService = CustomerServiceImpl.getInstance();
        this.validationContext = new CustomerValidationContext();
    }

    @Override
    public void deleteCustomerById(Integer id) throws CustomerNotFindException {
        customerService.deleteCustomerById(id);
    }

    @Override
    public List<CustomerDto> searchCustomersByFamily(String family) {
        return customerMapstruct.mapToCustomerDtoList(
                customerService.searchCustomersByFamily(family));
    }

    @Override
    public List<CustomerDto> searchCustomersByName(String name) {
        return customerMapstruct.mapToCustomerDtoList(
                customerService.searchCustomersByName(name));
    }

    @Override
    public List<CustomerDto> getActiveCustomers() throws EmptyCustomerException {
        return customerMapstruct.mapToCustomerDtoList(
                customerService.getActiveCustomers());
    }

    @Override
    public List<CustomerDto> getDeletedCustomers() throws EmptyCustomerException {
        return customerMapstruct.mapToCustomerDtoList(
                customerService.getDeletedCustomers());
    }

    @Override
    public CustomerDto getCustomerById(Integer id) throws CustomerNotFindException {
        return customerMapstruct.mapToCustomerDto(customerService.getCustomerById(id));
    }

    @Override
    public void addCustomer(CustomerDto customer) throws DuplicateCustomerException, ValidationException {
        validationContext.validate(customer);
        customerService.addCustomer(customerMapstruct.mapToCustomer(customer));
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) throws ValidationException, CustomerNotFindException {
        validationContext.validate(customerDto);
        Customer customer = customerService.getCustomerById(customerDto.getId());
        customerMapstruct.mapToCustomer(customerDto, customer);
    }

    @Override
    public void saveData(String name, FileType type) throws FileException {
        customerService.saveData(name, type);
    }

    @Override
    public void loadData(String name, FileType fileType) throws FileException {
        customerService.loadData(name, fileType);
    }

    @Override
    public void initData() {
        customerService.initData();
    }

    @Override
    public void saveOnExit() {
        customerService.saveOnExit();
    }

    @Override
    public void addData(String name) throws FileException {
        customerService.addData(name);
    }
}
