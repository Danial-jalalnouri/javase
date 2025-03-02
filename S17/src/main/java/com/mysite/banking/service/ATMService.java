package com.mysite.banking.service;

import com.mysite.banking.model.Amount;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ValidationException;

import java.util.List;

public interface ATMService {
    int[] withdraw(int accountId, Amount amount) throws AccountNotFindException, ValidationException;

    void deposit(int denomination, int quantity);

    List<Integer> getBillValues();
}
