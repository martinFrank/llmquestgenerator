package com.github.martinfrank.games.llmquestgenerator.location;

import com.github.martinfrank.games.llmquestgenerator.graph.Graph;
import com.github.martinfrank.games.llmquestgenerator.graph.GraphGenerator;
import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
import com.github.martinfrank.games.llmquestgenerator.location.model.QuestLocations;
import org.junit.jupiter.api.Test;

class LocationGeneratorTest {

    private static final String SIDE_LOCATIONS = """
            {
              "locations": [
                {
                  "name": "Silverstream Village",
                  "description": "A small, weathered village nestled beside the Silverstream River, perpetually shrouded in mist. The villagers are fearful and withdrawn, their faces etched with worry.",
                  "location_type": "TOWN"
                },
                {
                  "name": "The Crooked Hearth Inn",
                  "description": "A dimly lit tavern, smelling of stale ale and woodsmoke. Locals whisper of strange occurrences and unsettling visions. The innkeeper, Silas, reluctantly provides information for a hefty price.",
                  "location_type": "TOWN"
                },
                {
                  "name": "Ruined Shrine of Silvanus",
                  "description": "An overgrown shrine dedicated to Silvanus, the ancient forest spirit. The stone is cracked and covered in moss, and a palpable sense of unease hangs in the air.  A faint, mournful humming can be heard.",
                  "location_type": "TOWN"
                },
                {
                  "name": "The Sunken Crypt",
                  "description": "A crumbling crypt located deep within the Whispering Woods, partially submerged in a stagnant pool.  The air is thick with the smell of decay and something…ancient.",
                  "location_type": "DUNGEON"
                },
                {
                  "name": "The Spider's Lair",
                  "description": "A vast cavern riddled with webs and guarded by giant spiders.  The walls are slick with moisture, and the only light comes from bioluminescent fungi.",
                  "location_type": "DUNGEON"
                },
                {
                  "name": "The Obsidian Grotto",
                  "description": "A hidden grotto containing a pool of black, viscous water.  Strange symbols are carved into the walls, and a sense of oppressive dread permeates the area.",
                  "location_type": "DUNGEON"
                },
                {
                  "name": "Blackthorn Pass",
                  "description": "A treacherous mountain pass known for its dense thorn bushes and unpredictable weather.  The wind howls through the peaks, carrying unsettling whispers.",
                  "location_type": "WILDERNESS"
                },
                {
                  "name": "The Howling Falls",
                  "description": "A cascading waterfall surrounded by jagged cliffs. The sound of the water is punctuated by unsettling whispers carried on the wind.",
                  "location_type": "WILDERNESS"
                },
                {
                  "name": "The Murmuring Mire",
                  "description": "A vast, stagnant swamp filled with strange, glowing plants and unsettling noises. The ground is soft and treacherous beneath your feet.",
                  "location_type": "WILDERNESS"
                },
                {
                  "name": "Echoing Cave",
                  "description": "A cavern where the echoes of the forest’s past linger.  Illusory visions flicker in the darkness, and the air feels heavy with forgotten memories.",
                  "location_type": "WILDERNESS"
                },
                {
                  "name": "The Petrified Grove",
                  "description": "A grove of ancient trees, frozen in time by a magical event. The bark is smooth and grey, and a sense of melancholy hangs in the air. ",
                  "location_type": "WILDERNESS"
                },
                {
                  "name": "The Forgotten Bastion",
                  "description": "An ancient, crumbling fortress built into the side of a cliff.  It's partially collapsed and overrun with vegetation, hinting at a long-lost civilization.",
                  "location_type": "DUNGEON"
                },
                {
                  "name": "The Root Maze",
                  "description": "A labyrinthine network of roots and tunnels beneath the forest floor. The air is damp and claustrophobic, and the path ahead is often obscured by shadows.",
                  "location_type": "WILDERNESS"
                },
                 {
                  "name": "The Silent Well",
                  "description": "An ancient well, eternally filled with still water. It reflects not the sky, but a distorted image of the forest, and whispers seem to emanate from its depths.",
                  "location_type": "TOWN"
                }
              ]
            }
            """;

