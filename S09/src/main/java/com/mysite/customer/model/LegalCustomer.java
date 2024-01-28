package com.mysite.customer.model;

import java.io.Serializable;

public class LegalCustomer extends Customer implements Serializable {

    private String fax;

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public String toString() {
        return "LegalCustomer{" +
                super.toString() +
                ", fax='" + fax + '\'' +
                '}';
    }

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
