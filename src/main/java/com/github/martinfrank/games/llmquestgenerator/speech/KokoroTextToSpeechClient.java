package com.github.martinfrank.games.llmquestgenerator.speech;

import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class KokoroTextToSpeechClient {

    private final RestTemplate restTemplate;
    private final String kokoroUrl;

    public KokoroTextToSpeechClient() {
        this.restTemplate = new RestTemplate();
        this.kokoroUrl = "http://192.168.0.250:5050/v1/audio/speech";
    }

    public byte[] speak(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer your_api_key_here");

        //FIXME make json model for this
        String body = "{\"model\": \"tts-1\"," +
                " \"input\":\"" + text + "\"," +
                " \"voice\": \"alloy\"}";
        System.out.println();
        System.out.println(body);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        return restTemplate.exchange(kokoroUrl, HttpMethod.POST, request, byte[].class).getBody();

//        ResponseEntity<String> response = restTemplate.exchange(
//                kokoroUrl, HttpMethod.POST, request, String.class
//        );
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            // Fall A: Response ist Base64
//            String base64Audio = response.getBody();
//            return Base64.getDecoder().decode(base64Audio);
//
//            // Oder Fall B: Direkt binary response -> byte[].class verwenden
//            // return restTemplate.exchange(kokoroUrl + "/speak", HttpMethod.POST, request, byte[].class).getBody();
//        }

//        throw new RuntimeException("Kokoro TTS failed: " + response.getStatusCode());
    }
}
