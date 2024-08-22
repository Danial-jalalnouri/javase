package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public class Account implements Serializable {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(1);
    private Integer id;
    private AccountType type;
    private Double balance;
    private Integer customerId;
    private Boolean deleted;

    public Account(){
        this.id = ID_COUNTER.getAndIncrement();
        this.deleted = false;
    }

    public Account(AccountType type, Double balance, Integer customerId) {
        this.id = ID_COUNTER.getAndIncrement();
        this.type = type;
        this.balance = balance;
        this.customerId = customerId;
        this.deleted = false;
    }
}
