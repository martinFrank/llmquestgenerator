package com.github.martinfrank.games.llmquestgenerator.location;

import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
import com.github.martinfrank.games.llmquestgenerator.location.model.QuestLocation;
import com.github.martinfrank.games.llmquestgenerator.location.model.QuestLocations;
import com.google.gson.JsonObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationGenerator {


    private final LocationChatbotService locationChatbotService;

    @Autowired
    public LocationGenerator(LocationChatbotService locationChatbotService) {
        this.locationChatbotService = locationChatbotService;
    }

    private static final String LOCATIONS = """
            {
                 "locations": [
                   {
                     "name": "Village Elder's house",
                     "description": "A humble abode where Elder Elara resides, offering wisdom and guidance.",
                     "connections": [
                       "Path leading out of the village",
                       "Market Square"
                     ]
                   },
                   {
                     "name": "Market Square",
                     "description": "Bustling with activity, traders offer goods and villagers share news.",
                     "connections": [
                       "Village Elder's house",
                       "The Blacksmith's Forge",
                       "Path to the Woods"
                     ]
                   },
                   {
                     "name": "The Blacksmith's Forge",
                     "description": "Where the rhythmic clang of hammer against metal fills the air, forging tools and weapons.",
                     "connections": [
                       "Market Square",
                       "Ruined Shrine at the edge of the woods"
                     ]
                   },
                   {
                     "name": "Path leading out of the village",
                     "description": "A well-worn path leading into the dense forest beyond the village.",
                     "connections": [
                       "Village Elder's house",
                       "Path to the Woods"
                     ]
                   },
                   {
                     "name": "Path to the Woods",
                     "description": "A winding trail shrouded in dappled sunlight, leading deeper into the heart of the woods.",
                     "connections": [
                       "Market Square",
                       "Path leading deeper into the woods",
                       "Ruined Shrine at the edge of the woods",
                       "Path leading out of the village"
                     ]
                   },
                   {
                     "name": "Ruined Shrine at the edge of the woods",
                     "description": "A crumbling testament to ancient worship, overgrown with vines and shrouded in mystery.",
                     "connections": [
                       "Path leading deeper into the woods",
                       "The Blacksmith's Forge",
                       "Path to the Woods"
                     ]
                   },
                   {
                     "name": "Path leading deeper into the woods",
                     "description": "A less traveled path, winding through dense undergrowth and towering trees.",
                     "connections": [
                       "Path to the Woods",
                       "Ancient Grove",
                       "Heart of the Ancient Grove",
                       "Ruined Shrine at the edge of the woods",
                       "Haunted Woods"
                     ]
                   },
                   {
                     "name": "Haunted Woods",
                     "description": "A shadowy expanse where whispers echo through the trees and strange creatures roam.",
                     "connections": [
                       "Path leading deeper into the woods"
                     ]
                   },
                   {
                     "name": "Ancient Grove",
                     "description": "A clearing bathed in ethereal light, where ancient trees stand tall and spirits dwell.",
                     "connections": [
                       "Path leading deeper into the woods",
                       "Heart of the Ancient Grove"
                     ]
                   },
                   {
                     "name": "Heart of the Ancient Grove",
                     "description": "The mystical core of the grove, a place of power and ancient secrets.",
                     "connections": [
                       "Ancient Grove",
                       "Path leading deeper into the woods"
                     ]
                   }
                 ]
               }
            """;

    public void generateDetails(String locationDescription) {
        //create the top level quest structure
        QuestLocations locations = JsonMapper.fromJson(LOCATIONS, QuestLocations.class);

        QuestLocation location = locations.locations.get((int)(Math.random() * locations.locations.size()));
        String lastJson = JsonMapper.toJson(location);

        String locationDetails = locationChatbotService.createLocationDetails(location.description, lastJson);
        System.out.println("-------summary-------");
        System.out.println(locationDetails);
//        QuestSummary questSummary = JsonMapper.fromJson(questSummaryJson, QuestSummary.class);

    }

    public void generateQuestLocations(String questSummaryJson) {
        //create locations to
        JsonObject jsonObject = JsonMapper.fromJson(questSummaryJson, JsonObject.class);
        jsonObject.remove("start_location");
        String summaryWithoutStart = JsonMapper.toJson(jsonObject);
        System.out.println("----mini summary---");
        System.out.println(summaryWithoutStart);
        String questLocationJson = locationChatbotService.createQuestLocations(summaryWithoutStart);
        System.out.println("-------locations-------");
        System.out.println(questLocationJson);

        jsonObject.remove("sub_tasks");
        String summaryWithoutSubtasks = JsonMapper.toJson(jsonObject);
        String sideLocationJson = locationChatbotService.createSideLocations(summaryWithoutSubtasks);
        System.out.println("-------locations-------");
        System.out.println(sideLocationJson);






////        System.out.println("-------locations object-------");
////        System.out.println(questLocations);
//
//        System.out.println("-------locations repaired-------");
//        System.out.println(JsonMapper.toJson(questLocations));
//        System.out.println("number of locations = "+questLocations.locations.size());

    }

    //hinweis: gesamt aufnahme: wide shot of a full body figure:

}
