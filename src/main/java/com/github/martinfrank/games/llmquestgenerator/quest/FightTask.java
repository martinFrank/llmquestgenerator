package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.location.Location;

public class FightTask extends Task {

    public final String actorId;
    public final String locationId;
    public final String result;
    private String detail;

    private FightTask(String id, String actorId, String locationId, String result, boolean isOptional) {
        super(TaskType.FIGHT, id, isOptional);
        this.actorId = actorId;
        this.locationId = locationId;
        this.result = result;
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
        private String actorId;
        private String locationId;
        private boolean isOptional;
        public String result;

        public Builder id(String taskId) {
            this.id = taskId;
            return this;
        }

        public Builder location(Location location) {
            this.locationId = location.id;
            return this;
        }

        public Builder actor(Actor actor) {
            this.actorId = actor.id;
            return this;
        }

        public Builder optional() {
            this.isOptional = true;
            return this;
        }

        public Builder result(String result) {
            this.result = result;
            return this;
        }

        public FightTask build() {
            return new FightTask(id, actorId, locationId, result, isOptional);
        }


    }

}
