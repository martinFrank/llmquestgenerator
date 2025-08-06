package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.location.Location;

public class SearchTask extends Task {
    
    public final String locationId;
    public final String desiredObject;

    private SearchTaskDetail successDetails;
    private SearchTaskDetail failureDetails;

    private SearchTask(String id, String locationId, String desiredObject, boolean isOptional) {
        super(TaskType.SEARCH_AT, id, isOptional);
        this.locationId = locationId;
        this.desiredObject = desiredObject;
    }

    public static Builder builder(){
        return new Builder();
    }

    public void setSuccess(SearchTaskDetail successDetails) {
        this.successDetails = successDetails;
    }

    public void setFailure(SearchTaskDetail failureDetails) {
        this.failureDetails = failureDetails;
    }

    public static class Builder {
        //        private TaskType type;
        private String id;
        private String locationId;
        private String desiredObject;
        private boolean isOptional;

        public Builder id(String taskId) {
            this.id = taskId;
            return this;
        }

        public Builder location(Location location) {
            this.locationId = location.id;
            return this;
        }


        public Builder desiredObject(String desiredObject) {
            this.desiredObject = desiredObject;
            return this;
        }

        public SearchTask build() {
            return new SearchTask(id, locationId, desiredObject, false);
        }

    }
}
