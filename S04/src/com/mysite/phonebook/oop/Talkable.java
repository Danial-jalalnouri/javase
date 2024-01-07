package com.mysite.phonebook.oop;

public interface Talkable {
    //String talk();

    default String talk() {
        return "default";
    }
}
