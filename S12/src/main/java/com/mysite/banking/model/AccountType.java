package com.mysite.banking.model;

import com.mysite.banking.service.exception.InvalidType;

public enum AccountType {
    EURO(1),
    DOLLAR(2);

    private final int value;

    AccountType(int value){
        this.value = value;
    }

    public static AccountType fromValue(int value) throws InvalidType {
        for (AccountType type:values()){
            if(type.value == value){
                return type;
            }
        }
        throw new InvalidType();
    }
}
