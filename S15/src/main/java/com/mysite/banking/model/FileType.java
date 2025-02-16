package com.mysite.banking.model;

import com.mysite.banking.service.exception.InvalidType;

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
