package com.github.martinfrank.games.llmquestgenerator.aigeneration.actor;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationRequestEntry;

import java.util.List;

public record ActorGenerationRequest(List<ActorRequestEntry> history, String actorDescription, String actorLocationDescription) {

}
