package com.mysite.customer.dto;

import com.mysite.customer.model.CustomerType;

public class LegalCustomerDto extends CustomerDto{
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

    public LegalCustomerDto(Integer id, String name, String number) {
        super(id, name, number, CustomerType.LEGAL);
    }
}
