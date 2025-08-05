package com.github.martinfrank.games.llmquestgenerator.aigeneration;

public record ChatResponse(String model, String created_at, String response, boolean done) {
}