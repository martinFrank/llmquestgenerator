
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

    //077659186683

    private static final String PROMPT_GENERAL_INSTRUCTIONS_7 = """
            You have to create a map of a fantasy role play game. this map is represented as locations that are connected.
            to create a map of locations, you have to use the location graph from the user input locations. use this input und
            enrich the locations nodes. do not alter the data from the fields "id", "type" and "connectedIds"!
            
            Create town relevant locations in the TOWN_ADDON nodes, like smithy, library, other shopping locations or
            tavern, town hall or private houses.
            
            The TOWN_CENTER should be a town center, maybe a market place or a spring or a small forum.
            
            use the QUEST_AREA locations to create the quest task location. if all quest task locations are set then add
            some additional locations that might sound interesting for the players. make sure that the locations name
            matches the user input
            
            Your output should be based on the user input and have this JSON structure:
            {
               "locations": [
                 {
                   "name": "Name of the location - add this field to the existing location",
                   "description": "Short one-sentence description - add this field to the existing location",
                   "location_type": "choose one of these - quest_clue, quest_task, shop, governance, private, nature, forest, underground, other  - add this field to the existing location",
                   "id": "re-use the data from the user input here - do not modify it",
                   "type": "re-use the data from the user input here - do not modify it",
                   "connectedIds": ["re-use the data from the user input here - do not modify it",]
                 }
               ]
            }
            
            Input locations (provided by the user):
            {
              "locations": [
                {
                  "id": "this field is the unique id of the node",
                  "type": "this defines, which type of node that is - there are nodes for town relevant locations and nodes for quest relevant locations",
                  "connectedIds": ["this is a list of all connected ids. they are bi-directional"]
                }
              ]
            }
            
            Guidelines:
            - for every given location from the user input create a  
            - Use fantasy-themed locations (e.g., shrines, haunted woods, forgotten crypts).
            - Return valid JSON only, with no explanations or extra text or formatting. The result should be machine-readable.
            """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS_8 = """
            The user provides the the quest summary in json format. Use this summary when creating the locations. make
            sure that every location mentioned in the user input does also appear in your generated result.
            
            Input quest (provided by the user):
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
            
            Return valid JSON only, with no explanations or extra text or formatting.
            The result should be machine-readable.
            
            """;

    //- location_type: a general type/category label, such as QUEST_CLUE, QUEST_TASK, SHOP, GOVERNANCE, PRIVATE, NATURE, FOREST, UNDERGROUND, DUNGEON, FOREST, SHRINE, etc.

//                     - create additionally 6 adventurous locations like monster lairs or dungeons. give them the DUNGEON location_type.
//                 - create additionally 6 town locations, like shops, smithy, apothecary or other administration buildings. give them the TOWN_ADDON location_type.
    private static final String PROMPT_GENERAL_INSTRUCTIONS_9 = """
                 You are an assistant for a fantasy role-playing game. you have to create various locations where some in-game-events take place.

                 Your task is to create a list of locations, each location with the following fields:
                 - name: a rich, evocative name for the location
                 - description: a short, atmospheric description that fits into a fantasy world
                 - location_type: a general type/category label, choose a proper of  type from this list: TOWN, DUNGEON, WILDERNESS 
        
                 Important Rules:
                 - create locations for every location from the user input.
                 - give them the location_type a proper type, one of TOWN, DUNGEON, WILDERNESS

        
                 Return your result strictly in this JSON format:        
                {
                  "locations": [
                    {
                      "name": "example name",
                      "description": "Atmospheric fantasy description...", 
                      "location_type": "choose one of these -  TOWN, DUNGEON, WILDERNESS",
                    }
                  ]
                }
        
                Additionally you get this quest information. use this information to change the locations according to the quest
                Input quest (provided by the user):
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
            """;

    private static final String SYSTEM_PROMPT = """
                 You are an assistant for a fantasy role-playing game. you have to create various locations where some in-game-events take place.

                 Your task is to create a list of locations, each location with the following fields:
                 - name: a rich, evocative name for the location
                 - description: a short, atmospheric description that fits into a fantasy world
                 - location_type: a general type/category label, choose a proper of  type from this list: TOWN, DUNGEON, WILDERNESS
                 - create at least 8 locations of each kind, TOWN, DUNGEON, WILDERNESS
                 
                 Return your result strictly in this JSON format:        
                {
                  "locations": [
                    {
                      "name": "example name",
                      "description": "Atmospheric fantasy description...", 
                      "location_type": "choose one of these -  TOWN, DUNGEON, WILDERNESS",
                    }
                  ]
                }
        
                Additionally you get this quest information. use this information to change the locations according to the quest
                Input quest (provided by the user):
                {
                  "quest_name": "Short title of the quest",
                  "quest_giver": "Name of the quest giver",
                  "endboss": "final boss that must be defeated before doing the task",
                  "quest_summary": "what the quest taker has to do to solve the quest",
                  "quest_details": "a detailed description of the quest including a summary of the subtasks", 
                   }
                }
            """;

    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatClient;

    public LocationChatbotService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }


    public String createLocationDetails(String userMessage, String locationSummary) {
        SystemMessage generalInstructionsSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_4.concat(locationSummary));
        UserMessage currentPromptMessage = new UserMessage(userMessage);

        Prompt prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }

    public String createQuestLocations(String questSummaryJson) {
//        SystemMessage provideSummarySystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_8.concat(questSummaryJson));
//        SystemMessage provideTasksUserMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_7.concat(graphJson));

        SystemMessage provideTasksUserMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS_9);
        UserMessage UserMessage = new UserMessage(questSummaryJson);

        Prompt prompt = new Prompt(List.of(provideTasksUserMessage, UserMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }

    public String createSideLocations(String questSummaryJson) {
        SystemMessage provideTasksUserMessage = new SystemMessage(SYSTEM_PROMPT);
        UserMessage UserMessage = new UserMessage(questSummaryJson);

        Prompt prompt = new Prompt(List.of(provideTasksUserMessage, UserMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }
}
