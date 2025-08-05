package com.github.martinfrank.games.llmquestgenerator.quest;

import java.util.ArrayList;
import java.util.List;

public class Quest {

    public final String id;

    public final List<String> prerequisiteQuestIds = new ArrayList<>();
//    public final List<String> subQuestIds = new ArrayList<>();
    public final List<String> taskIds = new ArrayList<>();
    public final List<String> gameChangeIds = new ArrayList<>();
    public final String plot;
    public final String parentId;
    public boolean isCompleted = false;

    private Quest(String id, String parentId, String plot, List<String> prerequisiteQuestIds, List<String> taskIds, List<String> gameChangeIds) {
        this.id = id;
        this.parentId = parentId;
        this.plot = plot;
        this.prerequisiteQuestIds.addAll(prerequisiteQuestIds);
//        this.subQuestIds.addAll(subQuestIds);
        this.taskIds.addAll(taskIds);
        this.gameChangeIds.addAll(gameChangeIds);
    }

    public void addPrerequisiteQuest(String id) {
        prerequisiteQuestIds.add(id);
    }

//    public void addSubQuest(String id) {
//        subQuestIds.add(id);
//    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private List<String> prerequisiteQuestIds = new ArrayList<>();
//        private List<String> subQuestIds = new ArrayList<>();
        private List<String> taskIds = new ArrayList<>();
        private List<String> gameChangeIds = new ArrayList<>();
        private String plot;
        private String parentId;

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder addPrequisite(Quest prerequisiteQuest){
            prerequisiteQuestIds.add(prerequisiteQuest.id);
            return this;
        }

//        public Builder addSubQuest(Quest subQuest){
//            subQuestIds.add(subQuest.id);
//            return this;
//        }

        public Builder addTask(Quest task){
            taskIds.add(task.id);
            return this;
        }
        public Builder addGameChange(Quest gameChange){
            gameChangeIds.add(gameChange.id);
            return this;
        }
        public Builder plot(String plot){
            this.plot = plot;
            return this;
        }

        public Builder parent(Quest parent) {
            this.parentId = parent.id;
            return this;
        }

        public Quest build() {
            return new Quest(id, parentId, plot, prerequisiteQuestIds, taskIds, gameChangeIds);
        }
    }
}
