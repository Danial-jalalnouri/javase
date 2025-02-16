package com.mysite.banking.util;

public class Validator {
    public static boolean validateNumber(String number){
        return (number != null) &&
                number.matches("^0\\d{10}$|^00\\d{12}$|^\\+\\d{12}$");
    }

    public static boolean validateEmail(String email){
        return (email != null) &&
                email.matches("^.+@.+[.].+$");
    }
}
