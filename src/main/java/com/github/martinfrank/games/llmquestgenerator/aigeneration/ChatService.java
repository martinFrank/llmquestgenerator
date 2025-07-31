package com.github.martinfrank.games.llmquestgenerator.aigeneration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ChatService {


    public static String request(Prompt prompt) {
        List<String> modelResponse = new ArrayList<>();
        try(var client = HttpClient.newHttpClient()) {

            String json = prompt.toJson();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://192.168.0.250:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            try (var reader = new BufferedReader(new InputStreamReader(response.body(), StandardCharsets.UTF_8))) {
                String line;
//                System.out.print("Antwort: ");
                while ((line = reader.readLine()) != null) {
                    // Jede Zeile ist ein JSON-Objekt mit einem "response"-Feld
                    var content = extractResponse(line);
                    if (content != null) {
//                        System.out.print(content);
                        modelResponse.add(content);
                    }
                }
                System.out.println();
            }
        }catch (IOException | InterruptedException e){

        }
        return String.join("", modelResponse);
    }

    private static String extractResponse(String jsonLine) {
        int idx = jsonLine.indexOf("\"response\":\"");
        if (idx == -1) return null;

        int start = idx + "\"response\":\"".length();
        int end = jsonLine.indexOf('"', start);
        if (end == -1) return null;

        String content = jsonLine.substring(start, end);
        return content.replace("\\n", "\n").replace("\\", "\"");
//        return content.replace("\\n", "\n").replace();
//        return content;
    }

}
