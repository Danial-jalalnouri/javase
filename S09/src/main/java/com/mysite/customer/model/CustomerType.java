package com.mysite.customer.model;

import com.mysite.customer.service.exception.InvalidType;
public enum CustomerType {
    REAL(1),
    LEGAL(2);

    private final int value;

    CustomerType(int value){
        this.value = value;
    }

    public static CustomerType fromValue(int value) throws InvalidType {
        for (CustomerType type:values()){
            if(type.value == value){
                return type;
            }
        }
        throw new InvalidType();
    }
}
