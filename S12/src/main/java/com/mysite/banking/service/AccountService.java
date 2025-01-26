package com.mysite.banking.service;

import com.mysite.banking.model.Account;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.EmptyAccountException;
import com.mysite.banking.service.exception.FileException;
import com.mysite.banking.service.exception.ValidationException;

import java.util.List;

public interface AccountService {
    void deleteAccountById(Integer id) throws AccountNotFindException;
    List<Account> getActiveAccounts() throws EmptyAccountException;
    List<Account> getDeletedAccounts() throws EmptyAccountException;
    Account getAccountById(Integer id) throws AccountNotFindException;
    void addAccount(Account account);
    void saveData(String name, FileType type) throws FileException;
    void loadData(String name, FileType fileType) throws FileException;
    void initData();
    void saveOnExit();
    void addData(String name) throws FileException;
    List<Account> getAccountByCustomerId(Integer id);

    void deposit(int accountId, Double amount) throws AccountNotFindException;

    void withdraw(int accountId, Double amount) throws AccountNotFindException, ValidationException;

    void transfer(int fromAccountId, int toAccountId, Double amount) throws AccountNotFindException, ValidationException;
}
