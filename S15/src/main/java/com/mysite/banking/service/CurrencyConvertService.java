package com.mysite.banking.service;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyConvertService {
    BigDecimal convertCurrency(BigDecimal amount, Currency baseCurrency, Currency destinationCurrency);
}
