package com.netra.websearch;

import com.netra.crawler.WebCrawler;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class WebSearchManager {

    private final WikipediaSearchProvider wikipedia;
    private final WebCrawler crawler;

    public WebSearchManager() {
        wikipedia = new WikipediaSearchProvider();
        crawler = new WebCrawler();
    }

    public void searchAndCrawl(String query) {

        System.out.println(
                "Searching external sources for: " + query
        );

        Set<String> urls = new LinkedHashSet<>();

        // Wikipedia - maximum 3 URLs
        List<String> wikipediaUrls =
                wikipedia.findUrls(query);

        for (String url : wikipediaUrls) {

            urls.add(url);

            if (urls.size() == 3) {
                break;
            }
        }

        // GitHub - fixed repository
        urls.add(
                "https://github.com/spring-projects/spring-boot"
        );

        System.out.println(
                "External URLs found: " + urls.size()
        );

        // Crawl every unique URL
        for (String url : urls) {

            System.out.println(
                    "Crawling external URL: " + url
            );

            crawler.crawl(url);
        }

        System.out.println(
                "External search and crawling completed."
        );
    }
}