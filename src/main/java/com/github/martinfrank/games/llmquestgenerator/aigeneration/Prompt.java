package com.github.martinfrank.games.llmquestgenerator.aigeneration;

import com.google.gson.Gson;

public class Prompt {

    public final String model;// = "llama3";
    public final String prompt;// = "Erkläre Relativitätstheorie in einfachen Worten.";
    public final String system;// = "Erkläre Relativitätstheorie in einfachen Worten.";
//    public final double temperature = 1.1;
    public final double temperature = 1.3;
//    public final double top_p = 0.9;
    public final double top_p = 0.8;
//    public final double top_k = 50;
    public final double top_k = 100;
    public final double repeat_penalty = 1.1;

    public Prompt(String prompt){
//        this("deepseek-r1", prompt, null);
        this("gemma3", prompt, null);
    }

    public Prompt(String prompt, String system){
        this("gemma3", prompt, system);
    }

    public Prompt(String model, String prompt, String system) {
        this.model = model;
        this.prompt = prompt;
        this.system = system;
    }


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
