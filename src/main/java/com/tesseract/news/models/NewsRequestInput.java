package com.tesseract.news.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class NewsRequestInput {

    private String type;

    private Map<String, String> keywords;

    public String getType() {
        return type;
    }

    public Map<String, String> getKeywords() {
        return this.keywords;
    }

}
