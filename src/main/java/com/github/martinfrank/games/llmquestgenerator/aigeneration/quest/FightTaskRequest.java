package com.github.martinfrank.games.llmquestgenerator.aigeneration.quest;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;

public record FightTaskRequest(String quest, String location, Actor enemy) {

}
