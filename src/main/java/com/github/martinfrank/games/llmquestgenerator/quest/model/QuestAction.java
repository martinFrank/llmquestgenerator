package com.github.martinfrank.games.llmquestgenerator.quest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestAction {

    @SuppressWarnings("unused")
    @JsonProperty
    public WinningAction winning_action; //json conform name

    @SuppressWarnings("unused")
    @JsonProperty
    public List<Clue> clues;


}
