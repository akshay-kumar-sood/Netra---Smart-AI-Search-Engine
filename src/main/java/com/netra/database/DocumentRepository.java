package com.netra.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DocumentRepository {

    public void save(String url, String title, String content, String description) {

        String sql = """
                INSERT INTO documents (url, title, content, description)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, url);
            statement.setString(2, title);
            statement.setString(3, content);
            statement.setString(4, description);

            statement.executeUpdate();

            System.out.println("Document saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}