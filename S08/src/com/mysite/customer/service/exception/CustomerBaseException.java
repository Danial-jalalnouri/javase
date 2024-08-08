package com.mysite.customer.service.exception;

public class CustomerBaseException extends Exception{
    public CustomerBaseException(String message){
        super(message);
    }

    public CustomerBaseException(){
        super();
    }
}
