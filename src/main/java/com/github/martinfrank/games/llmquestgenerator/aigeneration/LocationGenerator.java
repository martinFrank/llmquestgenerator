package com.github.martinfrank.games.llmquestgenerator.aigeneration;

import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LocationGenerator {

    public List<Location> generate(List<Location> locations) {
        for (Location location : locations) {
            String firstGeneration = generateFuzzy(location);
            GeneratedLocationDetails finalGeneration = generateDetails(firstGeneration);
            location.setDetails(finalGeneration);
        }
        return locations;
    }

    private static final String SYSTEM_PROMPT_GENERATE_FUZZY = """
            your job is to create a description of a location
            for a medieval phantasy role play game. you do not describe
            any persons. the user input is a single cue word. describe
            the location with at least 10 sentences.
            """;
    public String generateFuzzy(Location location) {
        String input = location.type.toString()+": "+location.id;
        Prompt prompt = new Prompt(input, SYSTEM_PROMPT_GENERATE_FUZZY);
        String chatResponse = ChatService.request(prompt);
        List<String> sentences = new ArrayList<>(Arrays.asList(chatResponse.split("\\.")));

        Collections.shuffle(sentences);
        String shuffled = String.join(". ", sentences)+".";
        System.out.println("shuffled: " + shuffled);
        return strip(shuffled);
    }

    private static final String SYSTEM_PROMPT_GENERATE_DETAILS = """
            Always respond in JSON format.
            your job is to create a description of a location
            for a medieval role play game. you have to create
             - name of the location, be extra creative for this
             - short description, maximal 3 sentences
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
    public GeneratedLocationDetails generateDetails(String firstGeneration) {
        Prompt prompt = new Prompt(firstGeneration, SYSTEM_PROMPT_GENERATE_DETAILS);
        String chatResponse = ChatService.request(prompt);
        String stripped = strip(chatResponse);
        System.out.println("stripped: "+stripped);
        return new Gson().fromJson(stripped, GeneratedLocationDetails.class);
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
