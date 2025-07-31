package com.github.martinfrank.games.llmquestgenerator.quest;

public class Task {

    public final TaskType type;
    public final String taskId;
    public final String objectId;

    public Task(TaskType type, String taskId, String objectId) {
        this.type = type;
        this.taskId = taskId;
        this.objectId = objectId;
    }
}
