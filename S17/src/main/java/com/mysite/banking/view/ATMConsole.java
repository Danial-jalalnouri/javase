package com.mysite.banking.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.facade.ATMFacade;
import com.mysite.banking.facade.AccountFacade;
import com.mysite.banking.facade.CustomerFacade;
import com.mysite.banking.facade.impl.ATMFacadeImpl;
import com.mysite.banking.facade.impl.AccountFacadeImpl;
import com.mysite.banking.facade.impl.CustomerFacadeImpl;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.CustomerNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.ATMServiceImpl;
import com.mysite.banking.util.GlobalAttributes;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.IntStream;

public class ATMConsole  extends BaseConsole{

    private final GlobalAttributes globalAttributes;
    private final CustomerFacade customerFacade;
    private final AccountFacade accountFacade;
    private final ATMFacade atmFacade;

    public ATMConsole() {
        accountFacade = AccountFacadeImpl.getInstance();
        globalAttributes = GlobalAttributes.getInstance();
        customerFacade = CustomerFacadeImpl.getInstance();
        atmFacade = ATMFacadeImpl.getInstance();
    }

    public void menu() {
        Integer customerId = globalAttributes.getCustomerId();
        if(customerId != null) {
            menuWithUser();
        }else
            menuNoUser();
    }

    public void menuWithUser() {
        int choice = 0;
        do {
            try {
                printMenu();
                choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        withdraw();
                        break;
                    case 3:
                        deposit();
                        break;
                    default:
                        System.out.println("Invalid number.");
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        } while (choice != 0);
    }

    private void deposit() {
        int quantity = scannerWrapper.getUserInput("Enter the quantity: ", Integer::valueOf);

        System.out.println("Denomination:");
        System.out.println("1. 5€");
        System.out.println("2. 10€");
        System.out.println("3. 50€");
        System.out.println("4. 100€");
        System.out.println("5. 500€");
        int choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
        int denomination;
        if(choice == 1){
            denomination = 5;
        }else if (choice == 2){
            denomination = 10;
        }else if (choice == 3){
            denomination = 50;
        }else if (choice == 4){
            denomination = 100;
        }else{
            denomination = 500;
        }

        atmFacade.deposit(denomination, quantity);

    }

    private void withdraw() throws AccountNotFindException, ValidationException {
        int accountId = scannerWrapper.getUserInput("Enter the account id: ", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter the amount: ", BigDecimal::new);
        Currency currency = getCurrency();
        int[] billsToWithdraw = atmFacade.withdraw(accountId, new AmountDto(currency, amount));
        List<Integer> billValues = atmFacade.getBillValues();
        IntStream.range(0, billsToWithdraw.length)
                .filter(i -> billsToWithdraw[i] > 0)
                .forEach(i -> System.out.println(billsToWithdraw[i] + "€" + billValues.get(i)));
    }

    private Currency getCurrency(){
        System.out.println("Currency:");
        System.out.println("1. EUR");
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

    private void checkBalance() throws AccountNotFindException {
        List<AccountDto> allAccounts = accountFacade.searchAccountByCustomerId(globalAttributes.getCustomerId());
        System.out.println("All Accounts:");
        for (AccountDto account : allAccounts) {
            try {
                System.out.println(objectMapper.writeValueAsString(account));
            } catch (JsonProcessingException e) {
                System.out.println("Error on print account id " + account.getId());
            }
        }
    }

    public void menuNoUser() {
        int choice = 0;
        do {
            try {
                printMenu();
                choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
                switch (choice) {
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid number.");
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        } while (choice != 0);
    }

    private void printMenu() throws CustomerNotFindException {
        Integer customerId = globalAttributes.getCustomerId();
        if(customerId != null) {
            CustomerDto customerById = customerFacade.getCustomerById(customerId);
            printMenuWithUser(customerById);
        }else
            printMenuNoUser();
    }

    private void printMenuWithUser(CustomerDto customer) {
        System.out.println("Hello " + customer.getName());
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println("1. Check balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit To ATM");
        System.out.println();
    }

    private void printMenuNoUser() {
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println();
    }
}
