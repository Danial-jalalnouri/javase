package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.facade.AccountFacade;
import com.mysite.banking.mapper.AccountMapstruct;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.Customer;
import com.mysite.banking.service.AccountService;
import com.mysite.banking.service.CustomerService;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.service.impl.AccountServiceImpl;
import com.mysite.banking.service.impl.CustomerServiceImpl;
import com.mysite.banking.service.validation.ValidationContext;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class AccountFacadeImpl implements AccountFacade {
    private ValidationContext<AccountDto> validationContext;
    private AccountService accountService;

    private final CustomerService customerService;
    private final AccountMapstruct accountMapstruct;
    private static final AccountFacadeImpl INSTANCE;

    public static AccountFacadeImpl getInstance(){
        return INSTANCE;
    }

    public static AccountFacadeImpl getInstance(AccountService accountService){
        INSTANCE.accountService = accountService;
        return INSTANCE;
    }

    static {
        INSTANCE = new AccountFacadeImpl();
    }
    private AccountFacadeImpl() {
        this.accountMapstruct = Mappers.getMapper(AccountMapstruct.class);
        this.accountService = AccountServiceImpl.getInstance();
        this.customerService = CustomerServiceImpl.getInstance();
        this.validationContext = new AccountValidationContext();
    }

    private AccountFacadeImpl(AccountServiceImpl accountService) {
        this.accountMapstruct = Mappers.getMapper(AccountMapstruct.class);
        this.accountService = accountService;
        this.customerService = CustomerServiceImpl.getInstance();
        this.validationContext = new AccountValidationContext();
    }

    @Override
    public void deleteAccountById(Integer id) throws AccountNotFindException {
        accountService.deleteAccountById(id);
    }

    @Override
    public List<AccountDto> getActiveAccounts() throws EmptyAccountException {
        return accountMapstruct.mapToAccountDtoList(
                accountService.getActiveAccounts());
    }

    @Override
    public List<AccountDto> getDeletedAccounts() throws EmptyAccountException {
        return accountMapstruct.mapToAccountDtoList(
                accountService.getDeletedAccounts());
    }

    @Override
    public AccountDto getAccountById(Integer id) throws AccountNotFindException {
        return accountMapstruct.mapToAccountDto(accountService.getAccountById(id));
    }

    @Override
    public void addAccount(AccountDto accountDto) throws ValidationException {
        validationContext.validate(accountDto);
        accountService.addAccount(accountMapstruct.mapToAccount(accountDto));
    }

    @Override
    public void updateAccount(AccountDto accountDto) throws ValidationException, AccountNotFindException {
        validationContext.validate(accountDto);
        Account account = accountService.getAccountById(accountDto.getId());
        accountMapstruct.mapToAccount(accountDto, account);
    }

    @Override
    public List<AccountDto> searchAccountByCustomerName(String name) {
        List<Customer> customers = customerService.searchCustomersByName(name);
        List<Account> accountList = new ArrayList<>();
        for (Customer customer : customers) {
            accountList.addAll(accountService.getAccountByCustomerId(customer.getId()));
        }
        return accountMapstruct.mapToAccountDtoList(accountList);
    }

    @Override
    public List<AccountDto> searchAccountByCustomerId(Integer id) {
        return accountMapstruct.mapToAccountDtoList(
                accountService.getAccountByCustomerId(id));
    }

    @Override
    public void deposit(int accountId, AmountDto amount) throws AccountNotFindException {
        accountService.deposit(accountId, accountMapstruct.mapToAmount(amount));
    }

    @Override
    public void withdraw(int accountId, AmountDto amount) throws AccountNotFindException, ValidationException {
        accountService.withdraw(accountId, accountMapstruct.mapToAmount(amount));
    }

    @Override
    public void transfer(int fromAccountId, int toAccountId, AmountDto amount) throws AccountNotFindException, ValidationException {
        accountService.transfer(fromAccountId, toAccountId, accountMapstruct.mapToAmount(amount));
    }

    @Override
    public void exportJson(String fileName) {
        accountService.exportJson(fileName);
    }
}
