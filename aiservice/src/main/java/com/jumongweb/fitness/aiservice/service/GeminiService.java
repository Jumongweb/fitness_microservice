package com.jumongweb.fitness.aiservice.service;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

public class GeminiService {

    private final WebClient webClient;
    private String geminiApiUrl;

    public GeminiService(WebClient.Builder webClient) {
        this.webClient = WebClient.builder().build();
    }

    public String getAnswer(String question){
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question),
                        })
                }
        );
        String response = null;

        return response;
    }
}
