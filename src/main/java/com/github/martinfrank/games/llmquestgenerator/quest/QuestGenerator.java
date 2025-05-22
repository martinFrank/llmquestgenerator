package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestLocations;
import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestSummary;
import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestGenerator {

    private final QuestSummaryChatbotService questSummaryChatbotService;
    private final QuestTaskChatbotService questTaskChatbotService;
    private final QuestLocationChatbotService questLocationChatbotService;

    @Autowired
    public QuestGenerator(QuestSummaryChatbotService questSummaryChatbotService,
                          QuestTaskChatbotService questTaskChatbotService,
                          QuestLocationChatbotService questLocationChatbotService) {
        this.questSummaryChatbotService = questSummaryChatbotService;
        this.questTaskChatbotService = questTaskChatbotService;
        this.questLocationChatbotService = questLocationChatbotService;
    }

    public void generate(String questIdea) {
        //create the top level quest structure
        String questSummaryJson = questSummaryChatbotService.createQuestSummary(questIdea);
        QuestSummary questSummary = JsonMapper.fromJson(questSummaryJson, QuestSummary.class);
        System.out.println("-------summary-------");
        System.out.println(questSummaryJson);

        //create tasks to solve (the quest task)
        String taskJson = questTaskChatbotService.createTasks(questSummaryJson);
        System.out.println("-------tasks-------");
        System.out.println(taskJson);
        QuestTask questTask = JsonMapper.fromJson(taskJson, QuestTask.class);

        //create locations to
        String locationJson = questLocationChatbotService.createLocations(questSummaryJson, taskJson);
        System.out.println("-------locations-------");
        System.out.println(locationJson);
        QuestLocations questLocations = JsonMapper.fromJson(locationJson, QuestLocations.class);
        questLocations.repairConnections();

        System.out.println("-------locations repaired-------");
        System.out.println(JsonMapper.toJson(questLocations));
    }
}
