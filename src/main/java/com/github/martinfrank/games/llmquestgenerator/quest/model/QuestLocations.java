package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestLocations {

    @SuppressWarnings("unused")
    @JsonProperty
    public List<QuestLocation> locations;

    public void repairConnections() {
        for (QuestLocation from : locations) {
            for (String to : from.connections) {
                QuestLocation toLocation = findLocation(to);
                if (toLocation != null) {
                    if (!toLocation.connections.contains(from.name)) {
                        toLocation.connections.add(from.name);
                    }
                } else {
                    System.out.println("warning! could not find to-location: " + to);
                }
            }
        }
        List<QuestLocation> unreachable = locations.stream().filter(l -> l.connections.isEmpty()).toList();
        locations.removeAll(unreachable);
    }

    private QuestLocation findLocation(String to) {
        return locations.stream().filter(l -> l.name.equals(to)).findFirst().orElse(null);
    }
}
