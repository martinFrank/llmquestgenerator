package com.github.martinfrank.games.llmquestgenerator.aigeneration.actor;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.ChatService;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.Prompt;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationDescription;
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

    public static List<ActorDetails> generate(List<Actor> actors, List<LocationDescription> locations) {
        List<ActorDetails> result = new ArrayList<>();
        List<ActorRequestEntry> history = new ArrayList<>();
        for (Actor actor : actors) {
            LOGGER.debug("generate actor id: {}", actor.id);
            String locationDescription = locations.stream()
                    .filter(l -> Objects.equals(l.locationId(), actor.locationId))
                    .findAny().map(LocationDescription::generatedDescription).orElse("unknown");
            String description = generateDescription(actor, locationDescription, history);
            String actorName = generateName(description, locationDescription, history);
            String prompt = generateImagePrompt(description, locationDescription, history);
            result.add(new ActorDetails(actor.id, actorName, description, prompt));
        }
        return result;
    }

    private static String generateDescription(Actor actor, String locationDescription, List<ActorRequestEntry> history) {
        String systemPrompt = """
                Your job is to create a description of a person for
                a medieval role play game. you do not describe any
                locations. You do not describe the location. do not
                introduce names or locations other than provided.
                the user input is json that contains the history of
                the previous generation requests and a short description
                of the person and a description of the location. Do not
                comment the output, just generate the description of the
                location.
                
                user input:
                {
                    "history":[
                        {
                            "actorDescription" : "previous requested location",
                            "locationDescription" : "previous provided location for the actor description",
                            "generatedDescription" : "your previous generated description of that actor",
                            "generatedImage" : "your previous generated image prompt given to that actor"
                        }
                    ],
                    "actorDescription" : "the actual description of the actor that should be used for generation",
                    "locationDescription" : "the actual description of the location where the actor resides",
                }
                
                """;

        ActorGenerationRequest userPrompt = new ActorGenerationRequest(history, actor.description, locationDescription);
        String userPromptJson = new Gson().toJson(userPrompt);
        Prompt prompt = Prompt.builder()
                .systemPrompt(systemPrompt)
                .userPrompt(userPromptJson)
                .random()
                .build();
        return trim(ChatService.request(prompt));
    }

    private static String generateName(String actorDescription, String locationDescription, List<ActorRequestEntry> history) {
        String systemPrompt = """
                Your job is to create the name of an actor for a medieval
                role play game. You do not describe any locations. do not
                introduce names or locations other than provided. the
                user input is json that contains the history of the
                previous generation requests and an actor description that
                holds a long description of the actor. it also contains a
                long description of the location, where the actor resides.
                your job is to create a short name of the actor. Do not
                comment the output, just generate the name of the actor.
                
                user input:
                {
                    "history":[
                        {
                            "actorDescription" : "previous requested location",
                            "locationDescription" : "previous provided location for the actor description",
                            "generatedDescription" : "your previous generated description of that actor",
                            "generatedImage" : "your previous generated image prompt given to that actor"
                        }
                    ],
                    "actorDescription" : "the actual description of the actor that should be used for generation",
                    "locationDescription" : "the actual description of the location where the actor resides",
                }
                """;

        ActorGenerationRequest userPrompt = new ActorGenerationRequest(history, actorDescription, locationDescription);
        String userPromptJson = new Gson().toJson(userPrompt);

        Prompt prompt = Prompt.builder()
                .systemPrompt(systemPrompt)
                .userPrompt(userPromptJson)
                .build();
        return trim(ChatService.request(prompt));
    }

    private static String generateImagePrompt(String actorDescription, String locationDescription, List<ActorRequestEntry> history) {
        String systemPrompt = """
                Your job is to create a prompt for a image generation ai.
                the user input is json that contains the history of the
                previous generation requests and request, that holds a
                short description of the location. do not comment the
                output, just generate the prompt.
                
                user input:
                {
                    "history":[
                        {
                            "actorDescription" : "previous requested location",
                            "locationDescription" : "previous provided location for the actor description",
                            "generatedDescription" : "your previous generated description of that actor",
                            "generatedImage" : "your previous generated image prompt given to that actor"
                        }
                    ],
                    "actorDescription" : "the actual description of the actor that should be used for generation",
                    "locationDescription" : "the actual description of the location where the actor resides",
                }
                """;

        ActorGenerationRequest userPrompt = new ActorGenerationRequest(history, actorDescription, locationDescription);
        String userPromptJson = new Gson().toJson(userPrompt);

        Prompt prompt = Prompt.builder()
                .systemPrompt(systemPrompt)
                .userPrompt(userPromptJson)
                .build();
        return trim(ChatService.request(prompt));
    }

    private static String trim(String input) {
        return input.replace("\n", " ").trim();
    }
}
