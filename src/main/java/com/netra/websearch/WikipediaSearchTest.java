package com.netra.websearch;

import java.util.List;

public class WikipediaSearchTest {

    public static void main(String[] args) {

        WikipediaSearchProvider provider =
                new WikipediaSearchProvider();

        List<String> urls =
                provider.findUrls("machine learning");

        System.out.println("URLs found: " + urls.size());

        for (String url : urls) {
            System.out.println(url);
        }
    }
}