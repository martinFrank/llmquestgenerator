package com.github.martinfrank.games.llmquestgenerator.aigeneration;

public class Trimmer {

    public static String trim(String input){
            input = input.replace("“", "\"");
            input = input.replace("”", "\"");
            input = input.replace("„", "\"");
            input = input.replace("’", "'");
            input = input.replace("\n", " ");
            return input.trim();
    }
}
