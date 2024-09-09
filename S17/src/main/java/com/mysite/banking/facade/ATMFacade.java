package com.mysite.banking.facade;

import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ValidationException;

import java.util.List;

public interface ATMFacade {
    int[] withdraw(int accountId, AmountDto amount)  throws AccountNotFindException, ValidationException;

    void deposit(int denomination, int quantity);

    List<Integer> getBillValues();
}
