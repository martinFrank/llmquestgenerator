package com.github.martinfrank.games.llmquestgenerator.helpdesk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelpDeskResponse {

    @JsonProperty
    public String result;

    public HelpDeskResponse(String result) {
        this.result = result;
    }
}
