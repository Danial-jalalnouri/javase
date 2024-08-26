package com.mysite.banking.service.impl;

import com.mysite.banking.service.CurrencyConvertService;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConvertServiceImpl implements CurrencyConvertService {

    private static final CurrencyConvertServiceImpl INSTANCE;

    public static CurrencyConvertServiceImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new CurrencyConvertServiceImpl();
    }

    private Map<Currency, BigDecimal> currencyRates;

    private CurrencyConvertServiceImpl() {
        currencyRates = new HashMap<>();
        currencyRates.put(Currency.getInstance("USD"), BigDecimal.ONE);
        currencyRates.put(Currency.getInstance("EUR"), new BigDecimal("0.89"));
        currencyRates.put(Currency.getInstance("GBP"), new BigDecimal("0.78"));
    }

    @Override
    public BigDecimal convertCurrency(BigDecimal amount, Currency baseCurrency, Currency destinationCurrency) {
        BigDecimal baseRate = currencyRates.get(baseCurrency);
        BigDecimal destinationRate = currencyRates.get(destinationCurrency);

        BigDecimal convertedAmount = amount.multiply(
                destinationRate.divide(baseRate, 5, BigDecimal.ROUND_HALF_UP));

        return convertedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
