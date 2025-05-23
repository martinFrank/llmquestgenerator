package com.github.martinfrank.games.llmquestgenerator.person.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

    @SuppressWarnings("unused")
    @JsonProperty
    public String name;

    @SuppressWarnings("unused")
    @JsonProperty
    public String appearance_short;

    @SuppressWarnings("unused")
    @JsonProperty
    public String appearance;

    @SuppressWarnings("unused")
    @JsonProperty
    public String dalle_prompt;
}
