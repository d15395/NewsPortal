package com.tesseract.news.orchestrators;

import com.tesseract.news.models.NewsRecords;
import com.tesseract.news.models.RequestType;
import com.tesseract.news.service.NewsApiService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

public class NewsServiceOrchestrator {

    private final String authKey;

    private final NewsApiService newsApiService;

    @Inject
    public NewsServiceOrchestrator(@Named("NewsAPIAuthToken") String authKey, NewsApiService newsApiService) {
        this.authKey = authKey;
        this.newsApiService = newsApiService;
    }

    public NewsRecords getRecords(String requestType, Map<String, String> queryParams) throws IOException {
        switch (requestType) {
            case RequestType.ALL_NEWS:
                return newsApiService.getTopRecords(authKey, queryParams).execute().body();
            case RequestType.HEADLINES:
                return newsApiService.getAllRecords(authKey, queryParams).execute().body();
        }
        throw new IllegalArgumentException("Input requestType should be of type Headlines or AllNews");
    }
}
