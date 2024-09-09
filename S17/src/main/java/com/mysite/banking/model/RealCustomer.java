package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "real_customer")
@Getter
@Setter
@ToString(callSuper = true)
public class RealCustomer extends Customer implements Serializable {

    private String family;
    private Date birthday;

    @Override
    public boolean equals(Object obj){
        return obj instanceof RealCustomer &&
                ((RealCustomer) obj).getName().equals(getName()) &&
                ((RealCustomer) obj).getFamily().equals(getFamily());
    }

    public RealCustomer() {
        super(CustomerType.REAL);
    }
    public RealCustomer(String name, String number) {
        super(name, number, CustomerType.REAL);
    }
}
