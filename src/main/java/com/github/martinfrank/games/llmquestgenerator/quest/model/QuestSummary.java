package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestSummary {

    @SuppressWarnings("unused")
    @JsonProperty
    public String quest_name;

    @SuppressWarnings("unused")
    @JsonProperty
    public QuestGiver quest_giver;

    @SuppressWarnings("unused")
    @JsonProperty
    public QuestTask quest;
}
