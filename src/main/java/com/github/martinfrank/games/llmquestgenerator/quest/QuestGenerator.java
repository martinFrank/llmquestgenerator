package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
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
public class QuestGenerator {


    private final QuestSummaryChatbotService questSummaryChatbotService;
    private final QuestTaskChatbotService questTaskChatbotService;
    private final QuestLocationChatbotService questLocationChatbotService;
    private final KokoroTextToSpeechClient kokoroTextToSpeechClient;

    @Autowired
    public QuestGenerator(QuestSummaryChatbotService questSummaryChatbotService,
                          QuestTaskChatbotService questTaskChatbotService,
                          QuestLocationChatbotService questLocationChatbotService,
                          KokoroTextToSpeechClient kokoroTextToSpeechClient) {
        this.questSummaryChatbotService = questSummaryChatbotService;
        this.questTaskChatbotService = questTaskChatbotService;
        this.questLocationChatbotService = questLocationChatbotService;
        this.kokoroTextToSpeechClient = kokoroTextToSpeechClient;
    }

    public void generate(String questIdea) {
        //create the top level quest structure
        String questSummaryJson = questSummaryChatbotService.createQuestSummary(questIdea);
        System.out.println("-------summary-------");
        System.out.println(questSummaryJson);
        QuestSummary questSummary = JsonMapper.fromJson(questSummaryJson, QuestSummary.class);
//        System.out.println("-------summary object-------");
//        System.out.println(questSummary);

        //create tasks to solve (the quest task)
        String taskJson = questTaskChatbotService.createTasks(questSummaryJson);
        System.out.println("-------tasks-------");
        System.out.println(taskJson);
        QuestAction questAction = JsonMapper.fromJson(taskJson, QuestAction.class);
//        System.out.println("-------tasks object-------");
//        System.out.println(questAction);

        //create locations to
        String locationJson = questLocationChatbotService.createLocations(questSummaryJson, taskJson);
//        System.out.println("-------locations-------");
//        System.out.println(locationJson);
        QuestLocations questLocations = JsonMapper.fromJson(locationJson, QuestLocations.class);
        questLocations.repairConnections();
//        System.out.println("-------locations object-------");
//        System.out.println(questLocations);

        System.out.println("-------locations repaired-------");
        System.out.println(JsonMapper.toJson(questLocations));

        int debug = 1;
    }

    public void speak(String text) {
        byte[] audio = kokoroTextToSpeechClient.speak(text);

        try {
            Files.write(Paths.get("output.mp3"), audio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
