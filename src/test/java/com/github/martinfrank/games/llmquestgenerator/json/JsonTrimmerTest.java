package com.github.martinfrank.games.llmquestgenerator.json;

import org.junit.jupiter.api.Test;

class JsonTrimmerTest {

    private static final String response = "<think>\n" +
            "Okay, so I need to create a role-playing game quest based on the prompt given: \"a small village surrounded by a mysterious fog where a tainted unicorn exists.\" Let me break this down.\n" +
            "\n" +
            "First, the quest id. It should be short but descriptive. Maybe something like \"Tainted Unicorn's Shadow\" or \"Fog of Deception.\" I think \"Fog of Deception\" sounds catchy and ties in the elements mentioned.\n" +
            "\n" +
            "Next, the quest giver. They need to have a id, location, and motivation. The location could be within the village surrounded by fog, perhaps near a magical forest edge. The motivation should relate to something tied to the unicorn or the fog. Maybe they heard stories of a unicorn with a tainted mane that can corrupt others.\n" +
            "\n" +
            "Now, the quest structure. The task for the taker involves uncovering something related to the unicorn and facing its consequences. Perhaps they need to find artifacts from the unicorn's past or talk to local inhabitants who might know secrets about it.\n" +
            "\n" +
            "The endboss would be the tainted unicorn itself, but in a form that can't be easily defeated normally. Maybe it's cursed and has a curse symbol on it that needs to be broken before it can be killed. Alternatively, there could be a stronger minion with protective abilities once the players defeat the weaker ones.\n" +
            "\n" +
            "I should also consider some hidden items or optional quests. For example, the village might have magical artifacts like an amulet that shows glimpses of reality when you're close to the unicorn's tainted touch. Or there's a hidden coven in the forest that offers information but requires payment with rare ingredients.\n" +
            "\n" +
            "Putting it all together:\n" +
            "\n" +
            "- Quest Name: Fog of Deception\n" +
            "- Quest Giver: Name could be The Guardian, located near the village edge, motivation to protect the village from the unicorn's influence.\n" +
            "- Quest Task: Uncover the truth about the unicorn and face its consequences, like breaking a cursed symbol or recruiting allies.\n" +
            "- Endboss: The tainted unicorn with a curse symbol that needs to be destroyed. Maybe once defeated, there are weaker minions with protective shields.\n" +
            "\n" +
            "Wait, maybe I should adjust the task a bit more. Perhaps it's not just defeating the unicorn but also dealing with the spread of its corruption through the village. So the taker might need to find a cure or a way to counteract its influence on the villagers.\n" +
            "\n" +
            "Also, for hidden items: an amulet that shows distorted reality when near the unicorn's touch area and a coven in the forest offering information at a cost.\n" +
            "\n" +
            "I think this structure covers all parts without being too detailed. Now, I'll format it into JSON as per the instructions.\n" +
            "</think>\n" +
            "\n" +
            "```json\n" +
            "{\n" +
            "  \"quest_name\": \"Fog of Deception\",\n" +
            "  \"quest_giver\": {\n" +
            "    \"id\": \"The Guardian\",\n" +
            "    \"location\": \"Near the edge of the village, along the foggy outskirts.\",\n" +
            "    \"motivation\": \"To protect the village from the dark magic corruption tied to the unicorn.\"\n" +
            "  },\n" +
            "  \"quest\": {\n" +
            "    \"task\": \"Uncover the truth behind the unicorn's tainted existence and recruit allies to combat its influence across the village.\",\n" +
            "    \"endboss\": {\n" +
            "      \"id\": \"Tainted Unicorn\",\n" +
            "      \"final_boss_task\": \"Destroy its cursed symbol to break the spell on the village.\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"hidden_items\": [\n" +
            "    {\n" +
            "      \"item_name\": \"Reality Distortion Amulet\",\n" +
            "      \"description\": \"An amulet that reveals distorted glimpses of reality when in proximity to the unicorn's touch.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"item_name\": \"Corruption Coven Knowledge\",\n" +
            "      \"description\": \"Information about the coven and its rituals, obtainable by recruiting them with rare ingredients.\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"optional quests\": [\n" +
            "    {\n" +
            "      \"id\": \"Unraveling the Magic\",\n" +
            "      \"description\": \"A quest to find magical artifacts that can neutralize the unicorn's corruption.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"Dealing with Shadows\",\n" +
            "      \"description\": \"Exploring hidden coven members who offer insights but demand rare materials.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n" +
            "```";

    @Test
    void testTrim(){
        System.out.println(JsonTrimmer.trim(response));
    }

}