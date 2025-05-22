package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WinningAction {

    @SuppressWarnings("unused")
    @JsonProperty
    public String description;

    @SuppressWarnings("unused")
    @JsonProperty
    public String location;

    @SuppressWarnings("unused")
    @JsonProperty
    public String reason;

    @Override
    public String toString() {
        return "WinningAction{" +
                "description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}