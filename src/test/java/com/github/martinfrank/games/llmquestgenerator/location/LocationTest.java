package com.github.martinfrank.games.llmquestgenerator.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void testBuilder() {
        //given
        Location marketPlace = new Location(LocationType.MARKET_PLACE, "MARKET_PLACE", "a market place");
        Location townHall = new Location(LocationType.TOWN_HALL, "TOWNHALL", "the town hall");

        //when
        marketPlace.connect(townHall);

        //then
        Assertions.assertTrue(townHall.toLocationIds.contains(marketPlace.id));
        Assertions.assertTrue(marketPlace.toLocationIds.contains(townHall.id));
    }
}