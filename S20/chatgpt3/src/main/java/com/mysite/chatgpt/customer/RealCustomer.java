package com.mysite.chatgpt.customer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REAL")
class RealCustomer extends Customer {
    private String family;

    public RealCustomer() {
        super(null, null, CustomerType.REAL); // Call to the superclass constructor
    }
    public RealCustomer(String name, String family, String number) {
        super(name, number, CustomerType.REAL);
        this.family = family;
    }

    public RealCustomer(int id, String name, String number, String family) {
        super(id, name, number, CustomerType.REAL);
        this.family = family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFamily() {
        return family;
    }
}
