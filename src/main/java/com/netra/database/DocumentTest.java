package com.netra.database;

public class DocumentTest {

    public static void main(String[] args) {

        DocumentRepository repository = new DocumentRepository();

        repository.save(
                "https://example.com",
                "Example Domain",
                "This is a test document for NETRA search engine.",
                "Example website"
        );
    }
}