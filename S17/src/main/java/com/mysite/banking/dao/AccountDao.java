package com.mysite.banking.dao;

import com.mysite.banking.model.Account;

import java.util.List;

public interface AccountDao {
    Integer save(Account account);
    void delete(Account account);
    void update(Account account);
    Account getById(Integer id);
    List<Account> getByStatus(Boolean deleted);
    List<Account> getByCustomerId(Integer customerId);

    List<Account> getAll();
}
