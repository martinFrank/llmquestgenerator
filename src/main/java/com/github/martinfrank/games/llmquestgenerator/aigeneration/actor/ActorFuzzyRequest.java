package com.github.martinfrank.games.llmquestgenerator.aigeneration.actor;

public record ActorFuzzyRequest(
        String actorType,
        String actorId,
        String location
) {

}
