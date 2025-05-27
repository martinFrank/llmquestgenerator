package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
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
    private final KokoroTextToSpeechClient kokoroTextToSpeechClient;

    @Autowired
    public QuestGenerator(QuestSummaryChatbotService questSummaryChatbotService,
                          KokoroTextToSpeechClient kokoroTextToSpeechClient) {
        this.questSummaryChatbotService = questSummaryChatbotService;
        this.kokoroTextToSpeechClient = kokoroTextToSpeechClient;
    }

    public void generate(String questIdea) {
        //create the top level quest structure
        String questSummaryJson = questSummaryChatbotService.createQuestSummary(questIdea);
        System.out.println("-------summary-------");
        System.out.println(questSummaryJson);
        QuestSummary questSummary = JsonMapper.fromJson(questSummaryJson, QuestSummary.class);
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
