package com.github.martinfrank.games.llmquestgenerator.monster;

public class Monster {

    private Monster(){

    }

    public static Builder builder(){
        return new Builder();
    }

    private static class Builder {

        public Monster build(){
            return new Monster();
        }
    }
}
