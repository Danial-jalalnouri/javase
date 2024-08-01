package com.mysite.customer.model;

public enum CustomerType {
    REAL(1),
    LEGAL(2);

    private final int value;

    CustomerType(int value){
        this.value = value;
    }

    public static CustomerType fromValue(int value){
        for (CustomerType type:values()){
            if(type.value == value){
                return type;
            }
        }
        return null;
    }
}
