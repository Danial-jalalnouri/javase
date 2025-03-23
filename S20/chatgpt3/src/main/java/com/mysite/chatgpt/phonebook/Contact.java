package com.mysite.chatgpt.phonebook;

class Contact {
    private String name;
    private String family;
    private String number;

    public Contact(String name, String family, String number) {
        this.name = name;
        this.family = family;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Family: " + family + ", Number: " + number;
    }
}