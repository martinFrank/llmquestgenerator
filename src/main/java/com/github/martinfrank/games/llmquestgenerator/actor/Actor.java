package com.github.martinfrank.games.llmquestgenerator.actor;

public class Actor {

    public final ActorType type;
    public final String id;
    public final String locationId;

    public Actor(ActorType type, String id, String locationId) {
        this.type = type;
        this.id = id;
        this.locationId = locationId;
    }
}
