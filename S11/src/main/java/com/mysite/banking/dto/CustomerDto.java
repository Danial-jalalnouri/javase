package com.mysite.banking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;
    private String email;
    private final CustomerType type;
}
