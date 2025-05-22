package com.github.martinfrank.games.llmquestgenerator.json;

import com.google.gson.Gson;

public class JsonMapper {

    private JsonMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static <T> T fromJson(String questSummaryJson, Class<T> clazz) {
        String stripped = JsonTrimmer.trim(questSummaryJson);
//        System.out.println("------------stripped---------");
//        System.out.println(stripped);
        return new Gson().fromJson(stripped, clazz);
    }

    public static String toJson(Object object) {
        return new Gson().newBuilder().disableHtmlEscaping().create().toJson(object);
    }

}
