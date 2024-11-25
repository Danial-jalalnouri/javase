package com.mysite.phonebook.service;

import com.mysite.phonebook.model.BusinessContact;
import com.mysite.phonebook.model.Contact;
import com.mysite.phonebook.model.PersonalContact;

import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
    private ArrayList<Contact> contacts = new ArrayList<>();

    public void run(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            printMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    printAllContacts();
                    break;
                case 3:
                    System.out.println("Exit!");
                    break;
                default:
                    System.out.println("Invalid number.");
            }
        } while (choice != 3);
        scanner.close();
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Contact");
        System.out.println("2. Print All Contacts");
        System.out.println("3. Exit \n");
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

    private void addContact(Scanner scanner) {
        System.out.println("Contact Type:");
        System.out.println("1. Personal");
        System.out.println("2. Business (2 or other numbers)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1){
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            System.out.println("Enter family: ");
            String family = scanner.nextLine();
            System.out.println("Enter number: ");
            String number = scanner.nextLine();
            PersonalContact personalContact = new PersonalContact(name, number);
            personalContact.setFamily(family);
            contacts.add(personalContact);
        }else{
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            System.out.println("Enter number: ");
            String number = scanner.nextLine();
            System.out.println("Enter fax number: ");
            String fax = scanner.nextLine();
            BusinessContact businessContact = new BusinessContact(name, number);
            businessContact.setFax(fax);
            contacts.add(businessContact);
        }
    }
}
