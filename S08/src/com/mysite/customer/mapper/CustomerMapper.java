package com.mysite.customer.mapper;

import com.mysite.customer.dto.CustomerDto;
import com.mysite.customer.dto.LegalCustomerDto;
import com.mysite.customer.dto.RealCustomerDto;
import com.mysite.customer.model.Customer;
import com.mysite.customer.model.LegalCustomer;
import com.mysite.customer.model.RealCustomer;

import java.util.List;

public class CustomerMapper {

    public static List<CustomerDto> mapToCustomerDtoList(List<Customer> customerList){
        return customerList.stream()
                .map(CustomerMapper::mapToCustomerDto)
                .toList();
    }
    public static CustomerDto mapToCustomerDto(Customer customer){
        if(customer instanceof RealCustomer){
            return mapToRealCustomerDto((RealCustomer) customer);
        }else{
            return mapToLegalCustomerDto((LegalCustomer) customer);
        }
    }
    public static LegalCustomerDto mapToLegalCustomerDto(LegalCustomer legalCustomer){
        LegalCustomerDto legalCustomerDto = new LegalCustomerDto(
                legalCustomer.getId(),
                legalCustomer.getName(),
                legalCustomer.getNumber()
        );
        legalCustomerDto.setFax(legalCustomer.getFax());
        return legalCustomerDto;
    }

    public static RealCustomerDto mapToRealCustomerDto(RealCustomer realCustomer){
        RealCustomerDto realCustomerDto = new RealCustomerDto(
                realCustomer.getId(),
                realCustomer.getName(),
                realCustomer.getNumber()
        );
        realCustomerDto.setFamily(realCustomer.getFamily());
        return realCustomerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto,
                                         Customer customer){
        if(customerDto instanceof RealCustomerDto){
            return mapToRealCustomer((RealCustomerDto) customerDto,
                    (RealCustomer) customer);
        }else{
            return mapToLegalCustomer((LegalCustomerDto) customerDto,
                    (LegalCustomer) customer);
        }
    }
    public static Customer mapToCustomer(CustomerDto customerDto){
        if(customerDto instanceof RealCustomerDto){
            return mapToRealCustomer((RealCustomerDto) customerDto,
                    new RealCustomer(null, null));
        }else{
            return mapToLegalCustomer((LegalCustomerDto) customerDto,
                    new LegalCustomer(null, null));
        }
    }
    public static LegalCustomer mapToLegalCustomer(LegalCustomerDto legalCustomerDto,
                                                   LegalCustomer legalCustomer){
        legalCustomer.setName(legalCustomerDto.getName());
        legalCustomer.setNumber(legalCustomerDto.getNumber());
        legalCustomer.setFax(legalCustomerDto.getFax());
        return legalCustomer;
    }

    public static RealCustomer mapToRealCustomer(RealCustomerDto realCustomerDto,
                                                 RealCustomer realCustomer){
        realCustomer.setName(realCustomerDto.getName());
        realCustomer.setNumber(realCustomerDto.getNumber());
        realCustomer.setFamily(realCustomerDto.getFamily());
        return realCustomer;
    }

}
