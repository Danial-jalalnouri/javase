package com.mysite.banking.facade;

import com.mysite.banking.dto.RealCustomerDto;
import com.mysite.banking.facade.impl.CustomerFacadeImpl;
import com.mysite.banking.model.RealCustomer;
import com.mysite.banking.service.exception.CustomerNotFindException;
import com.mysite.banking.service.exception.DuplicateCustomerException;
import com.mysite.banking.service.exception.EmptyCustomerException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerFacadeTest {

    @Mock
    private CustomerServiceImpl customerService;
    private CustomerFacade customerFacade;

    static {
        System.setProperty("DB_MEM", "TRUE");
    }

    @Before
    public void setup(){
        customerFacade = CustomerFacadeImpl.getInstance(customerService);
    }

    @Test
    public void addCustomerInvalidEmailTest() throws ParseException, CustomerNotFindException {
        RealCustomerDto customer = new RealCustomerDto();
        customer.setEmail("test");
        customer.setNumber("09121234567");
        customer.setName("test");
        customer.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("11-11-1987"));
        customer.setFamily("test family");
        when(customerService.searchCustomersByEmail("test")).thenThrow(new CustomerNotFindException());

        Exception exception = assertThrows(ValidationException.class, () -> {
            customerFacade.addCustomer(customer);
        });
        assertEquals("Invalid email format.", exception.getMessage());
    }

    @Test
    public void addCustomerInvalidFamilyTest() throws ParseException, CustomerNotFindException {
        RealCustomerDto customer = new RealCustomerDto();
        customer.setEmail("test@site.com");
        customer.setNumber("09121234567");
        customer.setName("test");
        customer.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("11-11-1987"));
        customer.setFamily("test Family");
        when(customerService.searchCustomersByEmail("test@site.com")).thenThrow(new CustomerNotFindException());

        Exception exception = assertThrows(ValidationException.class, () -> {
            customerFacade.addCustomer(customer);
        });
        assertEquals("Family must not be empty or null, and should be in lower case.", exception.getMessage());
    }

    @Test
    public void addCustomerTest() throws ParseException, ValidationException, DuplicateCustomerException, EmptyCustomerException, CustomerNotFindException {
        RealCustomerDto customer = new RealCustomerDto();
        customer.setEmail("test@site.com");
        customer.setNumber("09121234567");
        customer.setName("test");
        customer.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("11-11-1987"));
        customer.setFamily("test family");
        when(customerService.searchCustomersByEmail("test@site.com")).thenThrow(new CustomerNotFindException());

        customerFacade.addCustomer(customer);

        RealCustomer customerEntity = new RealCustomer();
        customerEntity.setName("test");
        customerEntity.setEmail("test@site.com");
        customerEntity.setFamily("test family");
        customerEntity.setNumber("09121234567");
        customerEntity.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("11-11-1987"));
        verify(customerService).addCustomer(customerEntity);
    }

    @Test
    public void addCustomerDuplicateEmailTest() throws ParseException, CustomerNotFindException {
        RealCustomerDto customer = new RealCustomerDto();
        customer.setId(1);
        customer.setEmail("test@site.com");
        customer.setNumber("09121234567");
        customer.setName("test");
        customer.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("11-11-1987"));
        customer.setFamily("test family");
        when(customerService.searchCustomersByEmail("test@site.com")).thenReturn(new RealCustomer());

        Exception exception = assertThrows(ValidationException.class, () -> {
            customerFacade.addCustomer(customer);
        });

        assertEquals("Email must not be duplicate.", exception.getMessage());
    }

}