    private static final String QUEST_LOCATIONS = """
            {
              "locations": [
                {
                  "name": "Forgotten Ruins",
                  "description": "Weather-beaten stone structures, choked with moss and vines, hinting at a forgotten civilization that once revered the forest. Strange symbols are etched into the crumbling walls.",
                  "location_type": "DUNGEON"
                },
                {
                  "name": "Spirit Grove",
                  "description": "A clearing bathed in an ethereal green light, dominated by a colossal, ancient tree. The air hums with a palpable energy, and the whispers of the forest are most pronounced here.",
                  "location_type": "WILDERNESS"
                },
                {
                  "name": "Ancient Tree Sanctuary",
                  "description": "A secluded haven within the heart of the oldest tree. Sunlight filters through the leaves, illuminating a circular space filled with intricate carvings and a sense of profound peace. A palpable aura of ancient power surrounds the area.",
                  "location_type": "WILDERNESS"
                },
                {
                  "name": "Library",
                  "description": "A small, dusty chamber filled with scrolls and tomes, meticulously organized by the Village Historian. The scent of aged paper and leather hangs heavy in the air.",
                  "location_type": "TOWN"
                },
                {
                  "name": "Tavern",
                  "description": "A bustling hub of local gossip and weary travelers. The air is thick with the smell of ale and roasted meat. Villagers gather to share stories and seek news.",
                  "location_type": "TOWN"
                },
                {
                  "name": "Forest Path",
                  "description": "A winding trail that cuts through the dense undergrowth, leading deeper into the heart of the Whispering Woods. The trees here seem to watch, and the air is filled with an unsettling silence.",
                  "location_type": "WILDERNESS"
                }
              ]
            }
            """;

    private final String GRAPH = """
            {
              "locations": [
                {
                  "id": "AA",
                  "type": "TOWN_CENTER",
                  "connectedIds": [
                    "AB",
                    "AF",
                    "AG",
                    "AH",
                    "AI",
                    "AJ"
                  ]
                },
                {
                  "id": "AB",
                  "type": "PATH",
                  "connectedIds": [
                    "AA",
                    "AC"
                  ]
                },
                {
                  "id": "AC",
                  "type": "QUEST_JUNCTION",
                  "connectedIds": [
                    "AB",
                    "AD",
                    "AK",
                    "AL",
                    "AM",
                    "AN",
                    "AO"
                  ]
                },
                {
                  "id": "AD",
                  "type": "PATH",
                  "connectedIds": [
                    "AC",
                    "AE"
                  ]
                },
                {
                  "id": "AE",
                  "type": "QUEST_JUNCTION",
                  "connectedIds": [
                    "AD",
                    "AP",
                    "AQ",
                    "AR",
                    "AS",
                    "AT",
                    "AU"
                  ]
                },
                {
                  "id": "AF",
                  "type": "TOWN_ADDON",
                  "connectedIds": [
                    "AA"
                  ]
                },
                {
                  "id": "AG",
                  "type": "TOWN_ADDON",
                  "connectedIds": [
                    "AA"
                  ]
                },
                {
                  "id": "AH",
                  "type": "TOWN_ADDON",
                  "connectedIds": [
                    "AA"
                  ]
                },
                {
                  "id": "AI",
                  "type": "TOWN_ADDON",
                  "connectedIds": [
                    "AA"
                  ]
                },
                {
                  "id": "AJ",
                  "type": "TOWN_ADDON",
                  "connectedIds": [
                    "AA"
                  ]
                },
                {
                  "id": "AK",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AC"
                  ]
                },
                {
                  "id": "AL",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AC"
                  ]
                },
                {
                  "id": "AM",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AC"
                  ]
                },
                {
                  "id": "AN",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AC"
                  ]
                },
                {
                  "id": "AO",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AC"
                  ]
                },
                {
                  "id": "AP",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AE"
                  ]
                },
                {
                  "id": "AQ",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AE"
                  ]
                },
                {
                  "id": "AR",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AE"
                  ]
                },
                {
                  "id": "AS",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AE"
                  ]
                },
                {
                  "id": "AT",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AE"
                  ]
                },
                {
                  "id": "AU",
                  "type": "QUEST_AREA",
                  "connectedIds": [
                    "AE"
                  ]
                }
              ]
            }
            """;

    @Test
    void testQuestLocationGeneration(){
        QuestLocations questLocations = LocationGenerator.createQuestLocations(GRAPH, QUEST_LOCATIONS, SIDE_LOCATIONS);
        System.out.println(JsonMapper.toJson(questLocations));
    }
}