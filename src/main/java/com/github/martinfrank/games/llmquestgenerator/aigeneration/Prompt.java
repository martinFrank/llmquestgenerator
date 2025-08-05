package com.github.martinfrank.games.llmquestgenerator.aigeneration;

public class Prompt {

    public final String model;
    public final String prompt;
    public final String system;
    public final double temperature;
    public final double topP;
    public final double topK;
    public final double repeatPenalty;

    private Prompt(String model, String prompt, String system, double temperature, double topP, double topK, double repeatPenalty) {
        this.model = model;
        this.prompt = prompt;
        this.system = system;
        this.temperature = temperature;
        this.topP = topP;
        this.topK = topK;
        this.repeatPenalty = repeatPenalty;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String model = "gemma3";
        private String userPrompt = "why is the sky blue";
        private String systemPrompt = "";
        private double temperature = 1.1;
        private double topP = 0.9;
        private double topK = 50;
        private double repeatPenalty = 1.1;

        public Builder model(String model){
            this.model = model;
            return this;
        }

        public Builder userPrompt(String userPrompt){
            this.userPrompt = userPrompt;
            return this;
        }

        public Builder systemPrompt(String systemPrompt){
            this.systemPrompt = systemPrompt;
            return this;
        }

        public Builder random(){
            temperature = 1.3;
            topP = 0.8;
            topK = 100;
            repeatPenalty = 1.3;
            return this;
        }

        public Builder straight(){
            temperature = 1.1;
            topP = 0.9;
            topK = 50;
            repeatPenalty = 1.1;
            return this;
        }

        public Builder temperature(double temperature){
            this.temperature = temperature;
            return this;
        }

        public Builder topP(double top_p){
            this.topP = top_p;
            return this;
        }
        public Builder topK(double top_k){
            this.topK = top_k;
            return this;
        }
        public Builder repeatPenalty(double repeat_penalty){
            this.repeatPenalty = repeat_penalty;
            return this;
        }
        public Prompt build(){
            if (userPrompt == null){
                throw new IllegalStateException("userPrompt is null");
            }
            return new Prompt(model, userPrompt, systemPrompt, temperature, topP, topK, repeatPenalty);
        }
    }

}
