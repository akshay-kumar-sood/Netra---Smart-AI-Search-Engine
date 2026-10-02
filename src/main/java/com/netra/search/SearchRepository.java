package com.netra.search;

import com.netra.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchRepository {

    private static final int RESULTS_PER_PAGE = 5;

    // Minimum relevance required to consider a result useful
    private static final double MIN_RANK = 0.05;

    public List<SearchResult> search(String query, int page) {

        if (query == null || query.isBlank()) {
            return new ArrayList<>();
        }

        List<SearchResult> results = new ArrayList<>();

        int offset = (page - 1) * RESULTS_PER_PAGE;

        String sql = """
                SELECT id,
                       title,
                       url,
                       description,
                       content,
                       ts_rank(
                           search_vector,
                           plainto_tsquery('english', ?)
                       ) AS rank
                FROM documents
                WHERE search_vector @@ plainto_tsquery('english', ?)
                  AND ts_rank(
                      search_vector,
                      plainto_tsquery('english', ?)
                  ) >= ?
                ORDER BY rank DESC
                LIMIT ?
                OFFSET ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, query);
            statement.setString(2, query);
            statement.setString(3, query);
            statement.setDouble(4, MIN_RANK);
            statement.setInt(5, RESULTS_PER_PAGE);
            statement.setInt(6, offset);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                results.add(
                        new SearchResult(
                                result.getLong("id"),
                                result.getString("title"),
                                result.getString("url"),
                                result.getString("description"),
                                result.getString("content")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public boolean hasNext(String query, int page) {

        if (query == null || query.isBlank()) {
            return false;
        }

        int offset = page * RESULTS_PER_PAGE;

        String sql = """
                SELECT 1
                FROM documents
                WHERE search_vector @@ plainto_tsquery('english', ?)
                  AND ts_rank(
                      search_vector,
                      plainto_tsquery('english', ?)
                  ) >= ?
                LIMIT 1
                OFFSET ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, query);
            statement.setString(2, query);
            statement.setDouble(3, MIN_RANK);
            statement.setInt(4, offset);

            ResultSet result =
                    statement.executeQuery();

            return result.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}