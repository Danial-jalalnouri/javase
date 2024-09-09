package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.facade.ATMFacade;
import com.mysite.banking.mapper.AccountMapstruct;
import com.mysite.banking.service.ATMService;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.ATMServiceImpl;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class ATMFacadeImpl implements ATMFacade {

    private final ATMService atmService;
    private final AccountMapstruct accountMapstruct;
    private static final ATMFacadeImpl INSTANCE;

    public static ATMFacadeImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new ATMFacadeImpl();
    }
    private ATMFacadeImpl() {
        this.accountMapstruct = Mappers.getMapper(AccountMapstruct.class);
        atmService = ATMServiceImpl.getInstance();
    }

    @Override
    public int[] withdraw(int accountId, AmountDto amount) throws AccountNotFindException, ValidationException {
        return atmService.withdraw(accountId, accountMapstruct.mapToAmount(amount));
    }

    @Override
    public void deposit(int denomination, int quantity) {
        atmService.deposit(denomination, quantity);
    }

    @Override
    public List<Integer> getBillValues() {
        return atmService.getBillValues();
    }
}
