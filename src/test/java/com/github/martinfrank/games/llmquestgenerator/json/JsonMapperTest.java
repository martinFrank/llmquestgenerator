package com.github.martinfrank.games.llmquestgenerator.json;

import com.github.martinfrank.games.llmquestgenerator.quest.model.QuestLocations;
import org.junit.jupiter.api.Test;

class JsonMapperTest {

    private static final String LOCATIONS_JSON = """
            { \"locations\": [
               {\"name\": \"Oakhaven Village\",
                \"description\": \"A peaceful village nestled at the edge of the Whispering Woods.\",
                \"connections\": [\"Willow Grove\", \"Old Oak Trail\"]
                },
                {\"name\": \"Willow Grove\",
                 \"description\": \"A secluded grove of ancient willow trees, said to whisper secrets on the wind.\",
                 \"connections\": [\"Oakhaven Village\", \"Whispering Woods\"]
                },
                {\"name\": \"Old Oak Trail\",
                 \"description\": \"A winding path through the forest, lined with towering oaks and shrouded in mist.\",
                 \"connections\": [\"Oakhaven Village\", \"Forgotten Crypt\", \"Aethelred's Tomb\"]
                },
                {\"name\": \"Whispering Woods\",
                 \"description\": \"A vast and ancient forest, home to both beauty and danger.\",
                 \"connections\": [\"Willow Grove\", \"Old Oak Trail\", \"Hidden Glade\"]
                },
                {\"name\": \"Hidden Glade\",
                 \"description\": \"A secluded clearing deep within the Whispering Woods, rumored to be a place of magic.\",
                 \"connections\": [\"Whispering Woods\", \"Forgotten Crypt\"]
                },
                {\"name\": \"Forgotten Crypt\",
                 \"description\": \"An ancient burial ground, overgrown with vines and guarded by spectral wolves.\",
                 \"connections\": [\"Old Oak Trail\", \"Hidden Glade\"]
                },
                {\"name\": \"Aethelred's Tomb\",
                 \"description\": \"A weathered tomb hidden within a grove of ancient oaks, said to be the final resting place of Aethelred.\",
                 \"connections\": [\"Old Oak Trail\"]
                },
                {\"name\": \"Haunted Barrow\",
                 \"description\": \"An ominous barrow mound, rumored to be haunted by restless spirits.\",
                 \"connections\": [\"Whispering Woods\"]
                },
                {\"name\": \"Silverstream Falls\",
                 \"description\": \"A waterfall cascading into a crystal-clear pool, said to possess healing properties.\",
                 \"connections\": [\"Hidden Glade\"]
                },
                {\"name\": \"Sunstone Summit\",
                 \"description\": \"A mountain peak bathed in sunlight, offering breathtaking views of the surrounding landscape.\",
                 \"connections\": [\"Whispering Woods\"]
                }
            ]}
            """;
//{ "locations": [   {"name": "Oakhaven Village",    "description": "A peaceful village nestled at the edge of the Whispering Woods.",    "connections": ["Willow Grove", "Old Oak Trail"]    },    {"name": "Willow Grove",     "description": "A secluded grove of ancient willow trees, said to whisper secrets on the wind.",     "connections": ["Oakhaven Village", "Whispering Woods"]    },    {"name": "Old Oak Trail",     "description": "A winding path through the forest, lined with towering oaks and shrouded in mist.",     "connections": ["Oakhaven Village", "Forgotten Crypt", "Aethelred's Tomb"]    },    {"name": "Whispering Woods",     "description": "A vast and ancient forest, home to both beauty and danger.",     "connections": ["Willow Grove", "Old Oak Trail", "Hidden Glade"]    },    {"name": "Hidden Glade",     "description": "A secluded clearing deep within the Whispering Woods, rumored to be a place of magic.",     "connections": ["Whispering Woods", "Forgotten Crypt"]    },    {"name": "Forgotten Crypt",     "description": "An ancient burial ground, overgrown with vines and guarded by spectral wolves.",     "connections": ["Old Oak Trail", "Hidden Glade"]    },    {"name": "Aethelred's Tomb",     "description": "A weathered tomb hidden within a grove of ancient oaks, said to be the final resting place of Aethelred.",     "connections": ["Old Oak Trail"]    },    {"name": "Haunted Barrow",     "description": "An ominous barrow mound, rumored to be haunted by restless spirits.",     "connections": ["Whispering Woods"]    },    {"name": "Silverstream Falls",     "description": "A waterfall cascading into a crystal-clear pool, said to possess healing properties.",     "connections": ["Hidden Glade"]    },    {"name": "Sunstone Summit",     "description": "A mountain peak bathed in sunlight, offering breathtaking views of the surrounding landscape.",     "connections": ["Whispering Woods"]    }]}"
    @Test
    void testMapping(){
        QuestLocations questLocations = JsonMapper.fromJson(LOCATIONS_JSON, QuestLocations.class);
    }
}