package com.github.martinfrank.games.llmquestgenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class LogTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    void testLogs() {
        LOGGER.info("INFO ->>>>>>>>>>>>>>>");
        LOGGER.trace("TRACE ->>>>>>>>>>>>>>>");
        LOGGER.debug("DEBUG ->>>>>>>>>>>>>>>");
        LOGGER.warn("WARN ->>>>>>>>>>>>>>>");
        LOGGER.fatal("FATAL ->>>>>>>>>>>>>");
        LOGGER.error("ERROR ->>>>>>>>>>>>>>");
    }
}
