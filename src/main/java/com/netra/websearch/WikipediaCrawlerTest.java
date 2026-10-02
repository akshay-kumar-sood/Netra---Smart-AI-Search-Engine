package com.netra.websearch;

import com.netra.crawler.WebCrawler;

import java.util.List;

public class WikipediaCrawlerTest {

    public static void main(String[] args) {

        String query = "machine learning";

        WikipediaSearchProvider provider =
                new WikipediaSearchProvider();

        WebCrawler crawler =
                new WebCrawler();

        List<String> urls =
                provider.findUrls(query);

        System.out.println("URLs found: " + urls.size());

        for (String url : urls) {

            System.out.println("----------------------");
            System.out.println("Crawling: " + url);

            crawler.crawl(url);
        }

        System.out.println("----------------------");
        System.out.println("Wikipedia crawling completed.");
    }
}