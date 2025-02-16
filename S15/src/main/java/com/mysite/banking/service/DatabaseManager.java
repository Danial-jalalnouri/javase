package com.mysite.banking.service;

import org.hibernate.Session;

import java.sql.ResultSet;

public interface DatabaseManager {
    Session getSession();
}
