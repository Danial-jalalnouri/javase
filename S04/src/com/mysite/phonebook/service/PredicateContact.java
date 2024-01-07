package com.mysite.phonebook.service;

import com.mysite.phonebook.model.Contact;

import java.util.function.Predicate;

public class PredicateContact implements Predicate<Contact> {

    private String name;
    public PredicateContact(String name) {
        this.name = name;
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getName().equalsIgnoreCase(name);
    }
}
