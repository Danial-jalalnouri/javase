package com.mysite.banking.dao;

import com.mysite.banking.model.Customer;

import java.util.List;

public interface CustomerDao {
    Integer save(Customer customer);
    void delete(Customer customer);
    void update(Customer customer);
    Customer getById(Integer id);
    List<Customer> getByStatus(Boolean deleted);
    List<Customer> getAll();
    List<Customer> getByFamily(String family);
    Customer getFirstByEmail(String email);
    List<Customer> getByName(String name);
}
