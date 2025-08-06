package com.github.martinfrank.games.llmquestgenerator.aigeneration.quest;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.ChatService;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.Prompt;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.github.martinfrank.games.llmquestgenerator.quest.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestGenerator {

    private static final Logger LOGGER = LogManager.getLogger(QuestGenerator.class);

    private QuestGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static void generate(List<Quest> quests, List<Task> tasks, List<Actor> actors, List<Location> locations) {
        for (Quest quest : quests) {
            LOGGER.debug("generate quest id: {}", quest.id);
            List<Task> questTasks = tasks.stream().filter(t -> quest.taskIds.contains(t.id) ).toList();
            for(Task task: questTasks){
                switch (task.type){
                    case TaskType.TALK_TO -> generateTalkTask(quest, (TalkTask)task, actors, locations);
                    case TaskType.SEARCH_AT -> generateSearchTask(quest, (SearchTask)task, locations);
                    case TaskType.APPLY -> generateApplyTask(quest, (ApplyTask) task, locations);
                    case TaskType.FIGHT -> generateFightTask(quest, (FightTask) task, actors, locations);
                    default -> throw new IllegalStateException("Unexpected value: " + task.type);
                };
            }
//            quest.setDetails(details);
        }
    }

    private static final String SYSTEM_PROMPT_GENERATE_TALK_TASK_SUCCESS = """
                Always respond in JSON format.
                your job is to create a conversation in a phantasy role play game.
                create a dialog between the player and the actor, where the player
                will learn about the mystery. do not generate additional
                information other than provided by the user input, instead let the actor
                admit that he/she doesnt know certain details. do not describe the
                actors behaviour, only the text spoken from the actor
                
                this is the user input JSON:
                {
                    "quest": "a short description of the overarching quest. the dialog is a part of the whole quest",
                    "actor": "a description of the person",
                    "location": "a description of the location, where the dialog is happening",
                    "mystery": "the information that will be revealed during the conversation"
                }
                
                The generated JSON output should follow this structure.
                [
                    {"actor": "the spoken text from an actor"},
                    {"player": "the spoken text from another actor"},
                    {"actor": "the spoken text from an actor"},
                    {"player": "the spoken text from another actor"}
                ]
                """;

    private static final String SYSTEM_PROMPT_GENERATE_TALK_TASK_FAILURE = """
                Always respond in JSON format.
                your job is to create a conversation in a phantasy role play game.
                the conversion happens between the player and the person.
                create a dialog between the player and the actor, but the player
                will not learn about the mystery. The result of the dialog will be
                that the player has to come again later. do not generate additional
                information other than provided by the user input, instead let the actor
                admit that he/she doesnt know certain details. do not describe the
                actors behaviour, only the text spoken from the actor

                this is the user input JSON:
                {
                    "quest": "a short description of the overarching quest. the dialog is a part of the whole quest",
                    "actor": "a description of the person",
                    "location": "a description of the location, where the dialog is happening",
                    "mystery": "the information that will be revealed during the conversation"
                }

                The generated JSON output should follow this structure.
                [
                    {"actor": "the spoken text from an actor"},
                    {"player": "the spoken text from another actor"},
                    {"actor": "the spoken text from an actor"},
                    {"player": "the spoken text from another actor"}
                ]
                """;

    private static void generateTalkTask(Quest quest, TalkTask task, List<Actor> actors, List<Location> locations) {
        Actor actor = actors.stream().filter(a -> a.id.equals(task.actorId)).findFirst().orElseThrow();
        Location location = locations.stream().filter(l -> l.id.equals(actor.locationId)).findFirst().orElseThrow();
        TalkTaskRequest positiveTalkTaskRequest = new TalkTaskRequest(quest.plot, actor.getDetails().details, location.getDetails().details, task.mystery);
        String requestPositiveJson = new Gson().toJson(positiveTalkTaskRequest);
        LOGGER.debug("request: {}", requestPositiveJson);
        Prompt promptSuccess = Prompt.builder()
                .systemPrompt(SYSTEM_PROMPT_GENERATE_TALK_TASK_SUCCESS)
                .userPrompt(requestPositiveJson)
                .build();
        String success = stripTalkTask(ChatService.request(promptSuccess));
        LOGGER.debug("success: {}", success);
        Type listType = new TypeToken<List<DialogLine>>() {}.getType();
        List<DialogLine> successDetails = new Gson().fromJson(success, listType);
        task.setSuccessDetails(new TalkTaskDetail(successDetails));

        TalkTaskRequest negativeTalkTaskRequest = new TalkTaskRequest(quest.plot, actor.getDetails().details, location.getDetails().details, null);
        String requestNegativeJson = new Gson().toJson(negativeTalkTaskRequest);
        Prompt promptFailure = Prompt.builder()
                .systemPrompt(SYSTEM_PROMPT_GENERATE_TALK_TASK_FAILURE)
                .userPrompt(requestNegativeJson)
                .build();
        String failure = stripTalkTask(ChatService.request(promptFailure));
        LOGGER.debug("failure: {}", failure);
        List<DialogLine> failDetails = new Gson().fromJson(success, listType);
        task.setFailDetails(new TalkTaskDetail(failDetails));
    }


    private static final String SYSTEM_PROMPT_GENERATE_SEARCH_TASK_SUCCESS = """
                Always respond in JSON format.
                your job is to create a description of how the players
                search for an object. The players are part of a phantasy
                role play game. in the end they will find the object.
                describe also how the player finds the object.
                do not generate additional information other
                than provided by the user input
                
                this is the user input JSON:
                {
                    "quest": "a short description of the quest plot",
                    "location": "a description of the location",
                    "desiredObject": "the object the players are looking for"
                }
                
                The generated JSON output should follow this structure
                {
                    "summary" : "summary of the task",
                    "detail" : "describe how the search goes"
                }
                """;

    private static final String SYSTEM_PROMPT_GENERATE_SEARCH_TASK_FAILURE = """
                Always respond in JSON format.
                your job is to create a description of how the players
                search for an object. The players are part of a phantasy
                role play game. Sadly the players will not find the
                desired object. do not generate additional information other
                than provided by the user input
                
                this is the user input JSON:
                {
                    "quest": "a short description of the quest plot",
                    "location": "a description of the location",
                    "desiredObject": "the object the players are looking for"
                }
                
                The generated JSON output should follow this structure
                {
                    "summary" : "summary of the task",
                    "detail" : "describe how the search goes"
                }
                """;

    private static void generateSearchTask(Quest quest, SearchTask task, List<Location> locations) {
        Location location = locations.stream().filter(l -> l.id.equals(task.locationId)).findFirst().orElseThrow();
        SearchTaskRequest searchTaskRequest = new SearchTaskRequest(quest.plot, location.getDetails().details, task.desiredObject);
        String requestJson = new Gson().toJson(searchTaskRequest);
        Prompt promptSuccess = Prompt.builder()
                .systemPrompt(SYSTEM_PROMPT_GENERATE_SEARCH_TASK_SUCCESS)
                .userPrompt(requestJson)
                .build();
        String success = stripSearchTask(ChatService.request(promptSuccess));
        SearchTaskDetail successDetails = new Gson().fromJson(success, SearchTaskDetail.class);
        task.setSuccess(successDetails);

        Prompt promptFailure = Prompt.builder()
                .systemPrompt(SYSTEM_PROMPT_GENERATE_SEARCH_TASK_FAILURE)
                .userPrompt(requestJson)
                .build();
        String failure = stripSearchTask(ChatService.request(promptFailure));
        SearchTaskDetail failureDetails = new Gson().fromJson(failure, SearchTaskDetail.class);
        task.setFailure(failureDetails);
    }

    private static final String SYSTEM_PROMPT_APPLY_TASK = """
                your job is to create a description of how the players
                applies an object. Describe how the player executes
                the action. the player is a player in a phantasy role
                play game. do not add any explanations or question, only
                describe the task.
                
                this is the user input JSON:
                {
                    "quest": "a short description of the quest plot",
                    "player" : "name of the player",
                    "location": "a description of the location, where the apply takes place",
                    "action": "the action that is performed"
                }
                
                """;


    private static void generateApplyTask(Quest quest, ApplyTask task, List<Location> locations) {
        Location location = locations.stream().filter(l -> l.id.equals(task.locationId)).findFirst().orElseThrow();
        ApplyTaskRequest applyTaskRequest = new ApplyTaskRequest(quest.plot, "Radagast", location.getDetails().details, task.applyAction);
        String requestJson = new Gson().toJson(applyTaskRequest);
        Prompt promptSuccess = Prompt.builder()
                .systemPrompt(SYSTEM_PROMPT_APPLY_TASK)
                .userPrompt(requestJson)
                .random()
                .build();
        String detail = stripApplyTask(ChatService.request(promptSuccess));
        LOGGER.debug("result: {}", detail);
        task.setDetail(detail);

    }
    private static final String SYSTEM_PROMPT_FIGHT_TASK = """
                your job is to create a description of how the players
                engage into a fight.the player is a player in a phantasy role
                play game. do not add any explanations or question, only
                describe the beginning of the fight.
                
                this is the user input JSON:
                {
                    "quest": "a short description of the quest plot",
                    "location": "a description of the location, where the apply takes place",
                    "enemy": "description of the enemy"
                }
                
                """;


    private static void generateFightTask(Quest quest, FightTask task, List<Actor> actors, List<Location> locations) {
        Location location = locations.stream().filter(l -> l.id.equals(task.locationId)).findFirst().orElseThrow();
        Actor actor = actors.stream().filter(a -> a.id.equals(task.actorId)).findFirst().orElseThrow();
        FightTaskRequest fightTaskRequest = new FightTaskRequest(quest.plot, location.getDetails().details, actor);
        String requestJson = new Gson().toJson(fightTaskRequest);
        Prompt promptSuccess = Prompt.builder()
                .systemPrompt(SYSTEM_PROMPT_FIGHT_TASK)
                .userPrompt(requestJson)
                .random()
                .build();
        String result = stripFightTask(ChatService.request(promptSuccess));
        LOGGER.debug("result: {}", result);
        task.setDetail(result);

    }

    private static String stripFightTask(String input) {
        input = input.replace("”", "\"");
        input = input.replace("„", "\"");
        input = input.replace("“", "\"");
        input = input.replace("‘", "'");
        input = input.replace("’", "'");
        input = input.replace("…", "...");
        String[] lines = input.split("\n");
        List<String> cleaned = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty() ) {
                cleaned.add(line);
            }
        }
        return String.join("\n", cleaned);
    }

    private static String stripApplyTask(String input) {
        input = input.replace("”", "\"");
        input = input.replace("„", "\"");
        input = input.replace("“", "\"");
        input = input.replace("‘", "'");
        input = input.replace("’", "'");
        input = input.replace("…", "...");
        String[] lines = input.split("\n");
        List<String> cleaned = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty() ) {
                cleaned.add(line);
            }
        }
        return String.join("\n", cleaned);
    }

    private static String stripTalkTask(String input) {
        input = input.replace("```json", "");
        input = input.replace("```", "");
        input = input.replace("”", "\"");
        input = input.replace("„", "\"");
        input = input.replace("“", "\"");
        input = input.replace("‘", "'");
        input = input.replace("’", "'");
        input = input.replace("…", "...");
        input = input.replace("\"\"\"", "\"");
        String[] lines = input.split("\n");
        List<String> cleaned = new ArrayList<>();
        for (String line : lines) {
//            line = correctComma(line, "{ \"", true);
//            line = correctComma(line, "detail", false);
            cleaned.add(line);
        }
        return String.join("\n", cleaned);
    }

    private static String stripSearchTask(String input) {
        input = input.replace("```json", "");
        input = input.replace("```", "");
        input = input.replace("\" \"", "\": \"");
        input = input.replace("”", "\"");
        input = input.replace("„", "\"");
        input = input.replace("“", "\"");
        String[] lines = input.split("\n");
        List<String> cleaned = new ArrayList<>();
        for (String line : lines) {
            line = correctComma(line, "summary", true);
            line = correctComma(line, "detail", false);
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
