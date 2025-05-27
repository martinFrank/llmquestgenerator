package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestTaskClue {

    @SuppressWarnings("unused")
    @JsonProperty
    public String hint;

    @SuppressWarnings("unused")
    @JsonProperty
    public String reveal_type;

    @SuppressWarnings("unused")
    @JsonProperty
    public String source;

    @SuppressWarnings("unused")
    @JsonProperty
    public String location;

}