
package com.github.martinfrank.games.llmquestgenerator.quest;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestSummaryChatbotService {

    private static final String PROMPT_GENERAL_INSTRUCTIONS_4 = """
            You generate role-playing game quests using the following JSON structure.
            - start_location and quest_giver are just only placeholders or short descriptions, not detailed
            - endboss is the final enemy that the party has to battle before they can finish the task
            - quest_summary is linear, there are no options what to do
            - quest details explain what must be done including the subtasks 
            - a subtask may or may not involve a person. the person is just only placeholders or short descriptions, not detailed
            - a subtask may or may not involve a object
            - each subtask has a clue, the clue describes, how the players find out about the subtask
            - generate at least thee subtasks
            
            Stick strictly to the structure below:
            {
              "quest_name": "Short title of the quest",
              "start_location": "the name of the location where it starts and where the quest giver is",
              "quest_giver": "Name of the quest giver",
              "endboss": "final boss that must be defeated before doing the task",
              "quest_summary": "what the quest taker has to do to solve the quest",
              "quest_details": "a detailed description of the quest including a summary of the subtasks",
              "sub_tasks":[
                  {
                      "subtask_description":"what the players have to do in this location",
                      "subtask_location":"short name of the location",
                      "subtask_object":"the thing that is involved in this subtask",
                      "subtask_person":"the person involved in this subtask"
                      "subtask_type":"battle_loot, invokeItem, search, talk, riddle - pick the proper one"
                      "clue":{
                          "hint":"the hint the players get, describes what to do in the subtask",
                          "reveal_type":"how the players gained the hint, select one of these - rumor, talk, dropped_after_fight, riddle - pick the proper one",
                          "source":"the source of the hint, the provider of the clue - this field may not be empty",
                          "location":"where the clue can be obtained",
                      }
                  }
                  ] 
               }
            }
            
            Return valid JSON only, with no explanations or extra text. The result should be compact
            and machine-readable.
            """;


    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatClient;

    public QuestSummaryChatbotService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }


    public String createQuestSummary(String userMessage){
        SystemMessage generalInstructionsSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_4);
        UserMessage currentPromptMessage = new UserMessage(userMessage);

        Prompt prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }

}
