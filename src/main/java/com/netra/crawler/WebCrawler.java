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
            String description = page.select("meta[name=description]")
                    .attr("content");

            String content = page.body().text();

            repository.save(
                    url,
                    title,
                    content,
                    description
            );

            System.out.println("Crawled: " + url);
            System.out.println("Title: " + title);

        } catch (Exception e) {
            System.out.println("Failed to crawl: " + url);
            e.printStackTrace();
        }
    }
}