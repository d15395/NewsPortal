package com.tesseract.news.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tesseract.news.components.DaggerOrchestratorComponent;
import com.tesseract.news.components.OrchestratorComponent;
import com.tesseract.news.orchestrators.NewsServiceOrchestrator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NewsVisualizationHandler implements RequestStreamHandler {

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        OrchestratorComponent orchestratorComponent = DaggerOrchestratorComponent.builder().build();
        NewsServiceOrchestrator orchestrator = orchestratorComponent.getOrchestrator();
        JsonNode inputRequest = new ObjectMapper().readTree(inputStream);
        System.err.println(new Gson().toJson(orchestrator.getAllRecords(inputRequest)));
    }
}
