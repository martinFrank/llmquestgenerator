package com.github.martinfrank.games.llmquestgenerator.aigeneration.location;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.ChatService;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.Prompt;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LocationGenerator {

    private static final Logger LOGGER = LogManager.getLogger(LocationGenerator.class);

    private LocationGenerator(){
        throw new IllegalStateException("Utility class");
    }

    public static void generate(List<Location> locations) {
        for (Location location : locations) {
            LOGGER.debug("generation location {} of type {}", location.id, location.type.toString());
            String fuzzyDescription = generateFuzzy(location);
            LocationDetails finalGeneration = generateDetails(fuzzyDescription);
            location.setDetails(finalGeneration);
        }
    }

    private static String generateFuzzy(Location location) {
        String systemPrompt = """
            your job is to create a description of a location
            for a medieval role play game. you do not describe
            any persons. the user input is a consist of the
            location type and a cue word. describe
            the location with at least 10 sentences.
            """;

        Prompt prompt = Prompt.builder()
                .systemPrompt(systemPrompt)
                .userPrompt(location.type.toString()+", "+location.id)
                .random()
                .build();
        return ChatService.request(prompt);
    }

    private static LocationDetails generateDetails(String fuzzy) {
        String systemPrompt = """
            Always respond in JSON format.
            your job is to create a description of a location
            for a medieval role play game. you have to create
             - name of the location, avoid adjectives, be extra creative for this
             - short description is a summary of the user input, maximal 3 sentences
             - a prompt to generate a image of the location
            you do not describe any persons.
            the user input is a long description of the location.
            The generated JSON output should follow this structure
            {
                "name" : "name of the location",
                "details" : "short description, maximal 3 sentences",
                "image" : "a prompt to generate a image of the location"
            }
            """;
        Prompt prompt = Prompt.builder()
                .systemPrompt(systemPrompt)
                .userPrompt(fuzzy)
                .build();
        String chatResponse = ChatService.request(prompt);
        String stripped = strip(chatResponse);
        return new Gson().fromJson(stripped, LocationDetails.class);
    }

    private static String strip(String input) {
        input = input.replace("```json", "");
        input = input.replace("```", "");
        input = input.replace("\" \"", "\": \"");
        input = input.replace("”", "\"");
        input = input.replace("„", "\"");
        input = input.replace("“", "\"");
        String[] lines = input.split("\n");
        List<String> cleaned = new ArrayList<>();
        for (String line : lines) {
            line = correctComma(line, "name", true);
            line = correctComma(line, "details", true);
            line = correctComma(line, "image", false);
            cleaned.add(line);
        }
        return String.join("\n", cleaned);
    }

    private static String correctComma(String line, String lineIdentifier, boolean required) {
        if (line.contains(lineIdentifier) ) {
            //add if required
            if( required &&  !line.trim().endsWith(",")) {
                line = line + ",";
            }
            //remove if existing
            if( !required &&  line.trim().endsWith(",")) {
                line = line.substring(0, line.length() - 1);
            }
        }
        return line;
    }
}
