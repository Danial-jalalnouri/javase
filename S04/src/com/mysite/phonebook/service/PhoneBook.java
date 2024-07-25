package com.mysite.phonebook.service;

import com.mysite.phonebook.model.BusinessContact;
import com.mysite.phonebook.model.Contact;
import com.mysite.phonebook.model.PersonalContact;

import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook implements AutoCloseable {
    private ArrayList<Contact> contacts = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run(){
        int choice;
        do {
            printMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    printAllContacts();
                    break;
                case 0:
                    System.out.println("Exit!");
                    break;
                case 3:
                    searchAndPrintContactsByName();
                    break;
                case 4:
                    searchAndPrintContactsByFamily();
                    break;
                case 5:
                    searchAndEditContactsByName();
                    break;
                case 6:
                    searchAndDeleteContactsByName();
                    break;
                default:
                    System.out.println("Invalid number.");
            }
        } while (choice != 0);
    }

    private void searchAndDeleteContactsByName() {
        String name = getUserInput("Enter the name: ");
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
    }

    private void searchAndEditContactsByName() {
        String name = getUserInput("Enter the name: ");
        for (Contact contact : contacts) {
            if(contact.getName().equalsIgnoreCase(name)){
                System.out.println(contact);
                String number = getUserInput("Enter new number: ");
                contact.setNumber(number);
                if(contact instanceof PersonalContact personalContact){
                    String family = getUserInput("Enter new family: ");
                    personalContact.setFamily(family);
                } else if(contact instanceof BusinessContact businessContact) {
                    String fax = getUserInput("Enter fax number: ");
                    businessContact.setFax(fax);
                }

            }
        }
    }

    private void searchAndPrintContactsByFamily() {
        String family = getUserInput("Enter the family: ");

        contacts.stream()
                .filter(contact -> contact instanceof PersonalContact)
                .map(contact -> (PersonalContact) contact)
                .filter(personalContact -> personalContact.getFamily().equalsIgnoreCase(family))
                .forEach(System.out::println);
    }

    private void searchAndPrintContactsByName() {
        String name = getUserInput("Enter the name: ");

        contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .forEach(System.out::println);
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("0. Exit");
        System.out.println("1. Add Contact");
        System.out.println("2. Print All Contacts");
        System.out.println("3. Search and print contacts by name");
        System.out.println("4. Search and print contacts by family");
        System.out.println("5. Search and edit contacts by name");
        System.out.println("6. Search and delete contacts by name");
        System.out.println();
    }


    private void printAllContacts() {
        if(contacts.isEmpty()){
            System.out.println("Phonebook is empty!");
        }else{
            System.out.println("All Contacts:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    private void addContact() {
        System.out.println("Contact Type:");
        System.out.println("1. Personal");
        System.out.println("2. Business (2 or other numbers)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1){
            String name = getUserInput("Enter name: ");
            String family = getUserInput("Enter family: ");
            String number = getUserInput("Enter number: ");
            PersonalContact personalContact = new PersonalContact(name, number);
            personalContact.setFamily(family);
            contacts.add(personalContact);
        }else{
            String name = getUserInput("Enter name: ");
            String number = getUserInput("Enter number: ");
            String fax = getUserInput("Enter fax number: ");
            BusinessContact businessContact = new BusinessContact(name, number);
            businessContact.setFax(fax);
            contacts.add(businessContact);
        }
    }

    private String getUserInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }


    @Override
    public void close() {
        scanner.close();
    }
}
