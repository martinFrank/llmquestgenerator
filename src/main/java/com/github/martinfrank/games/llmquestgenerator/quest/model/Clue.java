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

}