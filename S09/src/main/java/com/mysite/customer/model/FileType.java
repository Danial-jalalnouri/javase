package com.mysite.customer.model;

import com.mysite.customer.service.exception.InvalidType;

public enum FileType {
    SERIALIZE(1),
    JSON(2);

    private final int value;

    FileType(int value){
        this.value = value;
    }

    public static FileType fromValue(int value) throws InvalidType {
        for (FileType type:values()){
            if(type.value == value){
                return type;
            }
        }
        throw new InvalidType();
    }
}
