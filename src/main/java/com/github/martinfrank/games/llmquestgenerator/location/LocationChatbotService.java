
package com.github.martinfrank.games.llmquestgenerator.location;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationChatbotService {

    private static final String PROMPT_GENERAL_INSTRUCTIONS_4 = """
            You generate fantasy role-playing game Locations using the following JSON structure. Stick strictly to the structure below:
            
            {
              "name": "name of the location",
              "appearance_short": "a short description of the location",
              "appearance": "a long description of the appearance of the location. create much content to make this location vivid, like nature",
              "dalle_prompt": "here you have to generate a description that can be used in a ai image generation as prompt. dont forget to mention that it is in a fantasy setting"
            }
            
            
            The user provides the the quest summary in json format. Use this summary when creating the location
            
            Input (provided by the user):
            {
                     "name": "the name of the location - use this input to create the field name in your result and do not modify it",
                     "description": "this is the appearance short description",
                     "connections": "this list contains other locations that can be reached from that location"
            }
            
            Return valid JSON only, with no explanations or extra text. The result should be compact
            and machine-readable.
            """;


    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatClient;

    public LocationChatbotService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }


    public String createLocation(String userMessage, String locationSummary) {
        SystemMessage generalInstructionsSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_4.concat(locationSummary));
        UserMessage currentPromptMessage = new UserMessage(userMessage);

        Prompt prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }
}
