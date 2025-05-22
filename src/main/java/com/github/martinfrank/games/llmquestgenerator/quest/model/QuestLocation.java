package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestLocation {

    @SuppressWarnings("unused")
    @JsonProperty
    public String name;

    @SuppressWarnings("unused")
    @JsonProperty
    public String description;

    @SuppressWarnings("unused")
    @JsonProperty
    public List<String> connections;

    @Override
    public String toString() {
        return "QuestLocation{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", connections=" + connections +
                '}';
    }
}
