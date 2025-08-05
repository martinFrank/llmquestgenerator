package com.github.martinfrank.games.llmquestgenerator.player;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public String name;
    public PlayerClass playerClass;
    public PlayerRace playerRace;

    public double lifePoints;
    public double endurancePoints; //inkl. geduld

    List<Integer> experiencePoints;
    List<Object> improvements = new ArrayList<>(); //verbesserungen pro level

    public Attributes attributes = new Attributes();
    public Skills skills = new Skills();


}
