package com.github.martinfrank.games.llmquestgenerator.aigeneration.actor;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.ChatService;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.Prompt;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActorGenerator {

    private static final Logger LOGGER = LogManager.getLogger(ActorGenerator.class);

    private ActorGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static void generate(List<Actor> actors, List<Location> locations) {
        for (Actor actor : actors) {
            LOGGER.debug("generate actor id: {}, type: {}", actor.id, actor.type.toString());
            Location location = locations.stream().filter(l -> Objects.equals(l.id, actor.locationId)).findAny().orElseThrow();
            String locationDescription = location.getDetails().details;
            String fuzzy = generateFuzzy(actor, locationDescription);
            ActorDetails details = generateDetails(fuzzy, locationDescription);
            actor.setDetails(details);
        }
    }

    private static final String SYSTEM_PROMPT_GENERATE_FUZZY = """
            your job is to create a description of a person
            for a medieval role play game. you do not describe
            any locations. the user input is json file. describe
            the person with at least 10 sentences. do not add any
            explanations or formatting
            
            Json input format:
            {
                "actorType" : "the type person",
                "actorId" : "id of the person",
                "location" : "location, where the person is dwelling"
            }
            """;

    private static String generateFuzzy(Actor actor, String locationDescription) {
        String userPrompt = new Gson().toJson(new ActorFuzzyInput(actor.type.toString(), actor.id, locationDescription));
        Prompt prompt = Prompt.builder()
                .systemPrompt(SYSTEM_PROMPT_GENERATE_FUZZY)
                .userPrompt(userPrompt)
                .random()
                .build();
        return ChatService.request(prompt);
    }

    private static ActorDetails generateDetails(String fuzzy, String location) {
        String systemPrompt = """
                Always respond in JSON format.
                your job is to create a description of a person
                for a medieval role play game. you have to create
                 - name of the person, avoid adjectives, be extra creative for this
                 - short description, a summary of the user input, maximal 3 sentences
                 - a prompt to generate a image of the person, use the information from location
                you do not describe any locations.
                
                this is the user input JSON:
                {
                    "actorLongDescription": "a long description of the person",
                    "actorLocation": "the location where the person dwells",
                }
                
                The generated JSON output should follow this structure
                {
                    "name" : "name of the person",
                    "details" : "short description of the person, maximal 3 sentences",
                    "image" : "a prompt to generate a image of the person"
                }
                """;
        ActorDetailInput detailInput = new ActorDetailInput(fuzzy, systemPrompt);
        String userInput = new Gson().toJson(detailInput);
        Prompt prompt = Prompt.builder()
                .systemPrompt(systemPrompt)
                .userPrompt(userInput)
                .build();
        String chatResponse = ChatService.request(prompt);
        String stripped = strip(chatResponse);
        return new Gson().fromJson(stripped, ActorDetails.class);
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

    private static String correctComma(String line, String lineIdentifier, boolean commaDesired) {
        if (line.contains(lineIdentifier)) {
            //add if required
            if (commaDesired && !line.trim().endsWith(",")) {
                line = line + ",";
            }
            //remove if existing
            if (!commaDesired && line.trim().endsWith(",")) {
                line = line.substring(0, line.length() - 1);
            }
        }
        return line;
    }
}
