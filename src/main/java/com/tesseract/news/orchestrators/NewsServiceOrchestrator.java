package com.tesseract.news.orchestrators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesseract.news.models.NewsRecords;
import com.tesseract.news.service.NewsApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewsServiceOrchestrator {

    private static final String NEWS_API_URL = "https://newsapi.org/";

    private final String authKey;

    @Inject
    public NewsServiceOrchestrator(@Named("NewsAPIAuthToken") String authKey) {
        this.authKey = authKey;
    }

    public NewsRecords getAllRecords(JsonNode inputJson) throws IOException {
        System.err.println(authKey);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();
        NewsApiService newsService = retrofit.create(NewsApiService.class);
        Map<String, String> query = new HashMap<>();
        query.put("q", "bitcoin");
        return newsService.getAllRecords(authKey, query).execute().body();
    }

    public NewsRecords getTopRecords(JsonNode inputJson) throws IOException {
        System.err.println(authKey);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_API_URL)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        NewsApiService newsService = retrofit.create(NewsApiService.class);
        Map<String, String> query = new HashMap<>();
        query.put("country", "india");
        return newsService.getTopRecords(authKey, query).execute().body();
    }
}
