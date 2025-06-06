package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestSummary {

    @SuppressWarnings("unused")
    @JsonProperty
    public String quest_name;

    @SuppressWarnings("unused")
    @JsonProperty
    public String quest_giver;

    @SuppressWarnings("unused")
    @JsonProperty
    public String start_location;

    @SuppressWarnings("unused")
    @JsonProperty
    public String quest_summary;

    @SuppressWarnings("unused")
    @JsonProperty
    public String quest_details;

    @SuppressWarnings("unused")
    @JsonProperty
    public List<QuestTask> sub_tasks;

    @SuppressWarnings("unused")
    @JsonProperty
    public List<QuestTaskClue> clues;

    @SuppressWarnings("unused")
    @JsonProperty
    public String endboss;

}
