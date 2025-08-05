package com.github.martinfrank.games.llmquestgenerator.location;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationDetails;

import java.util.ArrayList;
import java.util.List;

public class Location {

    public final LocationType type;
    public final String id;
    public final List<String> toLocationIds = new ArrayList<>();

    private LocationDetails details;


    private Location(LocationType locationType, String id) {
        this.type = locationType;
        this.id = id;
    }

    public void addTo(String toId) {
        toLocationIds.add(toId);
    }

    @Override
    public String toString() {
        return "Location{" +
                ", id='" + id + '\'' +
                "type=" + type;
    }

    public LocationDetails getDetails() {
        return details;
    }

    public void setDetails(LocationDetails details) {
        this.details = details;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private LocationType type;
        private String id;
        private final List<Location> destinations = new ArrayList<>();

        public Builder type(LocationType type){
            this.type = type;
            return this;
        }
        public Builder id(String id){
            this.id = id;
            return this;
        }
        public Builder connect(Location destination){
            destinations.add(destination);
            return this;
        }

        public Location build(){
            Location location = new Location(type, id);
            for(Location destinations : destinations){
                connectLocations(location, destinations);
            }
            return location;
        }

        private static void connectLocations(Location a, Location b) {
            a.addTo(b.id);
            b.addTo(a.id);
        }
    }
}
