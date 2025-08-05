package com.github.martinfrank.games.llmquestgenerator;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.AppSettings;
import org.junit.jupiter.api.Test;

class AppSettingsTest {

    @Test
    void testSettings(){
        String value = AppSettings.get("test.test.data");
        System.out.println(value);
    }
}
