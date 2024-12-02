package com.mysite.phonebook.service;

import com.mysite.phonebook.model.Contact;

import java.util.function.Consumer;

public class ConsumerContact implements Consumer<Contact> {
    @Override
    public void accept(Contact contact) {
        System.out.println(contact);
    }
}
