package com.mysite.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LegalCustomer.class, name = "LEGAL"),
        @JsonSubTypes.Type(value = RealCustomer.class, name = "REAL")
})
@Getter
@Setter
@ToString
public abstract class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name="customer_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Integer id;
    private String name;
    private String number;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private final CustomerType type;

    private Boolean deleted;

    public Customer(CustomerType type){
        this.type = type;
        this.deleted = false;
    }

    public Customer(String name, String number, CustomerType type) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.deleted = false;
    }


}
