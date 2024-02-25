package com.mysite.banking.util;

import com.mysite.banking.model.Amount;
import com.mysite.banking.service.CurrencyConvertService;
import com.mysite.banking.service.impl.CurrencyConvertServiceImpl;

import java.math.BigDecimal;

public class AmountUtil {

    private static final AmountUtil INSTANCE;

    public static AmountUtil getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new AmountUtil();
    }

    private AmountUtil() {
        this.currencyConvertService = CurrencyConvertServiceImpl.getInstance();
    }

    private CurrencyConvertService currencyConvertService;

    public Amount add(Amount firstAmount, Amount secondAmount){
        BigDecimal convertedAmount = currencyConvertService.convertCurrency(
                secondAmount.getValue(),
                secondAmount.getCurrency(),
                firstAmount.getCurrency()
        );
        return new Amount(firstAmount.getCurrency(),
                firstAmount.getValue().add(convertedAmount));
    }

    public int compareTo(Amount firstAmount, Amount secondAmount){
        BigDecimal convertedAmount = currencyConvertService.convertCurrency(
                secondAmount.getValue(),
                secondAmount.getCurrency(),
                firstAmount.getCurrency()
        );
        return firstAmount.getValue().compareTo(convertedAmount);
    }

    public Amount subtract(Amount firstAmount, Amount secondAmount){
        BigDecimal convertedAmount = currencyConvertService.convertCurrency(
                secondAmount.getValue(),
                secondAmount.getCurrency(),
                firstAmount.getCurrency()
        );
        return new Amount(firstAmount.getCurrency(),
                firstAmount.getValue().subtract(convertedAmount));
    }
}
