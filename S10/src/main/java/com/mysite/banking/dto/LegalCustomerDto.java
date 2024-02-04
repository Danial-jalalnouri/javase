package com.mysite.banking.dto;

import com.mysite.banking.model.CustomerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class LegalCustomerDto extends CustomerDto{
    private String fax;
    public LegalCustomerDto() {
        super(CustomerType.LEGAL);
    }
    public LegalCustomerDto(Integer id, String name, String number) {
        super(id, name, number, CustomerType.LEGAL);
    }
}
