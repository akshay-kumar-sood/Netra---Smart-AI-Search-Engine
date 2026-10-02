package com.netra.crawler;

public class CrawlerTest {

    public static void main(String[] args) {

        WebCrawler crawler = new WebCrawler();

        crawler.crawl("https://example.com");
    }
}