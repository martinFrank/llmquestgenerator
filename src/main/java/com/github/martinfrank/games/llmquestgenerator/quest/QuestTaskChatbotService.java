
package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestSummary;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestTaskChatbotService {


    private static final String PROMPT_GENERAL_INSTRUCTIONS_3 = """
            You generate goal-driven scenarios for a role-playing game. The player(s) must perform a specific
            final action to win the game. However, this action is not known to them at the start. To discover
            it, they must collect a set of clues throughout the game.
            
            Output the result in valid JSON with the following structure:
            
            {
               "winning_action": {
                 "description": "What the players must do to complete the game",
                 "location": "Where the action must be performed",
                 "reason": "Why this action works (the logic behind it)"
               },
               "clues": [
                 {
                   "content": "Short clue the players can find",
                   "hinted_fact": "What this clue suggests",
                   "source": "from whom or how this clue is discovered. When the source is a persons then give the person a name so i can refer to them later."
                   "location": "the location where the clue is discovered"
                 },
                 {
                   "content": "...",
                   "hinted_fact": "...",
                   "source": "...",
                   "location": "..."
                 }
               ]
             }
            
            Notes:
            - Use fantasy, mystery, or supernatural themes unless otherwise specified.
            - The clues should logically lead players toward understanding the correct winning action.
            - Include at least two clues, but feel free to generate more.
            
            Return valid JSON only, with no explanations or extra text or formatting.
            The result should be machine-readable.
            """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS_2 = """
            The user provides the the quest summary in json format. Use this summary to create the tasks so they may
            fit the summary.
            
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

    public QuestTaskChatbotService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }


    public String createTasks(String questSummary) {
        SystemMessage generalInstructionsSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_3);
        SystemMessage provideQuestSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_2.concat(questSummary));


        Prompt prompt = new Prompt(List.of(generalInstructionsSystemMessage, provideQuestSystemMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }
}
