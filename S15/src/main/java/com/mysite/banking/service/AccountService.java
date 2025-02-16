package com.mysite.banking.service;

import com.mysite.banking.model.Account;
import com.mysite.banking.model.Amount;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

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

    void deposit(int accountId, Amount amount) throws AccountNotFindException;

    void withdraw(int accountId, Amount amount) throws AccountNotFindException, ValidationException;

    void transfer(int fromAccountId, int toAccountId, Amount amount) throws AccountNotFindException, ValidationException;
}
