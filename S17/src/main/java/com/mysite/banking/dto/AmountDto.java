package com.mysite.banking.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AmountDto {
    private Currency currency;
    private BigDecimal value;
}
