package com.mysite.banking.view;
public class ConsoleUI extends BaseConsole implements AutoCloseable {

    private final CustomerConsole customerConsole;
    private final AccountConsole accountConsole;
    private final ATMConsole atmConsole;

    public ConsoleUI(){
        super();
        customerConsole = new CustomerConsole();
        accountConsole = new AccountConsole();
        atmConsole = new ATMConsole();
    }

    public void startMenu(){

        int choice;
        do {
            printMainMenu();
            choice = scannerWrapper.getUserInput("Enter your choice: ", Integer::valueOf);
                switch (choice) {
                    case 1:
                        customerConsole.menu();
                        break;
                    case 2:
                        accountConsole.menu();
                        break;
                    case 3:
                        atmConsole.menu();
                        break;
                    case 0:
                        System.out.println("Exit!");
                        break;
                    default:
                        System.out.println("Invalid number.");
                }
        } while (choice != 0);
    }

    private void printMainMenu() {
        System.out.println("Menu:");
        System.out.println("0. Exit");
        System.out.println("1. Customer Manager");
        System.out.println("2. Account Manager");
        System.out.println("3. ATM");
        System.out.println();
    }

    @Override
    public void close() {
        scannerWrapper.close();
    }
}
