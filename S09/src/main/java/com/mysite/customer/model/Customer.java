package com.mysite.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LegalCustomer.class, name = "LEGAL"),
        @JsonSubTypes.Type(value = RealCustomer.class, name = "REAL")
})
public abstract class Customer implements Serializable {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(1);
    @JsonIgnore
    private Integer id;
    private String name;
    private String number;
    private final CustomerType type;

    private Boolean deleted;

    public Customer(CustomerType type){
        this.id = ID_COUNTER.getAndIncrement();
        this.type = type;
    }

    public Customer(String name, String number, CustomerType type) {
        this.id = ID_COUNTER.getAndIncrement();
        this.name = capitalizeFirstCharacter(name);
        this.number = number;
        this.type = type;
        this.deleted = false;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public CustomerType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type ;
    }

    private String capitalizeFirstCharacter(String str) {
        if(str != null && !str.isEmpty()){
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
        return str;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = capitalizeFirstCharacter(name);
    }

}
