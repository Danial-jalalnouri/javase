package com.mysite.customer.view.component;

import com.mysite.customer.model.Customer;
import com.mysite.customer.model.CustomerType;
import com.mysite.customer.util.ScannerWrapper;

import java.util.function.Function;

public abstract class AbstractCustomerUI {
    protected final ScannerWrapper scannerWrapper;

    public AbstractCustomerUI() {
        this.scannerWrapper = ScannerWrapper.getInstance();
    }

    public static AbstractCustomerUI fromCustomerType(CustomerType type){
        return switch (type) {
            case REAL -> new RealCustomerUI();
            case LEGAL -> new LegalCustomerUI();
        };
    }

    public Customer generateCustomer(){
        String name = scannerWrapper.getUserInput("Enter name: ", Function.identity());
        String number = scannerWrapper.getUserInput("Enter number: ", Function.identity());
        return additionalGenerateCustomer(name, number);
    }

    protected abstract Customer additionalGenerateCustomer(String name, String number);

    public abstract void editCustomer(Customer customer);
}
