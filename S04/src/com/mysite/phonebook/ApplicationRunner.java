package com.mysite.phonebook;

import com.mysite.phonebook.service.PhoneBook;


public class ApplicationRunner {


    public static void main(String[] args) {
        try (PhoneBook phoneBook = new PhoneBook()){
            phoneBook.run();
        }
    }
}
