package com.mysite.banking.dao;

import com.mysite.banking.model.ATMStock;

import java.util.List;

public interface ATMStockDao {
    Long save(ATMStock atmStock);
    void update(ATMStock atmStock);
    List<ATMStock> getAll();
    List<ATMStock> getByDenomination(int denomination);
}
