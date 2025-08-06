package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.location.Location;

public class ApplyTask extends Task {

    public final String locationId;
    public final String applyAction;
    private String detail;


    private ApplyTask(String id, String locationId, String applyAction, boolean isOptional) {
        super(TaskType.APPLY, id, isOptional);
        this.locationId = locationId;
        this.applyAction = applyAction;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        //        private TaskType type;
        private String id;
        private String locationId;
        private String applyAction;
        private boolean isOptional;

        public Builder id(String taskId) {
            this.id = taskId;
            return this;
        }

        public Builder location(Location location) {
            this.locationId = location.id;
            return this;
        }


        public Builder applyAction(String action) {
            this.applyAction = action;
            return this;
        }


        public Builder optional() {
            this.isOptional = true;
            return this;
        }

        public ApplyTask build() {
            return new ApplyTask(id, locationId, applyAction, isOptional);
        }

    }
    
}
