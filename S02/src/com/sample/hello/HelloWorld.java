package com.sample.hello;

import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Application Started");
        System.out.println("--------------------");
        System.out.println("----- Menu -----");
        System.out.println("1. Print default message");
        System.out.println("2. Get the name and say hello");
        System.out.println("Select a menu item:");
        String userInput = scanner.nextLine();
        if(userInput.equals("1")){
            System.out.println("Hello World!");
        }
        if(userInput.equals("2")){
            System.out.println("Enter your name:");
            String userName = scanner.nextLine();
            System.out.println("Hello " + userName);
        }
        scanner.close();
    }
}
