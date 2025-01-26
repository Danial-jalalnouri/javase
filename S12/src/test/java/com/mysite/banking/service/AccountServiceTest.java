package com.mysite.banking.service;

import com.mysite.banking.model.Account;
import com.mysite.banking.model.AccountType;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest {
    private AccountService accountService;

    @Before
    public void setup(){
        accountService = AccountServiceImpl.getInstance();
    }

    @Test
    public void depositCaseA() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance((double) 0);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, 10.0);
        Account accountById = accountService.getAccountById(id);
        assertEquals(Double.valueOf(10.0), accountById.getBalance());
    }

    @Test
    public void depositCaseB() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance((double) 20);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, 10.0);
        Account accountById = accountService.getAccountById(id);
        assertEquals(Double.valueOf(30.0), accountById.getBalance());
    }

    @Ignore
    @Test
    public void depositCaseC() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance((double) 10.1);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, 20.2);
        Account accountById = accountService.getAccountById(id);
        assertEquals(Double.valueOf(30.3), accountById.getBalance());
    }

    @Test
    public void depositCaseD() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance((double) 50.1);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, 30.2);
        Account accountById = accountService.getAccountById(id);
        assertEquals(Double.valueOf(80.3), accountById.getBalance());
    }

    @Ignore
    @Test
    public void depositCaseE() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance((double) 1.1);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.deposit(id, 2.2);
        Account accountById = accountService.getAccountById(id);
        assertEquals(Double.valueOf(3.3), accountById.getBalance());
    }

    @Test
    public void depositCaseF() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance((double) 0);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();
        for (int i = 0; i <10; i++) {
            accountService.deposit(id, 10.0);
        }
        Account accountById = accountService.getAccountById(id);
        // 10 * 10  = 100
        assertEquals(Double.valueOf(100.0), accountById.getBalance());
    }

    @Test
    public void depositCaseG() throws AccountNotFindException {
        Account account = new Account();
        account.setBalance((double) 0);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable depositTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.deposit(id, 10.0);
                } catch (AccountNotFindException ignored) {

                }
            }
        };

        depositTask.run();

        Account accountById = accountService.getAccountById(id);
        // 100 * 10  = 1000
        assertEquals(Double.valueOf(1000.0), accountById.getBalance());
    }


    @Test
    public void depositCaseH() throws AccountNotFindException, InterruptedException {
        Account account = new Account();
        account.setBalance((double) 0);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable depositTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.deposit(id, 10.0);
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
        assertEquals(Double.valueOf(1000.0), accountById.getBalance());
    }



    @Ignore
    @Test
    public void depositCaseI() throws AccountNotFindException, InterruptedException {
        Account account = new Account();
        account.setBalance((double) 0);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable depositTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.deposit(id, 10.0);
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
        assertEquals(Double.valueOf(10000.0), accountById.getBalance());
    }


    @Ignore
    @Test
    public void withdrawCaseA() throws AccountNotFindException, ValidationException {
        Account account = new Account();
        account.setBalance((double) 20.3);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();
        accountService.withdraw(id, 10.1);
        Account accountById = accountService.getAccountById(id);
        assertEquals(Double.valueOf(10.1), accountById.getBalance());
    }


    @Ignore
    @Test
    public void withdrawCaseB() throws AccountNotFindException, InterruptedException {
        Account account = new Account();
        account.setBalance((double) 30000);
        account.setType(AccountType.EURO);
        accountService.addAccount(account);
        Integer id = account.getId();


        Runnable withdrawTask = () -> {
            for (int i = 0; i <100; i++) {
                try {
                    accountService.withdraw(id, 10.0);
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
        assertEquals(Double.valueOf(20000.0), accountById.getBalance());
    }


    @Ignore
    @Test
    public void transferCaseX() throws AccountNotFindException, ValidationException {
        Account accountA = new Account();
        accountA.setBalance((double) 10.1);
        accountA.setType(AccountType.EURO);
        accountService.addAccount(accountA);
        Integer idA = accountA.getId();

        Account accountB = new Account();
        accountB.setBalance((double) 20.2);
        accountB.setType(AccountType.EURO);
        accountService.addAccount(accountB);
        Integer idB = accountB.getId();

        accountService.transfer(idA,idB, 10.1);

        Account accountById = accountService.getAccountById(idB);
        assertEquals(Double.valueOf(30.3), accountById.getBalance());
    }

}
