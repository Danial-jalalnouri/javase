package com.mysite.customer.service.impl;

import com.mysite.customer.model.Customer;
import com.mysite.customer.model.RealCustomer;
import com.mysite.customer.service.CustomerService;

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
    public void deleteCustomerById(Integer id) {
        customers.stream()
                .filter(customer -> !customer.getDeleted())
                .filter(customer -> customer.getId().equals(id))
                .forEach(customer -> customer.setDeleted(true));
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
    public List<Customer> getActiveCustomers() {
        return customers.stream()
                .filter(customer -> !customer.getDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getDeletedCustomers() {
        return customers.stream()
                .filter(customer -> customer.getDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public Customer getCustomerById(Integer id){
        return customers.stream()
                .filter(customer -> !customer.getDeleted())
                .filter(customer -> customer.getId().equals(id))
                .findFirst().get();
    }

    @Override
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
