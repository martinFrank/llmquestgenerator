package com.github.martinfrank.games.llmquestgenerator.person;

import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
import com.github.martinfrank.games.llmquestgenerator.quest.QuestLocationChatbotService;
import com.github.martinfrank.games.llmquestgenerator.quest.QuestSummaryChatbotService;
import com.github.martinfrank.games.llmquestgenerator.quest.QuestTaskChatbotService;
import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestAction;
import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestLocations;
import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestSummary;
import com.github.martinfrank.games.llmquestgenerator.speech.KokoroTextToSpeechClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PersonGenerator {


    private final PersonChatbotService personChatbotService;

    @Autowired
    public PersonGenerator(PersonChatbotService personChatbotService) {
        this.personChatbotService = personChatbotService;
    }

    private static final String SUMMARY = """
            {
              "quest_name": "Whispers in the Woods",
              "quest_giver": {
                "name": "Elder Elara",
                "location": "Village Elder's House",
                "motivation": "Protect her village from the spirit's wrath"
              },
              "quest":{
                "task": "Perform a ritual or solve the mystery of the spirit's past",
                "endboss": "Spirit of the Whispering Woods"\s
              }
            }\s
            """;

    public void generate(String personDescription) {
        //create the top level quest structure
        String questSummaryJson = personChatbotService.createPerson(personDescription, SUMMARY);
        System.out.println("-------summary-------");
        System.out.println(questSummaryJson);
//        QuestSummary questSummary = JsonMapper.fromJson(questSummaryJson, QuestSummary.class);

    }

    //hinweis: gesamt aufnahme: wide shot of a full body figure:

}
