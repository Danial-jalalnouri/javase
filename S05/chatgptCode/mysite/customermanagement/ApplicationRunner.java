package com.mysite.customermanagement;

import com.mysite.customermanagement.ui.ConsoleUI;

public class ApplicationRunner {

    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.startMenu();
    }
}
