package com.mysite.banking.facade;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.facade.impl.AccountFacadeImpl;
import com.mysite.banking.facade.impl.CustomerFacadeImpl;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.AccountType;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AccountFacadeTest {
    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private AccountServiceImpl accountService;
    private AccountFacade accountFacade;

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
        accountDto.setType(AccountType.EURO);
        accountDto.setBalance((double) 0);
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
        accountDto.setType(AccountType.EURO);
        accountDto.setBalance((double) 0);
        when(customerService.getCustomerById(12)).thenReturn(new RealCustomer());

        accountFacade.addAccount(accountDto);

        verify(accountService).addAccount(any(Account.class));
    }
}
