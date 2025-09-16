package com.github.martinfrank.games.llmquestgenerator.game;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.github.martinfrank.games.llmquestgenerator.quest.Quest;
import com.github.martinfrank.games.llmquestgenerator.quest.Task;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public final String plot;
    public final List<Location> locations = new ArrayList<>();
    public final List<Actor> actors = new ArrayList<>();
    public final List<Quest> quests = new ArrayList<>();
    public final List<Task> tasks = new ArrayList<>();
    public final List<GameChange> changes = new ArrayList<>();

    public Game(
            String plot,
            List<Location> locations,
            List<Actor> actors,
            List<Quest> quests,
            List<Task> tasks,
            List<GameChange> changes) {
        this.plot = plot;
        this.locations.addAll(locations);
        this.actors.addAll(actors);
        this.quests.addAll(quests);
        this.tasks.addAll(tasks);
        this.changes.addAll(changes);
    }

}
