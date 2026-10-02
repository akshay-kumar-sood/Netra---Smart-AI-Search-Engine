package com.netra.search;

public class SnippetTest {

    public static void main(String[] args) {

        SearchRepository repository = new SearchRepository();

        var results = repository.search("java",1);

        for (SearchResult result : results) {

            String snippet = SnippetGenerator.generate(
                    result.getContent(),
                    "java"
            );

            System.out.println("Title: " + result.getTitle());
            System.out.println("Snippet: " + snippet);
            System.out.println("----------------------");
        }
    }
}