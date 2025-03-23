package com.mysite.chatgpt.customer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LEGAL")
class LegalCustomer extends Customer {
    private String address;

    public LegalCustomer() {
        super(null, null, CustomerType.LEGAL); // Call to the superclass constructor
    }
    public LegalCustomer(String name, String address, String number) {
        super(name, number, CustomerType.LEGAL);
        this.address = address;
    }

    public LegalCustomer(int id, String name, String number, String address) {
        super(id, name, number, CustomerType.LEGAL);
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
