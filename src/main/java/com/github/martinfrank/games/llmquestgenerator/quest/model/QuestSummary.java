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
    public Quest quest;

    @Override
    public String toString() {
        return "QuestSummary{" +
                "quest_name='" + quest_name + '\'' +
                ", quest_giver=" + quest_giver +
                ", quest=" + quest +
                '}';
    }
}
