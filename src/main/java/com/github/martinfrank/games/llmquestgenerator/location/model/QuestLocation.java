package com.github.martinfrank.games.llmquestgenerator.location.model;

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
    public String location_type;

    @SuppressWarnings("unused")
    @JsonProperty
    public String id;

    @SuppressWarnings("unused")
    @JsonProperty
    public String type;

    @SuppressWarnings("unused")
    @JsonProperty
    public List<String> connectedIds;

    @Override
    public String toString() {
        return "QuestLocation{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location_type='" + location_type + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", connectedIds=" + connectedIds +
                '}';
    }
}
