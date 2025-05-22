package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quest {

    @SuppressWarnings("unused")
    @JsonProperty
    public String task;

    @SuppressWarnings("unused")
    @JsonProperty
    public String endboss;

    @Override
    public String toString() {
        return "QuestTask{" +
                "task='" + task + '\'' +
                ", endboss='" + endboss + '\'' +
                '}';
    }
}
