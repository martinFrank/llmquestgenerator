package com.github.martinfrank.games.llmquestgenerator.helpdesk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelpDeskRequest {

    @JsonProperty
    String promptMessage;

    @JsonProperty
    String historyId;

    public HelpDeskRequest(String promptMessage, String historyId) {
        this.promptMessage = promptMessage;
        this.historyId = historyId;
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public String getHistoryId() {
        return historyId;
    }
}
