package com.netra.websearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WikipediaSearchProvider {


    public List<String> findUrls(String query) {

        List<String> urls = new ArrayList<>();

        if (query == null || query.isBlank()) {
            return urls;
        }

        try {
            String encodedQuery = URLEncoder.encode(
                    query,
                    StandardCharsets.UTF_8
            );

            String apiUrl =
                    "https://en.wikipedia.org/w/api.php"
                            + "?action=query"
                            + "&list=search"
                            + "&srsearch=" + encodedQuery
                            + "&format=json"
                            + "&srlimit=3";

            Document document = Jsoup.connect(apiUrl)
                    .userAgent("NETRA-Search/1.0")
                    .ignoreContentType(true)
                    .timeout(30000)
                    .followRedirects(true)
                    .get();

            String json = document.text();

            System.out.println("Wikipedia response received.");

            // Extract page titles from JSON response
            String[] parts = json.split("\"title\":\"");

            for (int i = 1; i < parts.length && urls.size() < 3; i++) {

                String title = parts[i].split("\"")[0];

                title = title
                        .replace("\\\"", "\"")
                        .replace(" ", "_");

                String url =
                        "https://en.wikipedia.org/wiki/"
                                + URLEncoder.encode(
                                title,
                                StandardCharsets.UTF_8
                        ).replace("+", "_");

                urls.add(url);
            }

        } catch (Exception e) {
            System.out.println("Wikipedia search failed.");
            e.printStackTrace();
        }

        return urls;
    }
}