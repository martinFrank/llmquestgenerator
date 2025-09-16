package com.github.martinfrank.games.llmquestgenerator.aigeneration.location;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.ChatService;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.Prompt;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.Trimmer;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LocationDescriptionGenerator {

    private static final Logger LOGGER = LogManager.getLogger(LocationDescriptionGenerator.class);

    private LocationDescriptionGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static List<LocationDescription> generate(List<Location> locations, String plot) {
        List<LocationDescription> result = new ArrayList<>();
        for (Location location : locations) {
            LOGGER.debug("generation location {}", location.id);
            String description = generateDescription(location, plot);
            String name = generateName(result, plot, description, location.type.toString());
            LOGGER.debug("generatedName: {}", name);
            LOGGER.debug("generatedDescription: {}", description);
            result.add(new LocationDescription(location.id, name, description));
        }
        return result;
    }

    private static String generateDescription(Location location, String plot) {
        String systemPrompt = """
                Your job is to create a description of a location
                for a medieval role play game. You do not describe
                any persons. do not introduce names or locations other
                than provided. the user input is json that holds a short description of the location. Do not
                comment the output, just generate the description of the
                location. the plot is an information that
                will not change and provide information about the
                background of the game. Do not start the description with "the air"!
                
                user input:
                {
                    "plot": "the overall plot in which the game takes place.",
                    "locationDescription" : "the actual description of the location that should be used for generation", 
                    "locationType" : "the type of location"
                }
                
                """;

        boolean startWithTheAir;
        String result = "";
        do {
            startWithTheAir = false;
            LocationGenerationRequest userPrompt = new LocationGenerationRequest(plot, location.generation, location.type.toString());
            String userPromptJson = new Gson().toJson(userPrompt);
            Prompt prompt = Prompt.builder()
                    .systemPrompt(systemPrompt)
                    .userPrompt(userPromptJson)
                    .straight()
                    .build();

            result = Trimmer.trim(ChatService.request(prompt));
            LOGGER.debug("generated description: {}", result);
            if (result.toLowerCase().startsWith("the air")) {
                startWithTheAir = true;
            }
        } while (startWithTheAir);
        return result;
    }

    private static String generateName(List<LocationDescription> descriptions, String plot, String description, String type) {
        String systemPrompt = """
                Your job is to create the name of a location
                for a medieval role play game. use the description to generate a name.
                use the adjectives from the description to generate a name. add adjectives
                that could describe the location.
                
                Do not comment the output, just generate the name!
                
                user input:
                {
                    "plot": "the overall plot in which the game takes place.",
                    "locationDescription" : "the actual description of the location that should be used for generation",
                    "locationType" : "the type of location"
                }
                """;

        LocationGenerationRequest userPrompt = new LocationGenerationRequest(plot, description, type);
        String userPromptJson = new Gson().toJson(userPrompt);

        String name = "";
        List<String> names = descriptions.stream().map(LocationDescription::generatedName).toList();
        boolean isUnique;
        do {
            isUnique = true;
            Prompt prompt = Prompt.builder()
                    .systemPrompt(systemPrompt)
                    .userPrompt(userPromptJson)
                    .random()
                    .build();
            name = Trimmer.trim(ChatService.request(prompt));
            LOGGER.debug("generated name: {}", name);
            for (String existing : names) {

                String miniName = existing.replace(" ", "").toLowerCase();
                if (name.replace(" ", "").toLowerCase().equals(miniName)) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);
        return name;
    }
}
