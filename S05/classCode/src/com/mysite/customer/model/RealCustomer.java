package com.mysite.customer.model;

public class RealCustomer extends Customer {

    private String family;

    public String getFamily() {
        return family;
    }

    @Override
    public String toString() {
        return "RealCustomer{" +
                super.toString() +
                ", family='" + family + '\'' +
                '}';
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public RealCustomer(String name, String number) {
        super(name, number, CustomerType.REAL);
    }
}
