package com.github.martinfrank.games.llmquestgenerator.json;

import com.github.martinfrank.games.llmquestgenerator.quest.model.Quest;
import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestAction;
import org.junit.jupiter.api.Test;

class JsonMapperTest {

    private static final String ACTION_JSON = """
            {
               "winning_action": {
                 "description": "Perform a ritual cleansing using purified water from the Crystal Spring and the Unicorn's horn to dispel the fog.",
                 "location": "Heart of the Whisperwood, where the fog originates",
                 "reason": "The Tainted Unicorn's essence is corrupted by the fog. Its horn holds potent magic that, combined with the purity of the spring water, can cleanse the forest."
               },
               "clues": [
                 {
                   "content": "The whispers grow louder near the Crystal Spring.",
                   "hinted_fact": "The source of the fog might be connected to the Crystal Spring.",
                   "source": "Elderwood Shepherd, near Oakhaven",
                   "location": "Oakhaven Village outskirts"
                 },
                 {
                   "content": "Legends speak of a unicorn whose horn holds the power to purify even the darkest magic.",
                   "hinted_fact": "A unicorn's horn might be crucial in cleansing the fog.",
                   "source": "Village Elder, Oakhaven",
                   "location": "Oakhaven Village center"
                 },
                 {
                   "content": "The whispers tell of a creature trapped within the heart of the Whisperwood, its magic twisted by the fog.",
                   "hinted_fact": "The Tainted Unicorn is the source of the corruption in the forest.",
                   "source": "Strange inscription found on an old ruin near the Whisperwood",
                   "location": "Edge of the Whisperwood"
                 }
               ]
             }
            """;

    @Test
    void testMapping(){
        QuestAction action = JsonMapper.fromJson(ACTION_JSON, QuestAction.class);
        System.out.println(action);
    }
}