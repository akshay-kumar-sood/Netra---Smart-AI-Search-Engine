package com.netra.database;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        System.out.println("URL set: " + (System.getenv("NETRA_DB_URL") != null));
        System.out.println("User set: " + (System.getenv("NETRA_DB_USER") != null));
        System.out.println("Password set: " + (System.getenv("NETRA_DB_PASSWORD") != null));

        try (Connection connection = DatabaseConnection.getConnection()) {

            System.out.println("Connected to Neon PostgreSQL!");
            System.out.println(connection.getMetaData().getDatabaseProductName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}