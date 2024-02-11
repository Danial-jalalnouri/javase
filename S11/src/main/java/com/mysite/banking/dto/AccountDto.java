package com.mysite.banking.dto;

import com.mysite.banking.model.AccountType;
import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Integer id;
    private AccountType type;
    private Double balance;
    private Integer customerId;
}
