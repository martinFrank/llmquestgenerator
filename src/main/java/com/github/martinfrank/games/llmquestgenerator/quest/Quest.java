package com.github.martinfrank.games.llmquestgenerator.quest;

import java.util.ArrayList;
import java.util.List;

public class Quest {

    public final String id;

    public final List<String> prerequisiteQuestIds = new ArrayList<>();
    public final List<String> subQuestIds = new ArrayList<>();
    public final List<String> taskIds = new ArrayList<>();
    public final List<String> gameChangeIds = new ArrayList<>();

    public boolean isCompleted = false;

    public Quest(String id) {
        this.id = id;
    }

    public void addPrerequisiteQuest(String id) {
        prerequisiteQuestIds.add(id);
    }

    public void addSubQuest(String id) {
        subQuestIds.add(id);
    }

    public void addTask(String id) {
        taskIds.add(id);
    }

    public void addGameChange(String id) {
        gameChangeIds.add(id);
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id='" + id + '\'' +
                '}';
    }
}
