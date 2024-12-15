package com.mysite.customer.view.component;

import com.mysite.customer.model.Customer;
import com.mysite.customer.model.RealCustomer;

import java.util.function.Function;

public class RealCustomerUI extends AbstractCustomerUI{
    public RealCustomerUI() {
        super();
    }

    @Override
    public Customer additionalGenerateCustomer(String name, String number) {
        String family = scannerWrapper.getUserInput("Enter family: ", Function.identity());
        RealCustomer realCustomer = new RealCustomer(name, number);
        realCustomer.setFamily(family);
        return realCustomer;
    }

    @Override
    public void editCustomer(Customer customer) {
        RealCustomer realCustomer = (RealCustomer) customer;
        String number = scannerWrapper.getUserInput("Enter new number: ", Function.identity());
        customer.setNumber(number);
        String family = scannerWrapper.getUserInput("Enter new family: ", Function.identity());
        realCustomer.setFamily(family);
    }
}
