package com.mysite.banking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.banking.dao.AccountDao;
import com.mysite.banking.dao.impl.AccountDaoImpl;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.Amount;
import com.mysite.banking.model.Customer;
import com.mysite.banking.service.AccountService;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.util.AmountUtil;
import com.mysite.banking.util.MapperWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static final AccountServiceImpl INSTANCE;
    public static AccountServiceImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new AccountServiceImpl();
    }

    private AmountUtil amountUtil;

    private AccountDao accountDao;

    private final ObjectMapper objectMapper;

    private AccountServiceImpl(){
        amountUtil = AmountUtil.getInstance();
        accountDao = AccountDaoImpl.getInstance();
        objectMapper = MapperWrapper.getInstance();
    }

    @Override
    public void deleteAccountById(Integer id) throws AccountNotFindException {
        Account accountById = getAccountById(id);
        accountDao.delete(accountById);
    }

    @Override
    public List<Account> getActiveAccounts() throws EmptyAccountException {
        List<Account> accountList = accountDao.getByStatus(false);
        if(accountList.isEmpty()){
            throw new EmptyAccountException();
        }
        return accountList;
    }

    @Override
    public List<Account> getDeletedAccounts() throws EmptyAccountException {
        List<Account> accountList = accountDao.getByStatus(true);
        if(accountList.isEmpty()){
            throw new EmptyAccountException();
        }
        return accountList;
    }

    @Override
    public Account getAccountById(Integer id) throws AccountNotFindException {
        Account account = accountDao.getById(id);
        if(account == null){
            throw new AccountNotFindException();
        }else{
            return account;
        }
    }

    @Override
    public void addAccount(Account account) {
        accountDao.save(account);
    }

    @Override
    public List<Account> getAccountByCustomerId(Integer id) {
        return accountDao.getByCustomerId(id);
    }

    @Override
    public void deposit(int accountId, Amount amount) throws AccountNotFindException {
        Account accountById = getAccountById(accountId);
        accountById.setBalance(amountUtil.add(accountById.getBalance(), amount));
        accountDao.update(accountById);
    }

    @Override
    public void withdraw(int accountId, Amount amount) throws AccountNotFindException, ValidationException {
        Account accountById = getAccountById(accountId);
        if(amountUtil.compareTo(amount,accountById.getBalance()) > 0){
            throw new ValidationException("The amount is larger than balance!");
        }
        accountById.setBalance(amountUtil.subtract(accountById.getBalance(), amount));
        accountDao.update(accountById);
    }

    @Override
    public synchronized void transfer(int fromAccountId, int toAccountId, Amount amount) throws AccountNotFindException, ValidationException {
        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);

        if(amountUtil.compareTo(amount, fromAccount.getBalance()) > 0){
            throw new ValidationException("The amount is larger than fromAccount balance!");
        }
        fromAccount.setBalance(amountUtil.subtract(fromAccount.getBalance(), amount));
        toAccount.setBalance(amountUtil.add(toAccount.getBalance(), amount));
        accountDao.update(fromAccount);
        accountDao.update(toAccount);
    }

    @Override
    public void exportJson(String fileName) {
        File file = new File(fileName + ".json");
        try {
            file.createNewFile();
            List<Account> accountList = accountDao.getAll();
            objectMapper.writeValue(file,accountList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
