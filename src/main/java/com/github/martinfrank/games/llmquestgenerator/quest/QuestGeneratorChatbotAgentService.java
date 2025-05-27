
package com.github.martinfrank.games.llmquestgenerator.quest;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestGeneratorChatbotAgentService {

    private static final String PROMPT_GENERAL_INSTRUCTIONS_1 = """
            Du bist eine KI, die Rollenspiel-Quests generiert. Jede Quest folgt strikt dieser Struktur:
            
            ### Begegnung mit dem Questgeber
            Beschreibe, wie der Spieler oder die Spielergruppe den Questgeber trifft. Gib dem Questgeber eine
            Persönlichkeit, ein Motiv und erkläre, was er von der Gruppe möchte.
            
            ### Hinweissuche
            Die Gruppe muss Hinweise sammeln, um dem Rätsel des Quests näher zu kommen. Beschreibe mindestens
            drei verschiedene Orte, Personen oder Situationen, bei denen Hinweise zu finden sind. Jeder Hinweis
            sollte einen Teil der Lösung andeuten.
            
            ### Lösungsfindung
            Wenn genügend Hinweise gesammelt wurden, kann die Gruppe die Lösung ableiten. Erkläre logisch, wie
            die Hinweise zur Lösung führen. Optional kann es falsche Fährten geben.
            
            ### Questabschluss mit Herausforderung
            Um die Quest zu beenden, müssen diese zwei Elemente enthalten sein: 
            - Ein Endboss-Kampf, der notwendig ist, um das Ziel zu erreichen. Beschreibe den Boss,
            seine Fähigkeiten und Besonderheiten des Kampfes.
            - Die finale Handlung, z.B. ein Ritual, eine Übergabe, eine Entscheidung oder eine andere bedeutende
             Handlung, mit der das Quest abgeschlossen wird.
            
            Stelle sicher, dass die Quest originell, spannend und logisch zusammenhängend ist. Der Ton kann je nach 
            Setting episch, mystisch, humorvoll oder düster sein – aber bleibe konsistent. Verwende 
            Fantasy-Elemente (z.B. Magie, Kreaturen, alte Ruinen), sofern nicht anders gewünscht.
            """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS_2 = """
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
              },
              "clues": [
                {
                  "source": "Place or person providing the clue",
                  "type": "Type of clue (e.g. visual, testimony, magical)",
                  "meaning": "What the clue points to"
                },
                {
                  "source": "...",
                  "type": "...",
                  "meaning": "..."
                },
                {
                  "source": "...",
                  "type": "...",
                  "meaning": "..."
                }
              ],
              "solution": {
                "description": "How the clues lead to the solution",
                "red_herrings": ["Optional list of false leads or misleading clues"]
              },
              "final_challenge": {
                "puzzle": "Type of puzzle (e.g. logic, linguistic, mechanical)",
                "final_boss": {
                  "name": "Name or type of boss enemy",
                  "trait": "What makes the fight special"
                },
                "conclusion_action": "What the players must do to complete the quest (e.g. activate artifact, make decision)"
              }
            }
            
            Return valid JSON only, with no explanations or extra text. The result should be compact
            and machine-readable.
            """;


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
                   "source": "Where or how this clue is discovered"
                 },
                 {
                   "content": "...",
                   "hinted_fact": "...",
                   "source": "..."
                 }
               ]
             }
            
            Notes:
            - Use fantasy, mystery, or supernatural themes unless otherwise specified.
            - The clues should logically lead players toward understanding the correct winning action.
            - Include at least two clues, but feel free to generate more.
            
            Return only valid JSON, no comments or explanations.
            """;

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

    private static final String PROMPT_GENERAL_INSTRUCTIONS_5 = """
            You generate locations for a role-playing game world. The locations form a network (nodeModel graph), where each
            location is connected to others and may or may not be relevant for a quest. Each location includes:
            - A short description
            - A flag indicating whether the location is quest_relevant (true/false)
            - A list of connections to other locations by name
            Output your result as valid JSON with the following structure:

            {
                "locations": [
                    {
                        "name": "Name of the location",
                        "description": "Short one-sentence description",
                        "quest_relevant": true,
                        "connections": ["OtherLocation1", "OtherLocation2"]
                    },
                    {
                        "name": "...",
                        "description": "...",
                        "quest_relevant": false,
                        "connections": ["..."]
                    }
                ]
            }

            Guidelines:
            - Generate at least 6 to 10 locations.
            - Use fantasy-themed environments (e.g., ruins, caves, villages, forests).
            - Ensure bi-directional connections (if A connects to B, B should also connect to A).
            - Mix quest-relevant and non-relevant (trivial or flavor) locations.

            Return only valid JSON. No comments, explanations, or extra text.
    """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS_6 = """
            You generate a complex location network (nodeModel graph) for a fantasy role-playing game. Each location may
            contain NPCs or clues and may or may not be quest-relevant. Some locations should only be reachable through
            longer paths, creating meaningful exploration and travel.

            Output the result as valid JSON in the following structure:

            {
                "locations": [
                    {
                        "name": "Name of the location",
                        "description": "Short description",
                        "quest_relevant": true,
                        "connections": ["OtherLocation1", "OtherLocation2"],
                        "npcs": [
                            {
                                "name": "Name of the NPC",
                                "role": "Short description of their role or personality",
                                "has_clue": true
                            }
                        ],
                        "clues": [
                            {
                                "content": "What the clue hints at",
                                "importance": "high | medium | low"
                            }
                        ]
                    },
                    {
                        "name": "...",
                        "description": "...",
                        "quest_relevant": false,
                        "connections": ["..."],
                        "npcs": [],
                        "clues": []
                    }
                ]
            }

            Guidelines:
            - Generate 8 to 12 locations in total.
            - Mix central and remote nodes: not all locations should be equally accessible.
            - Include at least:
                - 3 quest-relevant locations
                - 3 NPCs with clues
                - 2–4 false or low-importance clues
            - Ensure bi-directional connections (if A connects to B, B connects to A).
            - Use fantasy or medieval-themed locations (e.g., ancient ruins, cursed forest, village well, abandoned tower).
            - Keep all descriptions brief and structured.
            - Return valid JSON only, with no explanations or comments.
    """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS_7 = """
            You are given a JSON object that contains:
            - A winning_action the players must perform to complete the game
            - A list of clues that help guide players to discover that action
            Based on this input, generate a connected graph of fantasy-themed locations. The locations may or may not be
            quest-relevant, and may contain NPCs and/or clues. Some clues should be distributed across these locations,
            possibly through interactions with NPCs.

            Your output should follow this JSON structure:

            {
                       "locations": [
                         {
                           "name": "Name of the location",
                           "description": "Short one-sentence description",
                           "quest_relevant": true,
                           "connections": ["OtherLocation1", "OtherLocation2"],
                           "npcs": [
                             {
                               "name": "Name of the NPC",
                               "role": "Short description of their role or personality",
                               "has_clue": true
                             }
                           ],
                           "clues": [
                             {
                               "content": "What the clue hints at",
                               "importance": "high | medium | low"
                             }
                           ]
                         }
                       ]
                     }

            Guidelines:
            - Use the given clues and distribute them logically across different locations and/or NPCs.
            - Generate 8 to 12 total locations.
            - Include at least 3 quest-relevant locations and several flavor-only ones.
            - Some clues can be embedded in the environment; others must be revealed through NPCs.
            - Ensure the connections form a semi-complex graph – some places should only be reachable via longer paths.
            - Ensure all connections are bi-directional.
            - Use fantasy-themed locations (e.g., shrines, haunted woods, forgotten crypts).
            - Return only valid JSON, with no explanations or extra content.

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
                  "source": "Where or how this clue is discovered"
                }
              ]
            }
    """;


    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatClient;

    public QuestGeneratorChatbotAgentService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    private String requestLLM(String userMessage, String systemMessage) {

        var generalInstructionsSystemMessage = new SystemMessage(systemMessage);
        var currentPromptMessage = new UserMessage(userMessage);

        Prompt prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));
        return ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
//                .getContent();
                .getText();
    }

    public String createQuestSummary(String userInput){
        return requestLLM(userInput, PROMPT_GENERAL_INSTRUCTIONS_4);
    }

    public String requestTasks(String userInput){
        return requestLLM(userInput, PROMPT_GENERAL_INSTRUCTIONS_3);
    }
}
