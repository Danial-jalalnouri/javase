package com.mysite.banking.dto;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Integer id;
    private AmountDto balance;
    private Integer customerId;
}
