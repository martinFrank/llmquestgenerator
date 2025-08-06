package com.github.martinfrank.games.llmquestgenerator;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.actor.ActorGenerator;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationGenerator;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.quest.QuestGenerator;
import com.github.martinfrank.games.llmquestgenerator.game.Game;
import com.github.martinfrank.games.llmquestgenerator.game.SimpleGameGenerator;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.github.martinfrank.games.llmquestgenerator.quest.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class GenerationTest {

    private static final Logger LOGGER = LogManager.getLogger(GenerationTest.class);

    @Test
    void testGenerationGame() {
        SimpleGameGenerator gameGenerator = new SimpleGameGenerator();
        Game game = gameGenerator.generate();
        LOGGER.debug(new Gson().toJson(game));
        Assertions.assertNotNull(game);
        Assertions.assertNotNull(game.locations);
        Assertions.assertFalse(game.locations.isEmpty());
        Assertions.assertNotNull(game.actors);
        Assertions.assertFalse(game.actors.isEmpty());
        Assertions.assertNotNull(game.quests);
        Assertions.assertFalse(game.quests.isEmpty());
        Assertions.assertNotNull(game.changes);
        Assertions.assertFalse(game.changes.isEmpty());
    }

    @Test
    void testGenerationLocations() {
        Game game = load("rawgame.json");
        LocationGenerator.generate(game.locations);
        LOGGER.debug(new Gson().toJson(game));
        for(Location location : game.locations) {
            Assertions.assertNotNull(location.getDetails());
            Assertions.assertNotNull(location.getDetails().details);
            Assertions.assertNotNull(location.getDetails().name);
            Assertions.assertNotNull(location.getDetails().image);
        }
    }

    @Test
    void testGenerationActors()  {
        Game game = load("generatedLocations.json");
        ActorGenerator.generate(game.actors, game.locations);
        LOGGER.debug(new Gson().toJson(game));
        for(Actor actor : game.actors) {
            Assertions.assertNotNull(actor.getDetails());
            Assertions.assertNotNull(actor.getDetails().details);
            Assertions.assertNotNull(actor.getDetails().name);
            Assertions.assertNotNull(actor.getDetails().image);
        }
    }

    @Test
    void testGenerationQuest() {
        Game game = load("generatedActors.json");
//        Game game = load("rawgame.json");
//        Quest quest = game.quests.stream().filter(q -> q.id.equals("FIND_RING")).findAny().orElseThrow();
//        Quest quest = game.quests.stream().filter(q -> q.id.equals("QUEST_TALK_TO_VILLAGE_ELDER")).findAny().orElseThrow();
//        Quest quest = game.quests.stream().filter(q -> q.id.equals("AWAKEN_GHOST")).findAny().orElseThrow();
        Quest quest = game.quests.stream().filter(q -> q.id.equals("SETTLE_GHOST")).findAny().orElseThrow();
        game.quests.clear();
        game.quests.add(quest);
        LOGGER.debug(new Gson().toJson(game));
        QuestGenerator.generate(game.quests, game.tasks, game.actors, game.locations);
//        for(Actor actor : game.actors) {
//            Assertions.assertNotNull(actor.getDetails());
//            Assertions.assertNotNull(actor.getDetails().details);
//            Assertions.assertNotNull(actor.getDetails().name);
//            Assertions.assertNotNull(actor.getDetails().image);
//        }
    }



    private static Game load(String filename) {
        File directory = new File("src/test/resources/");

        RuntimeTypeAdapterFactory<Task> adapterFactory = RuntimeTypeAdapterFactory
                .of(Task.class, "type")
                .registerSubtype(TalkTask.class, TaskType.TALK_TO.toString())
                .registerSubtype(SearchTask.class, TaskType.SEARCH_AT.toString())
                .registerSubtype(ApplyTask.class, TaskType.APPLY.toString())
                .registerSubtype(FightTask.class, TaskType.FIGHT.toString());


        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(adapterFactory)
                .create();

        try {
            Game game = gson.fromJson(new FileReader(new File(directory, filename)), Game.class);
            for (Task task : game.tasks) {
                if (task instanceof TalkTask talkTask) {
                    talkTask.type = TaskType.TALK_TO;
                }
                if (task instanceof SearchTask searchTask) {
                    searchTask.type = TaskType.SEARCH_AT;
                }
                if (task instanceof ApplyTask applyTask) {
                    applyTask.type = TaskType.APPLY;
                }
                if (task instanceof FightTask fightTask) {
                    fightTask.type = TaskType.FIGHT;
                }
            }
            return game;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
