package com.mysite.customer.service.exception;

public class EmptyCustomerException extends CustomerBaseException{
    public EmptyCustomerException(){
        super("There is no customer!");
    }
}
