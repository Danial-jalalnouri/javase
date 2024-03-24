package com.mysite.banking.service;

import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.Amount;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.UpdateException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest {
    private AccountService accountService;

    static {
        System.setProperty("DB_MEM", "TRUE");
    }

    @Before
    public void setup(){
        accountService = AccountServiceImpl.getInstance();
    }

    @Test
    public void depositCaseA() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
        Account accountById = accountService.getAccountById(id);
        assertEquals(BigDecimal.valueOf(10.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseB() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.0)));
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
        Account accountById = accountService.getAccountById(id);
        assertEquals(BigDecimal.valueOf(30.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseC() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.1)));
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.2)));
        Account accountById = accountService.getAccountById(id);
        assertEquals(BigDecimal.valueOf(30.30).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseD() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(50.1)));
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(30.2)));
        Account accountById = accountService.getAccountById(id);
        assertEquals(BigDecimal.valueOf(80.30).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseE() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(1.1)));
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(2.2)));
        Account accountById = accountService.getAccountById(id);
        assertEquals(BigDecimal.valueOf(3.30).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseF() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountService.addAccount(account);
        Integer id = account.getId();
        for (int i = 0; i <10; i++) {
            accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
        }
        Account accountById = accountService.getAccountById(id);
        // 10 * 10  = 100
        assertEquals(BigDecimal.valueOf(100.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseG() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable depositTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (AccountNotFindException ignored) {

                }
            }
        };

        depositTask.run();

        Account accountById = accountService.getAccountById(id);
        // 100 * 10  = 1000
        assertEquals(BigDecimal.valueOf(1000.00).setScale(2), accountById.getBalance().getValue());
    }


    @Test
    public void depositCaseH() throws AccountNotFindException, InterruptedException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable depositTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (AccountNotFindException ignored) {

                }
            }
        };

        Thread[] threads = new Thread[1];
        for (int i = 0; i <threads.length; i++) {
            threads[i] = new Thread(depositTask);
            threads[i].start();
        }

        for (Thread thread: threads){
            thread.join();
        }

        Account accountById = accountService.getAccountById(id);
        // 100 * 10  = 1000
        assertEquals(BigDecimal.valueOf(1000.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void depositCaseI() throws AccountNotFindException, InterruptedException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.ZERO));
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable depositTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.deposit(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (UpdateException ex) {
                    i = i - 1; // retry
                    System.out.println("retry deposit");
                } catch (AccountNotFindException ignored) {

                }
            }
        };

        //10
        //A = +10
        //B = +10
        // >> 30



        //10
        //A = +10 >> sum = 10 + 10 >> 20
        //B = +10 >> sum = 10 + 10 >> 20
        // >> 20


        //5
        //A = +10 >> sum = 5 + 10 >> 15
        //B = +10 >> sum = 5 + 10 >> 15
        // >> 15

        Thread[] threads = new Thread[10];
        for (int i = 0; i <threads.length; i++) {
            threads[i] = new Thread(depositTask);
            threads[i].start();
        }

        for (Thread thread: threads){
            thread.join();
        }

        Account accountById = accountService.getAccountById(id);
        // 100 * 10 * 10  = 10000
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void withdrawCaseA() throws AccountNotFindException, ValidationException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.3)));
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.withdraw(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.1)));
        Account accountById = accountService.getAccountById(id);
        assertEquals(BigDecimal.valueOf(10.20).setScale(2), accountById.getBalance().getValue());
    }

    @Test
    public void withdrawCaseB() throws AccountNotFindException, InterruptedException {
        Account account = new Account();
        account.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(30000)));
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable withdrawTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.withdraw(id, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.0)));
                } catch (UpdateException ex) {
                    i = i - 1; // retry
                    System.out.println("retry withdraw");
                } catch (AccountNotFindException | ValidationException ignored) {

                }
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i <threads.length; i++) {
            threads[i] = new Thread(withdrawTask);
            threads[i].start();
        }

        for (Thread thread: threads){
            thread.join();
        }

        Account accountById = accountService.getAccountById(id);
        // 100 * 10 * 10  = 10000 >>> 30000 - 10000 = 20000
        assertEquals(BigDecimal.valueOf(20000.00).setScale(2), accountById.getBalance().getValue());
    }


    @Test
    public void transferCaseX() throws AccountNotFindException, ValidationException {
        Account accountA = new Account();
        accountA.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.1)));
        accountService.addAccount(accountA);
        Integer idA = accountA.getId();

        Account accountB = new Account();
        accountB.setBalance(new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(20.2)));
        accountService.addAccount(accountB);
        Integer idB = accountB.getId();

        accountService.transfer(idA,idB, new Amount(Currency.getInstance("EUR"), BigDecimal.valueOf(10.1)));

        Account accountById = accountService.getAccountById(idB);
        assertEquals(BigDecimal.valueOf(30.30).setScale(2), accountById.getBalance().getValue());
    }

}
