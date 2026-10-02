package com.netra.crawler;

import com.netra.database.DocumentRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebCrawler {

    private final DocumentRepository repository;

    public WebCrawler() {
        repository = new DocumentRepository();
    }

    public void crawl(String url) {

        try {
            Document page = Jsoup.connect(url)
                    .userAgent("NETRA-Crawler/1.0")
                    .timeout(10000)
                    .get();

            String title = page.title();

            String description = page.select(
                    "meta[name=description]"
            ).attr("content").trim();

            // Fallback when meta description is empty
            if (description.isEmpty()) {

                description = page.select("p")
                        .stream()
                        .map(p -> p.text().trim())
                        .filter(text -> !text.isEmpty())
                        .findFirst()
                        .orElse("");
            }

            // Keep description short for search results
            if (description.length() > 250) {
                description = description.substring(0, 250) + "...";
            }

            String content = page.body().text();

            repository.save(
                    url,
                    title,
                    content,
                    description
            );

            System.out.println("Crawled: " + url);
            System.out.println("Title: " + title);
            System.out.println("Description: " + description);

        } catch (Exception e) {
            System.out.println("Failed to crawl: " + url);
            e.printStackTrace();
        }
    }
}