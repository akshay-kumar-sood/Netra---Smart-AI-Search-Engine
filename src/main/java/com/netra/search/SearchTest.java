package com.netra.search;

import java.util.List;

public class SearchTest {

    public static void main(String[] args) {

        SearchRepository repository = new SearchRepository();

        List<SearchResult> results = repository.search("java",1);

        System.out.println("Results found: " + results.size());

        for (SearchResult result : results) {
            System.out.println("ID: " + result.getId());
            System.out.println("Title: " + result.getTitle());
            System.out.println("URL: " + result.getUrl());
            System.out.println("----------------------");
            System.out.println("Content: " + result.getContent());
        }
    }
}