package com.mysite.banking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysite.banking.model.CustomerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class RealCustomerDto extends CustomerDto {
    private String family;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthday;

    public RealCustomerDto() {
        super(CustomerType.REAL);
    }
    public RealCustomerDto(Integer id, String name, String number, String password, String email) {
        super(id, name, number, password,email, CustomerType.REAL);
    }
}
