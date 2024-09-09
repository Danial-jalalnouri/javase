package com.mysite.banking.facade;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.service.exception.*;

import java.util.List;

public interface AccountFacade {
    void deleteAccountById(Integer id) throws AccountNotFindException;
    List<AccountDto> getActiveAccounts() throws EmptyAccountException;
    List<AccountDto> getDeletedAccounts() throws EmptyAccountException;
    AccountDto getAccountById(Integer id) throws AccountNotFindException;
    void addAccount(AccountDto accountDto) throws ValidationException;
    void updateAccount(AccountDto accountDto) throws ValidationException, AccountNotFindException;

    List<AccountDto> searchAccountByCustomerName(String name);

    List<AccountDto> searchAccountByCustomerId(Integer id);

    void deposit(int accountId, AmountDto amount) throws AccountNotFindException;

    void withdraw(int accountId, AmountDto amount)  throws AccountNotFindException, ValidationException;

    void transfer(int fromAccountId, int toAccountId, AmountDto amount) throws AccountNotFindException, ValidationException;

    void exportJson(String fileName);
}
