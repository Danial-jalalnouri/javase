package com.mysite.banking.service.exception;

public class EmptyCustomerException extends BaseException {
    public EmptyCustomerException(){
        super("There is no customer!");
    }
}
