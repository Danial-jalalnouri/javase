package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "account")
@Getter
@Setter
@ToString
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @SequenceGenerator(name="account_sequence", sequenceName = "hibernate_account_seq", allocationSize = 1)
    private Integer id;

    //@Embedded
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "amount_id")
    private Amount balance;
    private Integer customerId;
    private Boolean deleted;

    public Account(){
        this.deleted = false;
    }

    public Account(Amount balance, Integer customerId) {
        this.balance = balance;
        this.customerId = customerId;
        this.deleted = false;
    }
}
