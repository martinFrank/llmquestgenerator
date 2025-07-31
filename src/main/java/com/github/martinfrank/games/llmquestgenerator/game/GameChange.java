package com.github.martinfrank.games.llmquestgenerator.game;

public class GameChange {

    public final GameChangeType type;
    public final String id;
    public final String objectId;
    public final String objectParameter;

    public GameChange(GameChangeType type, String id, String objectId, String objectParameter) {
        this.type = type;
        this.id = id;
        this.objectId = objectId;
        this.objectParameter = objectParameter;
    }
}
