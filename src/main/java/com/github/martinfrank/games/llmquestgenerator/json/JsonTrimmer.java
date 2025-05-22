package com.github.martinfrank.games.llmquestgenerator.json;

public class JsonTrimmer {

    public static String trim(String input) {
        return input
                .replace("\n", "")
                .replace("`", "") //sometimes md markup is included
                .replace("json{", "{") //sometimes formatting is included, such as "```json"
                .replace(",\\d+}", "}") //a comma too much before curly bracket
                .replace(",\\d+]", "]") //a comma too much before bracket
                ;
    }

}
