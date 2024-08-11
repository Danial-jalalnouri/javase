package com.mysite.customer.mapper;

import com.mysite.customer.dto.CustomerDto;
import com.mysite.customer.dto.LegalCustomerDto;
import com.mysite.customer.dto.RealCustomerDto;
import com.mysite.customer.model.Customer;
import com.mysite.customer.model.LegalCustomer;
import com.mysite.customer.model.RealCustomer;
import org.mapstruct.Mapper;

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

    default LegalCustomer mapToLegalCustomer(LegalCustomerDto legalCustomerDto,
                                            LegalCustomer legalCustomer){
        legalCustomer.setName(legalCustomerDto.getName());
        legalCustomer.setNumber(legalCustomerDto.getNumber());
        legalCustomer.setFax(legalCustomerDto.getFax());
        return legalCustomer;
    }

    default RealCustomer mapToRealCustomer(RealCustomerDto realCustomerDto,
                                          RealCustomer realCustomer){
        realCustomer.setName(realCustomerDto.getName());
        realCustomer.setNumber(realCustomerDto.getNumber());
        realCustomer.setFamily(realCustomerDto.getFamily());
        return realCustomer;
    }
}
