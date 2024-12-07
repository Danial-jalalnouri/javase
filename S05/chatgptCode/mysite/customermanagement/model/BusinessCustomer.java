package com.mysite.customermanagement.model;

public class BusinessCustomer extends Customer {

    private String fax;

    public String getFax() {
        return fax;
    }

    @Override
    public String toString() {
        return "BusinessCustomer{" +
                super.toString() +
                ", fax='" + fax + "'" +
                '}';
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public BusinessCustomer(String name, String number) {
        super(name, number, CustomerType.BUSINESS);
    }
}
