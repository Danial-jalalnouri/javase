package com.mysite.banking.view.component;

import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.dto.RealCustomerDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class RealCustomerUI extends AbstractCustomerUI{
    public RealCustomerUI() {
        super();
    }

    @Override
    public CustomerDto additionalGenerateCustomer(String name, String number, String password, String email) {
        String family = scannerWrapper.getUserInput("Enter family: ", Function.identity());
        Date birthdate = scannerWrapper.getUserInput("Enter birthdate (dd-MM-yyyy): ",
                input -> {
                    try {
                        return new SimpleDateFormat("dd-MM-yyyy").parse(input);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
        RealCustomerDto realCustomer = new RealCustomerDto(null,name, number, password, email);
        realCustomer.setFamily(family);
        realCustomer.setBirthday(birthdate);
        return realCustomer;
    }

    @Override
    public void editCustomer(CustomerDto customer) {
        RealCustomerDto realCustomer = (RealCustomerDto) customer;
        String number = scannerWrapper.getUserInput("Enter new number: ", Function.identity());
        customer.setNumber(number);
        String family = scannerWrapper.getUserInput("Enter new family: ", Function.identity());
        realCustomer.setFamily(family);
    }
}
