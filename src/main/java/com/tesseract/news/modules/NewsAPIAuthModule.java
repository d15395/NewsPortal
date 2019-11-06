package com.tesseract.news.modules;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesseract.news.orchestrators.NewsServiceOrchestrator;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;

@Module
public class NewsAPIAuthModule {

    private static final String REGION = System.getenv("Region");
    private static final String KEY_NAME = "NewsAPIAuthToken";
    private static final String AUTH_KEY = "authKey";

    @Provides
    @Inject
    public NewsServiceOrchestrator getOrcestrator(@Named(AUTH_KEY) String authKey) {
        return new NewsServiceOrchestrator(authKey);
    }

    @Provides
    @Named(AUTH_KEY)
    @Inject
    public String getNewsAPIAuthKey(AWSSecretsManager secretsManager, ObjectMapper mapper) {
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
    public ObjectMapper getJsonMapper() {
        return new ObjectMapper();
    }

    @Provides
    public AWSSecretsManager getSecretManagerClient() {
        return AWSSecretsManagerClientBuilder.standard().withRegion(REGION).build();
    }
}
