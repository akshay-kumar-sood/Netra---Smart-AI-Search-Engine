package com.netra.search;

public class SearchResult {

    private final long id;
    private final String title;
    private final String url;
    private final String description;
    private final String content;

    public SearchResult(long id, String title, String url,String description,String content) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description=description;
        this.content=content;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }
}