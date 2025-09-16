package com.github.martinfrank.games.llmquestgenerator.location;

import java.util.HashSet;
import java.util.Set;

public class Location {

    public final LocationType type;
    public final String id;
    public final String generation;
    public final Set<String> toLocationIds = new HashSet<>();

    public Location(LocationType locationType, String id, String generation) {
        this.type = locationType;
        this.id = id;
        this.generation = generation;
    }

    public void connect(Location location) {
        toLocationIds.add(location.id);
        location.toLocationIds.add(this.id);
    }

    @Override
    public String toString() {
        return "Location{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", generation='" + generation + '\'' +
                ", toLocationIds=" + toLocationIds +
                '}';
    }

}
