package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.facade.CustomerFacade;
import com.mysite.banking.service.exception.CustomerNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.validation.ValidationContext;

import java.math.BigDecimal;

public class AccountValidationContext extends ValidationContext<AccountDto> {
    private final CustomerFacade customerFacade;
    public AccountValidationContext(){
        this.customerFacade = CustomerFacadeImpl.getInstance();

        //Balance validation
        addValidation(accountDto -> {
            BigDecimal balance = accountDto.getBalance().getValue();
            if(balance.compareTo(BigDecimal.ZERO) < 0){
                throw new ValidationException("Balance can not be less than zero.");
            }
        });

        //Customer validation
        addValidation(accountDto -> {
            Integer customerId = accountDto.getCustomerId();
            try {
                customerFacade.getCustomerById(customerId);
            } catch (CustomerNotFindException e) {
                throw new ValidationException("Customer Id is not valid.");
            }
        });
    }
}
