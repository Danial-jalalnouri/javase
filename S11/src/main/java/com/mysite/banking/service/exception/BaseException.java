package com.mysite.banking.service.exception;

public class BaseException extends Exception{
    public BaseException(String message){
        super(message);
    }

    public BaseException(){
        super();
    }
}
