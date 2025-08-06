package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;

public class TalkTask extends Task {

    public final String actorId;
    public final String mystery;
    private TalkTaskDetail successDetails;
    private TalkTaskDetail failDetails;

    private TalkTask(String id, String actorId, String mystery, boolean isOptional) {
        super(TaskType.TALK_TO, id, isOptional);
        this.actorId = actorId;
        this.mystery = mystery;
    }

    public TalkTaskDetail getSuccessDetails() {
        return successDetails;
    }

    public void setSuccessDetails(TalkTaskDetail successDetails) {
        this.successDetails = successDetails;
    }

    public TalkTaskDetail getFailDetails() {
        return failDetails;
    }

    public void setFailDetails(TalkTaskDetail failDetails) {
        this.failDetails = failDetails;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
//        private TaskType type;
        private String id;
        private String actorId;
        private String mystery;
        private boolean isOptional;

        public Builder id(String taskId) {
            this.id = taskId;
            return this;
        }

        public Builder actor(Actor actor) {
            this.actorId = actor.id;
            return this;
        }


        public Builder mystery(String unknown) {
            this.mystery = unknown;
            return this;
        }


        public Builder optional() {
            this.isOptional = true;
            return this;
        }

        public TalkTask build() {
            return new TalkTask(id, actorId, mystery, isOptional);
        }

    }


}
