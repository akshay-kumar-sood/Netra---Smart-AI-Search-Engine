package com.netra.websearch;

import java.util.List;

public interface WebSearchService {

    List<String> findUrls(String query);
}