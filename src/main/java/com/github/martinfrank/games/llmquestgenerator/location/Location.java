package com.github.martinfrank.games.llmquestgenerator.location;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.GeneratedLocationDetails;

import java.util.ArrayList;
import java.util.List;

public class Location {

    public final LocationType type;
    public final String id;
    public final String descriptionPrompt;
    public final List<String> toLocationIds = new ArrayList<>();

    private GeneratedLocationDetails details;


    public Location(LocationType locationType, String id, String descriptionPrompt) {
        this.type = locationType;
        this.id = id;
        this.descriptionPrompt = descriptionPrompt;
    }

    public void addTo(String toId) {
        toLocationIds.add(toId);
    }

    @Override
    public String toString() {
        return "Location{" +
                ", id='" + id + '\'' +
                "type=" + type +
                ", toLocations=" + toLocationIds +
                '}';
    }

    public GeneratedLocationDetails getDetails() {
        return details;
    }

    public void setDetails(GeneratedLocationDetails details) {
        this.details = details;
    }
}
