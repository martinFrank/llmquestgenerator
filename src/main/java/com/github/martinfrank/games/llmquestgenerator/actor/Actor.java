package com.github.martinfrank.games.llmquestgenerator.actor;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.actor.ActorDetails;
import com.github.martinfrank.games.llmquestgenerator.location.Location;

public class Actor {

    public final ActorType type;
    public final String id;
    public final String locationId;
    private ActorDetails details;

    private Actor(ActorType type, String id, String locationId) {
        this.type = type;
        this.id = id;
        this.locationId = locationId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setDetails(ActorDetails details) {
        this.details = details;
    }

    public ActorDetails getDetails() {
        return details;
    }

    public static class Builder {
        private ActorType type;
        private String id;
        private String locationId;

        public Builder type(ActorType actorType) {
            this.type = actorType;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder location(Location location) {
            this.locationId = location.id;
            return this;
        }

        public Actor build() {
            return new Actor(type, id, locationId);
        }
    }
}
