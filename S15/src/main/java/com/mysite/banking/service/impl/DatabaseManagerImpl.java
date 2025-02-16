package com.mysite.banking.service.impl;

import com.mysite.banking.model.LegalCustomer;
import com.mysite.banking.model.RealCustomer;
import com.mysite.banking.service.DatabaseManager;
import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.*;

public class DatabaseManagerImpl implements DatabaseManager {

    //private static final String JDBC_URL = "jdbc:h2:mem:BankSystemDB";
    private static final String JDBC_URL = "jdbc:h2:./data/BankSystemDB";
    private static final String USERNAME = "myuser";
    private static final String PASSWORD = "mypass";
    private static final String PORT = "8082";
    private static final DatabaseManagerImpl INSTANCE;

    private SessionFactory sessionFactory;

    private DatabaseManagerImpl(){
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .applySetting(Environment.DRIVER, "org.h2.Driver")
                .applySetting(Environment.URL, JDBC_URL)
                .applySetting(Environment.USER, USERNAME)
                .applySetting(Environment.PASS, PASSWORD)
                .applySetting(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
                .applySetting(Environment.SHOW_SQL, "true")
                .applySetting("hibernate.hbm2ddl.auto","update")
                .build();

        try {
            Server.createTcpServer("-tcpAllowOthers").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Server.createWebServer("-webPort",PORT,"-web", "-webAllowOthers").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        MetadataSources sources = new MetadataSources(standardServiceRegistry);
        sources.addAnnotatedClass(LegalCustomer.class);
        sources.addAnnotatedClass(RealCustomer.class);

        Metadata metadata = sources.getMetadataBuilder().build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    static {
        INSTANCE = new DatabaseManagerImpl();
    }

    public static DatabaseManagerImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
