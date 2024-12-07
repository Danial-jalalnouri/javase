package com.mysite.customer.model;

public class LegalCustomer extends Customer {

    private String fax;

    public String getFax() {
        return fax;
    }

    @Override
    public String toString() {
        return "LegalCustomer{" +
                super.toString() +
                ", fax='" + fax + '\'' +
                '}';
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public LegalCustomer(String name, String number) {
        super(name, number, CustomerType.LEGAL);
    }
}
