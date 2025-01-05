package com.mysite.customer.util;

import java.util.Scanner;
import java.util.function.Function;

public class ScannerWrapper implements AutoCloseable {

    private static final ScannerWrapper INSTANCE;
    private final Scanner scanner;
    public static ScannerWrapper getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new ScannerWrapper();
    }

    private ScannerWrapper() {
        scanner = new Scanner(System.in);
    }

    public <T> T getUserInput(String message, Function<String, T> converter) {
        System.out.println(message);
        try {
            return converter.apply(scanner.nextLine());
        } catch (Exception ex) {
            System.out.println("Wrong input");
            return getUserInput(message, converter);
        }
    }

    @Override
    public void close() {
        scanner.close();
    }
}
