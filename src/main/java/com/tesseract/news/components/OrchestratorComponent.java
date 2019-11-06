package com.tesseract.news.components;

import com.tesseract.news.modules.NewsAPIAuthModule;
import com.tesseract.news.orchestrators.NewsServiceOrchestrator;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = NewsAPIAuthModule.class)
public interface OrchestratorComponent {
    NewsServiceOrchestrator getOrchestrator();
}