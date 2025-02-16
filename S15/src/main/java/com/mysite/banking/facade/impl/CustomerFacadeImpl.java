package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.model.Customer;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.service.validation.ValidationContext;
import com.mysite.banking.facade.CustomerFacade;
import com.mysite.banking.mapper.CustomerMapstruct;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.CustomerService;
import com.mysite.banking.service.impl.CustomerServiceImpl;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class CustomerFacadeImpl implements CustomerFacade {
    private ValidationContext<CustomerDto> validationContext;
    private CustomerService customerService;
    private final CustomerMapstruct customerMapstruct;
    private static final CustomerFacadeImpl INSTANCE;
    public static CustomerFacadeImpl getInstance(){
        return INSTANCE;
    }

    public static CustomerFacadeImpl getInstance(CustomerService customerService){
        INSTANCE.validationContext = new CustomerValidationContext(INSTANCE);
        INSTANCE.customerService = customerService;
        return INSTANCE;
    }

    static {
        INSTANCE = new CustomerFacadeImpl();
    }
    private CustomerFacadeImpl() {
        this.customerMapstruct = Mappers.getMapper(CustomerMapstruct.class);
        this.customerService = CustomerServiceImpl.getInstance();
        this.validationContext = new CustomerValidationContext(this);
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
    public CustomerDto searchCustomersByEmail(String email) throws CustomerNotFindException {
        return customerMapstruct.mapToCustomerDto(customerService.searchCustomersByEmail(email));
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
        customerService.updateCustomer(customer);
    }

    @Override
    public Boolean login(String username, String password) {
        return customerService.login(username, password);
    }
}
