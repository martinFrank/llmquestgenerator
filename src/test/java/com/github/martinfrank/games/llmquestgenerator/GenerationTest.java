package com.github.martinfrank.games.llmquestgenerator;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.actor.ActorGenerator;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationGenerator;
import com.github.martinfrank.games.llmquestgenerator.game.Game;
import com.github.martinfrank.games.llmquestgenerator.game.SimpleGameGenerator;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
//        ActorGenerator.generate(game.actors, game.locations);
        LOGGER.debug(new Gson().toJson(game));
//        for(Actor actor : game.actors) {
//            Assertions.assertNotNull(actor.getDetails());
//            Assertions.assertNotNull(actor.getDetails().details);
//            Assertions.assertNotNull(actor.getDetails().name);
//            Assertions.assertNotNull(actor.getDetails().image);
//        }
    }



    private static Game load(String filename) {
        File directory = new File("src/test/resources/");
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(new File(directory, filename)), Game.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
