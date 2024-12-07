package com.mysite.customermanagement.model;

public class PersonalCustomer extends Customer {

    private String family;

    public String getFamily() {
        return family;
    }

    @Override
    public String toString() {
        return "PersonalCustomer{" +
                super.toString() +
                ", family='" + family + "'" +
                '}';
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public PersonalCustomer(String name, String number) {
        super(name, number, CustomerType.PERSONAL);
    }
}
