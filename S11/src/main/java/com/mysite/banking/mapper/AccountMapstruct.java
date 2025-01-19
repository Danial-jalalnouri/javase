package com.mysite.banking.mapper;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.dto.LegalCustomerDto;
import com.mysite.banking.dto.RealCustomerDto;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.Customer;
import com.mysite.banking.model.LegalCustomer;
import com.mysite.banking.model.RealCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface AccountMapstruct {
    AccountDto mapToAccountDto(Account account);

    List<AccountDto> mapToAccountDtoList(List<Account> accountList);

    @Mapping(ignore = true, target = "id")
    Account mapToAccount(AccountDto accountDto,
                                           @MappingTarget Account account);
    @Mapping(ignore = true, target = "id")
    Account mapToAccount(AccountDto accountDto);
}
