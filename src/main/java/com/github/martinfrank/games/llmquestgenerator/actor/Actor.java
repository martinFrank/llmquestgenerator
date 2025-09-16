package com.github.martinfrank.games.llmquestgenerator.actor;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.actor.ActorDetails;
import com.github.martinfrank.games.llmquestgenerator.location.Location;

public class Actor {

    public final ActorType type;
    public final String id;
    public final String description;
    public final String locationId;


    public Actor(ActorType type, String id, String description, String locationId) {
        this.type = type;
        this.id = id;
        this.description = description;
        this.locationId = locationId;
    }

}
