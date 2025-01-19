package com.mysite.banking.mapper;

import com.mysite.banking.dto.CustomerDto;
import com.mysite.banking.dto.LegalCustomerDto;
import com.mysite.banking.dto.RealCustomerDto;
import com.mysite.banking.model.Customer;
import com.mysite.banking.model.LegalCustomer;
import com.mysite.banking.model.RealCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface CustomerMapstruct {
    default CustomerDto mapToCustomerDto(Customer customer) {
        if(customer instanceof RealCustomer){
            return mapToRealCustomerDto((RealCustomer) customer);
        }else{
            return mapToLegalCustomerDto((LegalCustomer) customer);
        }
    }
    RealCustomerDto mapToRealCustomerDto(RealCustomer realCustomer);

    LegalCustomerDto mapToLegalCustomerDto(LegalCustomer legalCustomer);

    List<CustomerDto> mapToCustomerDtoList(List<Customer> customerList);

    default Customer mapToCustomer(CustomerDto customerDto,
                                  Customer customer){
        if(customerDto instanceof RealCustomerDto){
            return mapToRealCustomer((RealCustomerDto) customerDto,
                    (RealCustomer) customer);
        }else{
            return mapToLegalCustomer((LegalCustomerDto) customerDto,
                    (LegalCustomer) customer);
        }
    }

    default Customer mapToCustomer(CustomerDto customerDto){
        if(customerDto instanceof RealCustomerDto){
            return mapToRealCustomer((RealCustomerDto) customerDto,
                    new RealCustomer(null, null));
        }else{
            return mapToLegalCustomer((LegalCustomerDto) customerDto,
                    new LegalCustomer(null, null));
        }
    }

    @Mapping(ignore = true, target = "id")
    LegalCustomer mapToLegalCustomer(LegalCustomerDto legalCustomerDto,
                                           @MappingTarget LegalCustomer legalCustomer);

    @Mapping(ignore = true, target = "id")
    RealCustomer mapToRealCustomer(RealCustomerDto realCustomerDto,
                                   @MappingTarget RealCustomer realCustomer);
}
