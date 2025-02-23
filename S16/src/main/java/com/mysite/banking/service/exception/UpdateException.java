package com.mysite.banking.service.exception;

public class UpdateException extends RuntimeException{
    public UpdateException(String message, Throwable cause){
        super(message, cause);
    }

    public UpdateException(){
        super();
    }
}
