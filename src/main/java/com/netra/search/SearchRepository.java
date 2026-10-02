package com.netra.search;

import com.netra.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchRepository {

    public List<SearchResult> search(String query) {

        List<SearchResult> results = new ArrayList<>();

        String sql = """
        SELECT id, title, url, description,
               ts_rank(
                   search_vector,
                   plainto_tsquery('english', ?)
               ) AS rank
        FROM documents
        WHERE search_vector @@ plainto_tsquery('english', ?)
        ORDER BY rank DESC
        LIMIT 10
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, query);
            statement.setString(2, query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                results.add(new SearchResult(
                        result.getLong("id"),
                        result.getString("title"),
                        result.getString("url"),
                        result.getString("description")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}