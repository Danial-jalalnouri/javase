package com.mysite.chatgpt.customer;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class CustomerService {
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        } catch (Exception e) {
            System.err.println("Failed to start H2 web console: " + e.getMessage());
            return;
        }
        printMenu();
        handleMenuChoice();
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Real Customer");
        System.out.println("2. Add Legal Customer");
        System.out.println("3. Show All Customers");
        System.out.println("4. Find Customer By Name");
        System.out.println("5. Exit");
    }

    private static void handleMenuChoice() {
        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addRealCustomer();
                    break;
                case 2:
                    addLegalCustomer();
                    break;
                case 3:
                    showAllCustomers();
                    break;
                case 4:
                    findCustomerByName();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);
    }

    private static void showAllCustomers() {
        try (Session session = sessionFactory.openSession()) {
            List<Customer> customers = session.createQuery("FROM Customer", Customer.class).list();
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addRealCustomer() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter number: ");
        String number = scanner.nextLine();
        System.out.print("Enter family: ");
        String family = scanner.nextLine();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            RealCustomer realCustomer = new RealCustomer();
            realCustomer.setName(name);
            realCustomer.setNumber(number);
            realCustomer.setFamily(family);

            session.save(realCustomer);

            session.getTransaction().commit();
            System.out.println("Real customer added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to add real customer.");
        }
    }

    private static void addLegalCustomer() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter number: ");
        String number = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            LegalCustomer legalCustomer = new LegalCustomer();
            legalCustomer.setName(name);
            legalCustomer.setNumber(number);
            legalCustomer.setAddress(address);

            session.save(legalCustomer);

            session.getTransaction().commit();
            System.out.println("Legal customer added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to add legal customer.");
        }
    }
    private static void findCustomerByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();
        try (Session session = sessionFactory.openSession()) {
            List<Customer> customers = session.createQuery("FROM Customer WHERE name LIKE :name", Customer.class)
                    .setParameter("name", "%" + name + "%")
                    .list();
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
