package com.github.martinfrank.games.llmquestgenerator.aigeneration;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ChatService {


    public static String request(Prompt prompt) {
        try(HttpClient client = HttpClient.newHttpClient()) {

            String json = new Gson().toJson(prompt);
            String ollamaUrl = AppSettings.get("spring.ai.ollama.base-url")+"/api/generate";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            return getBody(response);

        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static String getBody(HttpResponse<InputStream> response) throws IOException {
        List<String> modelResponse = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Jede Zeile ist ein JSON-Objekt mit einem "response"-Feld
                String content = extractResponse(line);
                if (!content.isEmpty()) {
                    modelResponse.add(content);
                }
            }
        }
        return String.join("", modelResponse);
    }

    private static String extractResponse(String jsonLine) {
        ChatResponse response = new Gson().fromJson(jsonLine, ChatResponse.class);
        String content = response.response();
        return content.replace("\\n", "\n").replace("\\", "\"");
    }

}
