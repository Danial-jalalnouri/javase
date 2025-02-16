package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public class Account implements Serializable {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(1);
    private Integer id;
    private Amount balance;
    private Integer customerId;
    private Boolean deleted;

    public Account(){
        this.id = ID_COUNTER.getAndIncrement();
        this.deleted = false;
    }

    public Account(Amount balance, Integer customerId) {
        this.id = ID_COUNTER.getAndIncrement();
        this.balance = balance;
        this.customerId = customerId;
        this.deleted = false;
    }
}
