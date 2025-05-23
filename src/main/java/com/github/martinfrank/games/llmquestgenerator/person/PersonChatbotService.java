
package com.github.martinfrank.games.llmquestgenerator.person;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonChatbotService {

    private static final String PROMPT_GENERAL_INSTRUCTIONS_4 = """
            You generate fantasy role-playing game Persons (NPCs) using the following JSON structure.
            The content should be only placeholders or short descriptions, not detailed
            texts. Full details will be generated later when players interact with the
            quest. Stick strictly to the structure below:
            
            {
              "name": "name of the person",
              "appearance_short": "a short description of the apearance of the person, some words or one sentence ",
              "appearance": "a long description of the appearance of the person, including face, hair, clothes, age, gender, probably jewellery or other accessoires",
              "dalle_prompt": "here you have to generate a description that can be used in a ai image generation as prompt. dont forget to mention that it is in a fantasy setting"
            }
            
            
            The user provides the the quest summary in json format. Use this summary when creating the person

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
            
            Return valid JSON only, with no explanations or extra text. The result should be compact
            and machine-readable.
            """;


    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatClient;

    public PersonChatbotService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }


    public String createPerson(String userMessage, String questSummary){
        SystemMessage generalInstructionsSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_4.concat(questSummary));
        UserMessage currentPromptMessage = new UserMessage(userMessage);

        Prompt prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }

}
