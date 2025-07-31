package com.github.martinfrank.games.llmquestgenerator.game;

import com.github.martinfrank.games.llmquestgenerator.quest.Quest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGameGeneratorTest {

    @Test
    void testSimpleQuestGameGenerator() {
        //given
        SimpleGameGenerator generator = new SimpleGameGenerator();

        //when
        Game game = generator.generate();

        //jedes Quest OHNE subQuests muss mind. einen Task haben!
        List<Quest> questsWithMissingTasks = game.quests.stream().filter(q -> q.subQuestIds.isEmpty()).filter(q -> q.taskIds.isEmpty()).toList();
        Assertions.assertTrue(questsWithMissingTasks.isEmpty());

        //jedes Quest MIT subQuests drauf keinen Task haben!
        List<Quest> questsWithTasksAndSubQuests = game.quests.stream().filter(q -> !q.subQuestIds.isEmpty()).filter(q -> !q.taskIds.isEmpty()).toList();
        Assertions.assertTrue(questsWithTasksAndSubQuests.isEmpty());

        game.locations.forEach(l -> System.out.println(l.type.toString()));

        //then
        assertNotNull(game);

        //write to JSON
        System.out.println(new Gson().toJson(game));
    }

}