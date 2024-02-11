package com.mysite.banking.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MapperWrapper {
    private static final ObjectMapper INSTANCE;

    public static ObjectMapper getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new ObjectMapper();
        INSTANCE.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private MapperWrapper(){}
}
