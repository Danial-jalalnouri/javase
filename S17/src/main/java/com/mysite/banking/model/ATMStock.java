package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "atm_stock")
@Getter
@Setter
@ToString
public class ATMStock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atm_sequence")
    @SequenceGenerator(name="atm_sequence", sequenceName = "hibernate_atm_seq", allocationSize = 1)
    private Long id;

    private int denomination;

    private int quantity;

}
