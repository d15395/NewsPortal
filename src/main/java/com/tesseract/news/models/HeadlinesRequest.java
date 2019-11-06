package com.tesseract.news.models;

import lombok.Builder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Builder
public class HeadlinesRequest {

    private LanguageCode languageCode;

    @Builder.Default
    private Integer page = 0;

    private Set<String> sources;

    private Category category;

    private Map<String, String> getQueryParams() {
        return Collections.EMPTY_MAP;
    }
}
