package com.mysite.banking.view.component;

import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.dto.LegalCustomerDto;

import java.util.function.Function;

public class LegalCustomerUI extends AbstractCustomerUI {
    public LegalCustomerUI() {
        super();
    }

    @Override
    public CustomerDto additionalGenerateCustomer(String name, String number, String password, String email) {
        String fax = scannerWrapper.getUserInput("Enter fax number: ", Function.identity());
        LegalCustomerDto legalCustomer = new LegalCustomerDto(null,name, number, password, email);
        legalCustomer.setFax(fax);
        return legalCustomer;
    }

    @Override
    public void editCustomer(CustomerDto customer) {
        LegalCustomerDto legalCustomer = (LegalCustomerDto) customer;
        String number = scannerWrapper.getUserInput("Enter new number: ", Function.identity());
        customer.setNumber(number);
        String fax = scannerWrapper.getUserInput("Enter fax number: ", Function.identity());
        legalCustomer.setFax(fax);
    }
}
