package com.github.martinfrank.games.llmquestgenerator.location;

import com.github.martinfrank.games.llmquestgenerator.graph.Graph;
import com.github.martinfrank.games.llmquestgenerator.graph.GraphGenerator;
import com.github.martinfrank.games.llmquestgenerator.graph.Node;
import com.github.martinfrank.games.llmquestgenerator.graph.NodeType;
import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
import com.github.martinfrank.games.llmquestgenerator.location.model.QuestLocation;
import com.github.martinfrank.games.llmquestgenerator.location.model.QuestLocations;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        QuestLocation location = locations.locations.get((int) (Math.random() * locations.locations.size()));
        String lastJson = JsonMapper.toJson(location);

        String locationDetails = locationChatbotService.createLocationDetails(location.description, lastJson);
        System.out.println("-------summary-------");
        System.out.println(locationDetails);
//        QuestSummary questSummary = JsonMapper.fromJson(questSummaryJson, QuestSummary.class);

    }

    public String generateQuestLocations(String questSummaryJson) {
        //create locations to
        JsonObject jsonObject = JsonMapper.fromJson(questSummaryJson, JsonObject.class);
        jsonObject.remove("start_location");
        String summaryWithoutStart = JsonMapper.toJson(jsonObject);
        System.out.println("----mini summary---");
        System.out.println(summaryWithoutStart);
        String questLocationJson = locationChatbotService.createQuestLocations(summaryWithoutStart);
        System.out.println("-------questLocationJson-------");
        System.out.println(questLocationJson);

        jsonObject.remove("sub_tasks");
        String summaryWithoutSubtasks = JsonMapper.toJson(jsonObject);
        String sideLocationJson = locationChatbotService.createSideLocations(summaryWithoutSubtasks);
        System.out.println("-------sideLocationJson-------");
        System.out.println(sideLocationJson);

        Graph graph = new GraphGenerator().generate();
        String graphJson = JsonMapper.toJson(graph);
        QuestLocations locations = createQuestLocations(graphJson, questLocationJson, sideLocationJson);
        return JsonMapper.toJson(locations);
////        System.out.println("-------locations object-------");
////        System.out.println(questLocations);
//
//        System.out.println("-------locations repaired-------");
//        System.out.println(JsonMapper.toJson(questLocations));
//        System.out.println("number of locations = "+questLocations.locations.size());

    }

    public static QuestLocations createQuestLocations(String graphJson, String questLocationJson, String sideLocationJson) {
        Graph graph = JsonMapper.fromJson(graphJson, Graph.class);
        QuestLocations questLocations = JsonMapper.fromJson(questLocationJson, QuestLocations.class);
        QuestLocations sideLocations = JsonMapper.fromJson(sideLocationJson, QuestLocations.class);

        QuestLocations result = new QuestLocations();
        result.locations = new ArrayList<>();
        List<Node> reverse = new ArrayList<>(graph.locations());
        Collections.reverse(reverse);
        for (Node node : reverse) {
            if (node.type() == NodeType.TOWN_CENTER) {
                result.locations.add(createTownCenter(node));
                System.out.println("adding town center @"+node.id());
                continue;
            }
            QuestLocation fromQuest = findMatchingLocation(node, questLocations);
            if (fromQuest != null) {
                questLocations.locations.remove(fromQuest);
                System.out.println("adding quest location "+fromQuest.name+" @"+node.id());
                result.locations.add(createQuestLocation(node, fromQuest));
            } else {
                QuestLocation fromSide = findMatchingLocation(node, sideLocations);
                if (fromSide != null) {
                    sideLocations.locations.remove(fromSide);
                    System.out.println("adding side location "+fromSide.name+" @"+node.id());
                    result.locations.add(createQuestLocation(node, fromSide));
                }
            }
        }

        List<String> existingIds = result.locations.stream().map(l -> l.id).toList();
        for(QuestLocation from: result.locations) {
            List<String> removals = new ArrayList<>();
            for (String toId : from.connectedIds){
                if (!existingIds.contains(toId)){
                    removals.add(toId);
                }
            }
            from.connectedIds.removeAll(removals);
        }

        return result;
    }

    private static QuestLocation findMatchingLocation(Node node, QuestLocations questLocations) {
        for (QuestLocation location : questLocations.locations) {
            //location.location_type: TOWN, DUNGEON, WILDERNESS
            //node.type: TOWN_CENTER, PATH, QUEST_JUNCTION, TOWN_ADDON, QUEST_AREA
            boolean isNodePath = node.type() == NodeType.PATH || node.type() == NodeType.QUEST_JUNCTION;
            boolean isQuestPath = location.location_type.equals("WILDERNESS");
            if (isNodePath && isQuestPath) {
                return location;
            }

            //location.location_type: TOWN, DUNGEON, WILDERNESS
            //node.type: TOWN_CENTER, PATH, QUEST_JUNCTION, TOWN_ADDON, QUEST_AREA
            boolean isNodeTown = node.type() == NodeType.TOWN_ADDON;
            boolean isQuestTown = location.location_type.equals("TOWN");
            if (isNodeTown && isQuestTown) {
                return location;
            }

            //location.location_type: TOWN, DUNGEON, WILDERNESS
            //node.type: TOWN_CENTER, PATH, QUEST_JUNCTION, TOWN_ADDON, QUEST_AREA
            boolean isNodeDungeon = node.type() == NodeType.QUEST_AREA;
            boolean isQuestDungeon = location.location_type.equals("DUNGEON");
            if (isNodeDungeon && isQuestDungeon) {
                return location;
            }
        }
        return null;
    }

    private static void removeNode(Node node, QuestLocations result) {
        //FIXME
        for (QuestLocation location : result.locations) {
            location.connectedIds.remove(node.id());
        }
        System.out.println("remove me! " + node);
    }

    private static QuestLocation createQuestLocation(Node node, QuestLocation location) {
        QuestLocation questLocation = createQuestLocation(node);
        questLocation.name = location.name;
        questLocation.description = "In the heart of the town stands a stone fountain surrounded by town buildings";
        questLocation.location_type = node.type().name();
        return questLocation;
    }


    private static QuestLocation createTownCenter(Node node) {
        QuestLocation townCenter = createQuestLocation(node);
        townCenter.name = "Town center";
        townCenter.description = "In the heart of the town stands a stone fountain surrounded by town buildings";
        townCenter.location_type = node.type().name();
        return townCenter;
    }

    private static QuestLocation createQuestLocation(Node node) {
        QuestLocation location = new QuestLocation();
        location.id = node.id();
        location.type = node.type().name();
        location.connectedIds = node.connectedIds();
        return location;
    }

    //hinweis: gesamt aufnahme: wide shot of a full body figure:
}
