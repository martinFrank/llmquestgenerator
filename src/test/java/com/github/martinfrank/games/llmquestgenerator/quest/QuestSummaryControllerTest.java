package com.github.martinfrank.games.llmquestgenerator.quest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestSummaryControllerTest {

    final String json = """
            ```\n{\n  \"winning_action\": {\n    \"description\": \"Use the ancient ritual of the Moonlit Reflections to calm the beast's fury and banish it back to its slumbering depths.\",\n    \"location\": \"The Heart of the Swamp, where the ancient ritual site lies hidden behind a cascading waterfall\",\n    \"reason\": \"By performing this ritual, the players will tap into the mystical energies of the swamp, resonating with the monster's own connection to the land and soothing its savage heart. The reflected light of the moon will guide the beast back to its natural state, rendering it docile and harmless.\"\n  },\n  \"clues\": [\n    {\n      \"content\": \"A cryptic poem etched into the bark of an ancient cypress tree reads: 'When shadows dance upon the water's face, seek the reflection of a peaceful place.'\",\n      \"hinted_fact\": \"The Moonlit Reflections ritual involves using the swamp's unique geography to create a mirrored image that will calm the monster.\",\n      \"source\": \"Found in the heart of the swamp, near the ancient cypress tree\"\n    },\n    {\n      \"content\": \"A local shaman reveals that the beast was once a guardian of the swamp, tasked with maintaining the delicate balance of nature within its domain. However, a dark force corrupted its intentions, twisting it into a monstrous creature.\",\n      \"hinted_fact\": \"The monster's corruption is tied to a specific location in the swamp, and restoring balance will require a deep understanding of the land's ancient magic.\",\n      \"source\": \"Shared by the local shaman during a private audience\"\n    }\n  ]\n}\n```
            """;


    @Test
    void testResult(){
        System.out.println(json);

    }

}