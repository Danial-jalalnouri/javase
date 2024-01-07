package com.mysite.phonebook.model;

public class BusinessContact extends Contact {

    private String fax;

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public BusinessContact(String name, String number) {
        super(name, number, ContactType.BUSINESS);
    }
}
