package com.mysite.phonebook.model;

public abstract class Contact {
    private String name;
    private String number;
    private final ContactType type;

    public Contact(String name, String number, ContactType type) {
        this.name = name;
        this.number = number;
        this.type = type;
    }

    public ContactType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type ;
    }

    private String capitalizeFirstCharacter(String str) {
        if(str != null && !str.isEmpty()){
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
        return str;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = capitalizeFirstCharacter(name);
    }

}
