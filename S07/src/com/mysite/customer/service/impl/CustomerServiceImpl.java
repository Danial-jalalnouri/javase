package com.mysite.customer.service.impl;

import com.mysite.customer.model.Customer;
import com.mysite.customer.model.RealCustomer;
import com.mysite.customer.service.CustomerService;
import com.mysite.customer.service.exception.CustomerNotFindException;
import com.mysite.customer.service.exception.DuplicateCustomerException;
import com.mysite.customer.service.exception.EmptyCustomerException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {

    private static final CustomerServiceImpl INSTANCE;
    public static CustomerServiceImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new CustomerServiceImpl();
    }
    private CustomerServiceImpl(){

    }
    private ArrayList<Customer> customers = new ArrayList<>();

    @Override
    public void deleteCustomerById(Integer id) throws CustomerNotFindException {
        getCustomerById(id).setDeleted(true);
    }

    @Override
    public List<Customer> searchCustomersByFamily(String family) {
        return customers.stream()
                .filter(customer -> !customer.getDeleted())
                .filter(customer -> customer instanceof RealCustomer)
                .map(customer -> (RealCustomer) customer)
                .filter(realCustomer -> realCustomer.getFamily().equalsIgnoreCase(family))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> searchCustomersByName(String name) {
        return customers.stream()
                .filter(customer -> !customer.getDeleted())
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getActiveCustomers() throws EmptyCustomerException {
        List<Customer> collect = customers.stream()
                .filter(customer -> !customer.getDeleted())
                .collect(Collectors.toList());
        if(collect.isEmpty()){
            throw new EmptyCustomerException();
        }
        return collect;
    }

    @Override
    public List<Customer> getDeletedCustomers() throws EmptyCustomerException {
        List<Customer> collect = customers.stream()
                .filter(Customer::getDeleted)
                .collect(Collectors.toList());
        if(collect.isEmpty()){
            throw new EmptyCustomerException();
        }
        return collect;
    }

    @Override
    public Customer getCustomerById(Integer id) throws CustomerNotFindException {
        return customers.stream()
                .filter(customer -> !customer.getDeleted())
                .filter(customer -> customer.getId().equals(id))
                .findFirst().orElseThrow(CustomerNotFindException::new);
    }

    @Override
    public void addCustomer(Customer customer) throws DuplicateCustomerException {
        List<Customer> collect = customers.stream()
                .filter(it -> it.equals(customer))
                .findAny()
                .stream().toList();
        if(!collect.isEmpty())
            throw new DuplicateCustomerException();
        customers.add(customer);
    }
}
