package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestTask {

    @SuppressWarnings("unused")
    @JsonProperty
    public String subtask_description;

    @SuppressWarnings("unused")
    @JsonProperty
    public String subtask_location;

    @SuppressWarnings("unused")
    @JsonProperty
    public String subtask_object;

    @SuppressWarnings("unused")
    @JsonProperty
    public String subtask_person;

    @SuppressWarnings("unused")
    @JsonProperty
    public String subtask_type;

    @SuppressWarnings("unused")
    @JsonProperty
    public QuestTaskClue clue;
}
