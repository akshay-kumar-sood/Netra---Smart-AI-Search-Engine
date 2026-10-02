package com.netra.search;

public class SnippetGenerator {

    public static String generate(String content, String query) {

        if (content == null || content.isBlank()) {
            return "";
        }

        if (query == null || query.isBlank()) {
            return content.length() > 180
                    ? content.substring(0, 180) + "..."
                    : content;
        }

        String lowerContent = content.toLowerCase();
        String lowerQuery = query.toLowerCase();

        int index = lowerContent.indexOf(lowerQuery);

        if (index == -1) {
            return content.length() > 180
                    ? content.substring(0, 180) + "..."
                    : content;
        }

        int start = Math.max(0, index - 70);
        int end = Math.min(content.length(), index + query.length() + 110);

        String snippet = content.substring(start, end).trim();

        if (start > 0) {
            snippet = "..." + snippet;
        }

        if (end < content.length()) {
            snippet = snippet + "...";
        }

        return snippet;
    }
}