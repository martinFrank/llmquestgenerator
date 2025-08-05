package com.github.martinfrank.games.llmquestgenerator.game;

public class GameChange {

    public final GameChangeType type;
    public final String id;
    public final String objectId;
    public final String objectParameter;

    private GameChange(GameChangeType type, String id, String objectId, String objectParameter) {
        this.type = type;
        this.id = id;
        this.objectId = objectId;
        this.objectParameter = objectParameter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GameChangeType type;
        private String id;
        private String objectId;
        private String objectParameter;

        public Builder type(GameChangeType type) {
            this.type = type;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder objectId(String objectId) {
            this.objectId = objectId;
            return this;
        }

        public Builder objectParameter(String objectParameter) {
            this.objectParameter = objectParameter;
            return this;
        }

        public GameChange build() {
            return new GameChange(type, id, objectId, objectParameter);
        }
    }
}
