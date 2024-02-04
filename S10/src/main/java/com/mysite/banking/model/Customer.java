package com.mysite.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LegalCustomer.class, name = "LEGAL"),
        @JsonSubTypes.Type(value = RealCustomer.class, name = "REAL")
})
@Getter
@Setter
@ToString
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
        this.deleted = false;
    }

    public Customer(String name, String number, CustomerType type) {
        this.id = ID_COUNTER.getAndIncrement();
        this.name = name;
        this.number = number;
        this.type = type;
        this.deleted = false;
    }


}
