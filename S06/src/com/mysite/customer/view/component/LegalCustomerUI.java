package com.mysite.customer.view.component;

import com.mysite.customer.model.Customer;
import com.mysite.customer.model.LegalCustomer;

import java.util.function.Function;

public class LegalCustomerUI extends AbstractCustomerUI {
    public LegalCustomerUI() {
        super();
    }

    @Override
    public Customer additionalGenerateCustomer(String name, String number) {
        String fax = scannerWrapper.getUserInput("Enter fax number: ", Function.identity());
        LegalCustomer legalCustomer = new LegalCustomer(name, number);
        legalCustomer.setFax(fax);
        return legalCustomer;
    }

    @Override
    public void editCustomer(Customer customer) {
        LegalCustomer legalCustomer = (LegalCustomer) customer;
        String number = scannerWrapper.getUserInput("Enter new number: ", Function.identity());
        customer.setNumber(number);
        String fax = scannerWrapper.getUserInput("Enter fax number: ", Function.identity());
        legalCustomer.setFax(fax);
    }
}
