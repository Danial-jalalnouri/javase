package com.mysite.banking.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.facade.AccountFacade;
import com.mysite.banking.facade.impl.AccountFacadeImpl;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.function.Function;

public class AccountConsole extends BaseConsole {
    final AccountFacade accountFacade;

    public AccountConsole() {
        accountFacade = AccountFacadeImpl.getInstance();
    }

    public void initData(){
        accountFacade.initData();
    }

    public void saveOnExit(){
        accountFacade.saveOnExit();
    }
    private void printAccountMenu() {
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println("1. Add Account");
        System.out.println("2. Print All Accounts");
        System.out.println("3. Edit account by id");
        System.out.println("4. Delete account by id");
        System.out.println("5. Print All Deleted Accounts");
        System.out.println("6. Save data");
        System.out.println("7. Load data");
        System.out.println("8. Add data");
        System.out.println("9. Search and print customers by name");
        System.out.println("10. Deposit");
        System.out.println("11. Withdraw");
        System.out.println("12. Transfer");
        System.out.println();
    }
    public void menu(){
        int choice;
        do {
            printAccountMenu();
            choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
            try {
                switch (choice) {
                    case 1:
                        addAccount();
                        break;
                    case 2:
                        printAllAccounts();
                        break;
                    case 0:
                        break;
                    case 3:
                        editAccountById();
                        break;
                    case 4:
                        deleteAccountById();
                        break;
                    case 5:
                        printAllDeletedAccounts();
                        break;
                    case 6:
                        saveAccountData();
                        break;
                    case 7:
                        loadAccountData();
                        break;
                    case 8:
                        addAccountData();
                        break;
                    case 9:
                        searchAndPrintAccountByCustomerName();
                        break;
                    case 10:
                        deposit();
                        break;
                    case 11:
                        withdraw();
                        break;
                    case 12:
                        transfer();
                        break;
                    default:
                        System.out.println("Invalid number.");
                }
            }catch (AccountNotFindException | FileException | EmptyAccountException | ValidationException ex){
                System.out.println(ex.getMessage());
            }
        } while (choice != 0);
    }

    private void transfer() throws AccountNotFindException, ValidationException {
        int fromAccountId = scannerWrapper.getUserInput("Enter the from account id: ", Integer::valueOf);
        int toAccountId = scannerWrapper.getUserInput("Enter the to account id: ", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter the amount: ", BigDecimal::new);
        Currency currency = getCurrency();
        accountFacade.transfer(fromAccountId, toAccountId, new AmountDto(currency, amount));
    }

    private void withdraw() throws AccountNotFindException, ValidationException {
        int accountId = scannerWrapper.getUserInput("Enter the account id: ", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter the amount: ", BigDecimal::new);
        Currency currency = getCurrency();
        accountFacade.withdraw(accountId, new AmountDto(currency, amount));
    }
    private void deposit() throws AccountNotFindException {
        int accountId = scannerWrapper.getUserInput("Enter the account id: ", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter the amount: ", BigDecimal::new);
        Currency currency = getCurrency();
        accountFacade.deposit(accountId, new AmountDto(currency, amount));
    }
    private void addAccountData() throws FileException {
        String name = scannerWrapper.getUserInput("Enter the json file name: ", Function.identity());
        accountFacade.addData(name);
    }
    private void loadAccountData() throws FileException {
        System.out.println("File Type:");
        System.out.println("1. Serialize");
        System.out.println("2. Json");
        int choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
        try {
            FileType fileType = FileType.fromValue(choice);
            String name = scannerWrapper.getUserInput("Enter the file name: ", Function.identity());
            accountFacade.loadData(name, fileType);
        } catch (InvalidType e) {
            System.out.println("Invalid type!");
            loadAccountData();
        }
    }
    private void saveAccountData() throws FileException {
        System.out.println("File Type:");
        System.out.println("1. Serialize");
        System.out.println("2. Json");
        int choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
        try {
            FileType fileType = FileType.fromValue(choice);
            String name = scannerWrapper.getUserInput("Enter the file name: ", Function.identity());
            accountFacade.saveData(name, fileType);
        } catch (InvalidType e) {
            System.out.println("Invalid type!");
            saveAccountData();
        }
    }
    private void addAccount() {
        try {
            Currency currency = getCurrency();
            int number = scannerWrapper.getUserInput("Enter customer id: ", Integer::valueOf);
            AccountDto accountDto = new AccountDto(null, new AmountDto(currency, BigDecimal.ZERO), number);
            accountFacade.addAccount(accountDto);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            addAccount();
        }
    }
    private void printAllAccounts() throws EmptyAccountException {
        List<AccountDto> allAccounts = accountFacade.getActiveAccounts();
        System.out.println("All Accounts:");
        for (AccountDto account : allAccounts) {
            try {
                System.out.println(objectMapper.writeValueAsString(account));
            } catch (JsonProcessingException e) {
                System.out.println("Error on print account id " + account.getId());
            }
        }
    }
    private void printAllDeletedAccounts() throws EmptyAccountException {
        List<AccountDto> allAccounts = accountFacade.getDeletedAccounts();
        System.out.println("All Deleted Accounts:");
        for (AccountDto account : allAccounts) {
            System.out.println(account);
        }
    }
    private void searchAndPrintAccountByCustomerName() {
        String name = scannerWrapper.getUserInput("Enter the name: ", Function.identity());
        List<AccountDto> accountDtoList = accountFacade.searchAccountByCustomerName(name);
        accountDtoList.forEach(System.out::println);
    }
    private void editAccountById() throws AccountNotFindException {
        String id = scannerWrapper.getUserInput("Enter the account id: ", Function.identity());
        AccountDto accountDto = accountFacade.getAccountById(Integer.valueOf(id));
        System.out.println(accountDto);

        int number = scannerWrapper.getUserInput("Enter new customer id: ", Integer::valueOf);
        accountDto.setCustomerId(number);

        try {
            accountFacade.updateAccount(accountDto);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            editAccountById();
        }
    }
    private void deleteAccountById() throws AccountNotFindException {
        String id = scannerWrapper.getUserInput("Enter the account id: ", Function.identity());
        accountFacade.deleteAccountById(Integer.valueOf(id));
    }

    private Currency getCurrency(){
        System.out.println("Currency:");
        System.out.println("1. EUR");
        System.out.println("2. USD");
        System.out.println("3. GBP");
        int choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
        Currency currency;
        if(choice == 1){
            currency = Currency.getInstance("EUR");
        }else if (choice == 2){
            currency = Currency.getInstance("USD");
        }else{
            currency = Currency.getInstance("GBP");
        }
        return currency;
    }
}
