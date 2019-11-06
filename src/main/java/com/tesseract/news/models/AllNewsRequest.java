package com.tesseract.news.models;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Builder
@Getter
public class AllNewsRequest {

    private Set<String> query;

    private Set<String> qInTitle;

    private String domain;

    private LanguageCode languageCode;

    private ZonedDateTime from;

    private ZonedDateTime to;

    @Builder.Default
    private Integer page = 0;

    private Set<String> sources;

    public Map<String, String> getQueryParams() {
        return Collections.EMPTY_MAP;
    }

    private String getQuery() {
        return query.toString();
    }

    private String getRquery() {
        return qInTitle.toString();
    }
}
