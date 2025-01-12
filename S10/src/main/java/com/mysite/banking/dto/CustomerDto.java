package com.mysite.banking.dto;

import com.mysite.banking.model.CustomerType;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class CustomerDto {
    private Integer id;
    private String name;
    private String number;
    private final CustomerType type;
}
