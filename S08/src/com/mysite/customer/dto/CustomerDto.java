package com.mysite.customer.dto;

import com.mysite.customer.model.CustomerType;

public abstract class CustomerDto {
    private final Integer id;
    private String name;
    private String number;
    private final CustomerType type;

    public CustomerDto(Integer id, String name, String number, CustomerType type) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public CustomerType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type ;
    }
}
