
package com.github.martinfrank.games.llmquestgenerator.quest;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestLocationChatbotService {

    private static final String PROMPT_GENERAL_INSTRUCTIONS_7 = """
            You are given a JSON object that contains:
            - A winning_action the players must perform to complete the game
            - A list of clues that help guide players to discover that action, including the location where to find that clue
            Based on this input, generate a connected graph of fantasy-themed locations. The Graph must contain all locations from the clues.

            Your output should follow this JSON structure:
            {
                       "locations": [
                         {
                           "name": "Name of the location",
                           "description": "Short one-sentence description",
                           "connections": ["OtherLocation1", "OtherLocation2"],
                         }
                       ]
                     }

            Guidelines:
            - Use the given clues and distribute them logically across different locations.
            - Generate at least 10 total locations.
            - Ensure the connections form a semi-complex graph – some places should only be reachable via longer paths.
            - Ensure all connections are bi-directional.
            - Use fantasy-themed locations (e.g., shrines, haunted woods, forgotten crypts).
            - Return valid JSON only, with no explanations or extra text or formatting. The result should be machine-readable.

            Input (provided by the user):
            {
              "winning_action": {
                "description": "What the players must do",
                "location": "Where it must happen",
                "reason": "Why it works"
              },
              "clues": [
                {
                  "content": "The clue text",
                  "hinted_fact": "What this clue implies",
                  "source": "from whom or how this clue is discovered"
                  "location": "Where that clue can be found. this information must be found in the generated locations"
                }
              ]
            }
    """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS_8 = """
            The user provides the the quest summary in json format. Use this summary when creating the locations

            Input (provided by the user):
            {
              "quest_name": "a short description of the quest",
              "quest_giver": {
                "name": "name of the quest giver",
                "location": "the location, where the quest starts",
                "motivation": "reason why the quest must be fulfilled"
               },
              "quest":{
                 "task": "this the important part, that is the taks, that the players have to perform to fulfill the quest",
                 "endboss": "the final enemy the party is facing before the task can be fulfilled "
               }
            }

            Return valid JSON only, with no explanations or extra text or formatting.
            The result should be machine-readable.

    """;

    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatClient;

    public QuestLocationChatbotService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    public String createLocations(String questSummary, String taskJson) {
        SystemMessage provideTasksSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_7.concat(taskJson));
        SystemMessage provideSummarySystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_8.concat(questSummary));

        Prompt prompt = new Prompt(List.of(provideTasksSystemMessage,provideSummarySystemMessage ));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }
}
