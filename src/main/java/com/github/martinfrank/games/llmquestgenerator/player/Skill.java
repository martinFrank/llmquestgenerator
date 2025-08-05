package com.github.martinfrank.games.llmquestgenerator.player;

public class Skill {

    public final AttributeType primary;
    public final AttributeType secondary;
    public final AttributeType bad;

    public Skill(AttributeType primary, AttributeType secondary, AttributeType bad) {
        this.primary = primary;
        this.secondary = secondary;
        this.bad = bad;
    }

    public boolean execute(int modifier){
        return false;
    }

}
