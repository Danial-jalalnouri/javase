package com.mysite.banking.service.exception;

public class EmptyAccountException extends BaseException {
    public EmptyAccountException(){
        super("There is no account!");
    }
}
