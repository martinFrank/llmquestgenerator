package com.github.martinfrank.games.llmquestgenerator.integration;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationDescription;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationDescriptionGenerator;
import com.github.martinfrank.games.llmquestgenerator.game.Game;
import com.github.martinfrank.games.llmquestgenerator.game.SimpleGameGenerator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LocationGenerationIntegrationTest {

    private static final Logger LOGGER = LogManager.getLogger(LocationGenerationIntegrationTest.class);

    @Test
    void testGenerationLocations() {
        SimpleGameGenerator gameGenerator = new SimpleGameGenerator();
        Game game = gameGenerator.generate();
        List<LocationDescription> generated = LocationDescriptionGenerator.generate(game.locations, game.plot);
        LOGGER.debug(new Gson().toJson(generated));
    }


}
