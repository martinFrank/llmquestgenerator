package com.github.martinfrank.games.llmquestgenerator.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void testBuilder(){
        Location marketPlace = Location.builder()
                .id("MARKET_PLACE")
                .type(LocationType.MARKET_PLACE)
                .build();

        Location townHall = Location.builder()
                .id("TOWNHALL")
                .type(LocationType.TOWN_HALL)
                .connect(marketPlace)
                .build();

        Assertions.assertTrue(townHall.toLocationIds.contains(marketPlace.id));
        Assertions.assertTrue(marketPlace.toLocationIds.contains(townHall.id));

    }

}