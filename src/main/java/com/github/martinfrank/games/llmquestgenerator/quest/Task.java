package com.github.martinfrank.games.llmquestgenerator.quest;

public class Task {

    public final TaskType type;
    public final String id;
    public final String objectId;
    public final String result;

    private Task(TaskType type, String id, String objectId, String result) {
        this.type = type;
        this.id = id;
        this.objectId = objectId;
        this.result = result;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private TaskType type;
        private String id;
        private String objectId;
        private String result;

        public Builder type(TaskType type) {
            this.type = type;
            return this;
        }

        public Builder id(String taskId) {
            this.id = taskId;
            return this;
        }

        public Builder objectId(String objectId) {
            this.objectId = objectId;
            return this;
        }

        public Builder result(String result) {
            this.result = result;
            return this;
        }

        public Task build() {
            return new Task(type, id, objectId, result);
        }
    }
}
