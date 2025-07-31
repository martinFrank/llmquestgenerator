package com.github.martinfrank.games.llmquestgenerator.quest;

import com.github.martinfrank.games.llmquestgenerator.game.GameChange;
import com.github.martinfrank.games.llmquestgenerator.game.GameChangeType;

import java.util.List;

public class QuestFactory {

    public static Quest createQuest(String questId, List<Quest> quests) {
        Quest mainQuest = new Quest(questId);
        quests.add(mainQuest);
        return mainQuest;
    }

    public static Quest addSubQuest( String id, Quest parent,List<Quest> quests) {
        Quest quest = new Quest(id);
        parent.addSubQuest(quest.id);
        quests.add(quest);
        return quest;
    }

    public static Task addTask(Quest quest, TaskType taskType, String taskId, String objectiveId, List<Task> tasks) {
        Task task = new Task(taskType, taskId, objectiveId);
        tasks.add(task);
        quest.addTask(task.taskId);
        return task;
    }

    public static GameChange addGameChange(Quest quest, GameChangeType gameChangeType, String changeGameId, String changedObjectId, String changeObjectParameter, List<GameChange> changes) {
        GameChange gameChange = new GameChange(gameChangeType, changeGameId, changedObjectId, changeObjectParameter);
        changes.add(gameChange);
        quest.addGameChange(gameChange.id);
        return gameChange;

    }
}
