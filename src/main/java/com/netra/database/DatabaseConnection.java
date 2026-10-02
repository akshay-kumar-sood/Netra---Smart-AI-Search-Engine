package com.netra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                System.getenv("NETRA_DB_URL"),
                System.getenv("NETRA_DB_USER"),
                System.getenv("NETRA_DB_PASSWORD")
        );
    }
}