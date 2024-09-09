package com.mysite.banking.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GlobalAttributes {
    private static final GlobalAttributes INSTANCE;

    public static GlobalAttributes getInstance(){
        return INSTANCE;
    }

    private Integer customerId;

    static {
        INSTANCE = new GlobalAttributes();
    }

    private GlobalAttributes(){
        customerId = null;
    }

}
