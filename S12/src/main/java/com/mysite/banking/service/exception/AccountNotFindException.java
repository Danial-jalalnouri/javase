package com.mysite.banking.service.exception;

public class AccountNotFindException extends BaseException {
    public AccountNotFindException(){
        super("Account not find exception!");
    }
}
