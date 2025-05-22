package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestGiver {

    @SuppressWarnings("unused")
    @JsonProperty
    public String name;

    @SuppressWarnings("unused")
    @JsonProperty
    public String location;

    @SuppressWarnings("unused")
    @JsonProperty
    public String motivation;

    @Override
    public String toString() {
        return "QuestGiver{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", motivation='" + motivation + '\'' +
                '}';
    }
}
