package com.mysite.banking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.banking.dao.CustomerDao;
import com.mysite.banking.dao.impl.CustomerDaoImpl;
import com.mysite.banking.model.Customer;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.service.CustomerService;
import com.mysite.banking.util.GlobalAttributes;
import com.mysite.banking.util.MapperWrapper;
import com.mysite.banking.util.PasswordEncoderUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomerServiceImpl implements CustomerService {
    private static final CustomerServiceImpl INSTANCE;

    public static CustomerServiceImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new CustomerServiceImpl();
    }

    private final ObjectMapper objectMapper;

    private final GlobalAttributes globalAttributes;

    private CustomerDao customerDao;

    private CustomerServiceImpl(){
        globalAttributes = GlobalAttributes.getInstance();
        customerDao = CustomerDaoImpl.getInstance();
        objectMapper = MapperWrapper.getInstance();
    }

    @Override
    public void deleteCustomerById(Integer id) throws CustomerNotFindException {
        Customer customerById = getCustomerById(id);
        customerDao.delete(customerById);
    }

    @Override
    public List<Customer> searchCustomersByFamily(String family) {
        return customerDao.getByFamily(family);
    }

    @Override
    public Customer searchCustomersByEmail(String email) throws CustomerNotFindException {
        Customer firstByEmail = customerDao.getFirstByEmail(email);
        if(firstByEmail == null){
            throw new CustomerNotFindException();
        }else{
            return firstByEmail;
        }
    }

    @Override
    public List<Customer> searchCustomersByName(String name) {
        return customerDao.getByName(name);
    }

    @Override
    public List<Customer> getActiveCustomers() throws EmptyCustomerException {
        List<Customer> customerList = customerDao.getByStatus(false);
        if(customerList.isEmpty()){
            throw new EmptyCustomerException();
        }
        return customerList;
    }

    @Override
    public List<Customer> getDeletedCustomers() throws EmptyCustomerException {
        List<Customer> customerList = customerDao.getByStatus(true);
        if(customerList.isEmpty()){
            throw new EmptyCustomerException();
        }
        return customerList;
    }

    @Override
    public Customer getCustomerById(Integer id) throws CustomerNotFindException {
        Customer customer = customerDao.getById(id);
        if(customer == null){
            throw new CustomerNotFindException();
        }else{
            return customer;
        }
    }

    @Override
    public void addCustomer(Customer customer) throws DuplicateCustomerException {
        Integer id = customerDao.save(customer);
        customer.setPassword(PasswordEncoderUtil.encodePassword(customer.getPassword(), id));
        customerDao.update(customer);

    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public Boolean login(String username, String password) {
        try {
            Customer customer = searchCustomersByEmail(username);
            if(Objects.equals(
                    customer.getPassword(),
                    PasswordEncoderUtil.encodePassword(password,customer.getId()))){
                globalAttributes.setCustomerId(customer.getId());
                return true;
            }else{
                globalAttributes.setCustomerId(null);
                return false;
            }
        } catch (CustomerNotFindException e) {
            globalAttributes.setCustomerId(null);
            return false;
        }
    }

    @Override
    public void exportJson(String fileName) {
        File file = new File(fileName + ".json");
        try {
            file.createNewFile();
            List<Customer> customerList = customerDao.getAll();
            objectMapper.writeValue(file,customerList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
