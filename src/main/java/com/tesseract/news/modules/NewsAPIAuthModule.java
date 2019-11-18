package com.tesseract.news.modules;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesseract.news.orchestrators.NewsServiceOrchestrator;
import com.tesseract.news.service.NewsApiService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;

@Module
public class NewsAPIAuthModule {

    private static final String REGION = System.getenv("Region");
    private static final String KEY_NAME = "NewsAPIAuthToken";
    private static final String AUTH_KEY = "authKey";
    private static final String NEWS_API_URL = "https://newsapi.org/";

    @Provides
    @Inject
    NewsServiceOrchestrator getOrcestrator(@Named(AUTH_KEY) String authKey, NewsApiService newsApiService) {
        return new NewsServiceOrchestrator(authKey, newsApiService);
    }

    @Provides
    @Named(AUTH_KEY)
    @Inject
    String getNewsAPIAuthKey(AWSSecretsManager secretsManager, ObjectMapper mapper) {
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId(KEY_NAME);
        String secret = secretsManager.getSecretValue(request).getSecretString();
        try {
            return mapper.readTree(secret).get("api-key").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    ObjectMapper getJsonMapper() {
        return new ObjectMapper();
    }

    @Provides
    AWSSecretsManager getSecretManagerClient() {
        return AWSSecretsManagerClientBuilder.standard().withRegion(REGION).build();
    }

    @Provides
    @Singleton
    NewsApiService getNewsService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_API_URL)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(NewsApiService.class);
    }
}
