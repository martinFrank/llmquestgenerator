package com.github.martinfrank.games.llmquestgenerator.player;

public class Skills {

    public Skill search = new Skill(AttributeType.INTELLIGENCE, AttributeType.STRENGTH, AttributeType.CURIOSITY);
    public Skill detectTraps = new Skill(AttributeType.INTELLIGENCE, AttributeType.DEXTERITY, AttributeType.GREED);
    public Skill talk = new Skill(AttributeType.INTELLIGENCE, AttributeType.PERSONALITY, AttributeType.ANGER);
    public Skill attack = new Skill(AttributeType.STRENGTH, AttributeType.DEXTERITY, AttributeType.FEAR);
    public Skill defense = new Skill(AttributeType.STRENGTH, AttributeType.DEXTERITY, AttributeType.ANGER);

//    Object magicFireballSkill;

}
