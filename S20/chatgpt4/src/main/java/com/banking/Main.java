
package com.banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerManager customerManager = new CustomerManager();
        //AccountManager accountManager = new AccountManager();

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("0. Exit");
            System.out.println("1. Customer Manager");
            //System.out.println("2. Account Manager");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            } else if (choice == 1) {
                customerManager.customerMenu(scanner);
            } else if (choice == 2) {
                //accountManager.accountMenu(scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
