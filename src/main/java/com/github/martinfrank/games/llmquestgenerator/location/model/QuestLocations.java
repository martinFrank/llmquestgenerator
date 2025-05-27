package com.github.martinfrank.games.llmquestgenerator.location.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class QuestLocations {

    @SuppressWarnings("unused")
    @JsonProperty
    public List<QuestLocation> locations;

    private QuestLocation findLocation(String to) {
        return locations.stream().filter(l -> l.name.equals(to)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "QuestLocations{" +
                "locations=" + locations +
                '}';
    }

    public void repairConnections() {
        List<String> locationIds = new ArrayList<>();
        for (QuestLocation location : locations) {
            locationIds.add(location.id);
        }
        for (QuestLocation location : locations) {
            List<String> connectedIds = location.connectedIds.stream().filter(locationIds::contains).toList();
            location.connectedIds = new ArrayList<>(connectedIds);
        }
    }
}
