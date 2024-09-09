package com.mysite.banking.facade;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.facade.impl.AccountFacadeImpl;
import com.mysite.banking.facade.impl.CustomerFacadeImpl;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.RealCustomer;
import com.mysite.banking.service.exception.CustomerNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.AccountServiceImpl;
import com.mysite.banking.service.impl.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
public class AccountFacadeTest {
    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private AccountServiceImpl accountService;
    private AccountFacade accountFacade;


    static {
        System.setProperty("DB_MEM", "TRUE");
    }

    @Before
    public void setup(){
        accountFacade = AccountFacadeImpl.getInstance();
        CustomerFacadeImpl.getInstance(customerService);
        AccountFacadeImpl.getInstance(accountService);
    }

    @Test
    public void addAccountTest() throws CustomerNotFindException {
        AccountDto accountDto = new AccountDto();
        accountDto.setCustomerId(12);
        accountDto.setBalance(new AmountDto(Currency.getInstance("EUR"), BigDecimal.ZERO));
        when(customerService.getCustomerById(12)).thenThrow(new CustomerNotFindException());

        Exception exception = assertThrows(ValidationException.class, () -> {
            accountFacade.addAccount(accountDto);
        });

        assertEquals("Customer Id is not valid.", exception.getMessage());
    }

    @Test
    public void addAccount() throws CustomerNotFindException, ValidationException {
        AccountDto accountDto = new AccountDto();
        accountDto.setCustomerId(12);
        accountDto.setBalance(new AmountDto(Currency.getInstance("EUR"), BigDecimal.ZERO));
        when(customerService.getCustomerById(12)).thenReturn(new RealCustomer());

        accountFacade.addAccount(accountDto);

        verify(accountService).addAccount(any(Account.class));
    }
}
