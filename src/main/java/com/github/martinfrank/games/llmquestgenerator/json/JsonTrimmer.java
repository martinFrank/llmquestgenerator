package com.github.martinfrank.games.llmquestgenerator.json;

public class JsonTrimmer {

    public static String trim(String input) {

        //removes thoughts from deepseek
        if (input.startsWith("<think>") && input.contains("{")) {
            int index = input.indexOf("{");
            input = input.substring(index);
        }

        return input
                .replaceAll("\n", "")
                .replaceAll("`", "") //sometimes md markup is included
                .replaceAll("json\\{", "{") //sometimes formatting is included, such as "```json"
                .replaceAll(",\\s+}", "}") //a comma too much before curly bracket
                .replaceAll(",\\s+]", "]") //a comma too much before bracket

                ;
    }
}
