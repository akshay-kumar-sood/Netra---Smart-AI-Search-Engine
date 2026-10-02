package com.netra.crawler;

public class CrawlerTest {

    public static void main(String[] args) {

        WebCrawler crawler = new WebCrawler();

        crawler.crawl("https://en.wikipedia.org/wiki/Java_(programming_language)");
    }
}