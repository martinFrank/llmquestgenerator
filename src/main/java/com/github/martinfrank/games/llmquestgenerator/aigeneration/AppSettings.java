package com.github.martinfrank.games.llmquestgenerator.aigeneration;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class AppSettings {

    private static final Properties PROPERTIES = new Properties();
    private static boolean isLoaded = false;

    public static String get(String key) {
        if(!isLoaded) {
            try (InputStream input = AppSettings.class.getClassLoader().getResourceAsStream("application.properties")) {
                PROPERTIES.load(input);
                isLoaded = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return PROPERTIES.getProperty(key);
    }

}
