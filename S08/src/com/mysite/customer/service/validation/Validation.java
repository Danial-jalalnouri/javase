package com.mysite.customer.service.validation;

import com.mysite.customer.service.exception.ValidationException;

@FunctionalInterface
public interface Validation <T> {
    void validate(T t) throws ValidationException;
}
