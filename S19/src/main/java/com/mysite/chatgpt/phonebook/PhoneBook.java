package com.mysite.chatgpt.phonebook;

import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("Phone Book Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Show All Contacts");
            System.out.println("3. Find and Print Contact by Name");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    showAllContacts();
                    break;
                case 3:
                    findAndPrintContactByName();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the phone book. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
        scanner.close();
    }

    private static void findAndPrintContactByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.println("Contact found:");
                System.out.println(contact);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Contact with name \"" + name + "\" not found.");
        }
    }

    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        // Check if the name already exists
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.println("Contact with this name already exists. Please enter a different name.");
                return;
            }
        }

        System.out.print("Enter family: ");
        String family = scanner.nextLine();
        System.out.print("Enter number: ");
        String number = scanner.nextLine();
        Contact contact = new Contact(name, family, number);
        contacts.add(contact);
        System.out.println("Contact added successfully.");
    }

    private static void showAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("All Contacts:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }
}


