package com.mysite.customer.service.exception;

public class CustomerNotFindException extends CustomerBaseException {
    public CustomerNotFindException(){
        super("Customer not find exception!");
    }
}
