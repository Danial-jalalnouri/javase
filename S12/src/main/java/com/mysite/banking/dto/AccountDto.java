package com.mysite.banking.dto;

import com.mysite.banking.model.AccountType;
import lombok.*;

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
