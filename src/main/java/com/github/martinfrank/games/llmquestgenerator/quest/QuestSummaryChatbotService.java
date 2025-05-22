
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
            The content should be only placeholders or short descriptions, not detailed
            texts. Full details will be generated later when players interact with the
            quest. Stick strictly to the structure below:
            
            {
              "quest_name": "Short title of the quest",
              "quest_giver": {
                "name": "Name of the quest giver",
                "location": "Where the quest giver is encountered",
                "motivation": "Why they need help"
               }
               "quest":{
                 "task": "what the quest taker has to do to solve the quest",
                 "endboss": "final boss that must be defeated before doing the task"
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
