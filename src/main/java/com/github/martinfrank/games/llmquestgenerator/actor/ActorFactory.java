package com.github.martinfrank.games.llmquestgenerator.actor;

import java.util.List;

public class ActorFactory {

    public static Actor addPerson(ActorType actorType, String actorId, String locationId, List<Actor> actors) {
        Actor actor = new Actor(actorType, actorId, locationId);
        actors.add(actor);

        return actor;
    }
}
