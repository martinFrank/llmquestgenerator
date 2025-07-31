package com.github.martinfrank.games.llmquestgenerator.location;

import java.util.List;

public class LocationFactory {

    private static void connectLocations(Location a, Location b) {
        a.addTo(b.id);
        b.addTo(a.id);
    }

    public static Location addLocation(LocationType type, String id, String generationPrompt, Location fromLocation, List<Location> result) {
        Location newLocation = new Location(type, id, generationPrompt);
        connectLocations(fromLocation, newLocation);
        result.add(newLocation);

        return newLocation;
    }

    public static Location createLocation(LocationType type, String id, String generationPrompt, List<Location> result) {
        Location location = new Location(type, id, generationPrompt);
        result.add(location);

        return location;
    }

}
