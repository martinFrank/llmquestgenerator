package com.github.martinfrank.games.llmquestgenerator.game;

import com.github.martinfrank.games.llmquestgenerator.quest.Quest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.desktop.QuitEvent;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGameGeneratorTest {


    @Test
    void testSimpleQuestGameGenerator() {
        //given
        SimpleGameGenerator generator = new SimpleGameGenerator();

        //when
        Game game = generator.generate();

        //then
        assertNotNull(game);

        //FIXME - separate tests

        //jedes Parent Quest darf KEINE Task haben!
        Set<String> parentIds = game.quests.stream().filter(q -> q.parentId != null).map(q -> q.parentId).collect(Collectors.toSet());
        boolean hasParentQuestsWithTasks = game.quests.stream().filter(q -> parentIds.contains(q.id)).anyMatch(q -> !q.taskIds.isEmpty());
        Assertions.assertFalse(hasParentQuestsWithTasks);

        //jedes subQuest MUSS tasks haben
        boolean hasSubQuestWithoutTasks = game.quests.stream().filter(q -> !parentIds.contains(q.id)).anyMatch(q -> q.taskIds.isEmpty());
        Assertions.assertFalse(hasSubQuestWithoutTasks);

        //quest ids are unique
        Set<String> questIdsAsSet = game.quests.stream().map(quest -> quest.id).collect(Collectors.toSet());
        assertEquals(game.quests.size(), questIdsAsSet.size());

        //task ids are unique
        Set<String> taskIdsAsSet = game.tasks.stream().map(task -> task.id).collect(Collectors.toSet());
        assertEquals(game.tasks.size(), taskIdsAsSet.size());



        //print JSON
        System.out.println(new Gson().toJson(game));
    }

}