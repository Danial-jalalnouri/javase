package com.mysite.customer.dto;

import com.mysite.customer.model.CustomerType;

public class RealCustomerDto extends CustomerDto {
    private String family;

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    @Override
    public String toString() {
        return "RealCustomer{" +
                super.toString() +
                ", family='" + family + '\'' +
                '}';
    }

    public RealCustomerDto(Integer id, String name, String number) {
        super(id, name, number, CustomerType.REAL);
    }
}
