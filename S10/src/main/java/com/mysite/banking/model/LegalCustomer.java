package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString(callSuper = true)
public class LegalCustomer extends Customer implements Serializable {

    private String fax;

    @Override
    public boolean equals(Object obj){
        return obj instanceof LegalCustomer &&
                ((LegalCustomer) obj).getName().equals(getName());
    }

    public LegalCustomer() {
        super(CustomerType.LEGAL);
    }

    public LegalCustomer(String name, String number) {
        super(name, number, CustomerType.LEGAL);
    }
}
