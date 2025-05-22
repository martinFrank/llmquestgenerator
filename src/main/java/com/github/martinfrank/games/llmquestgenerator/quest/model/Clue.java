package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clue {

    @SuppressWarnings("unused")
    @JsonProperty
    public String content;

    @SuppressWarnings("unused")
    @JsonProperty
    public String hinted_fact;

    @SuppressWarnings("unused")
    @JsonProperty
    public String source;

    @SuppressWarnings("unused")
    @JsonProperty
    public String location;

    @Override
    public String toString() {
        return "Clue{" +
                "content='" + content + '\'' +
                ", hinted_fact='" + hinted_fact + '\'' +
                ", source='" + source + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}