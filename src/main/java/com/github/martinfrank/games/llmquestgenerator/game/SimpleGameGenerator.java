package com.github.martinfrank.games.llmquestgenerator.game;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.actor.ActorFactory;
import com.github.martinfrank.games.llmquestgenerator.actor.ActorType;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.github.martinfrank.games.llmquestgenerator.location.LocationFactory;
import com.github.martinfrank.games.llmquestgenerator.location.LocationType;
import com.github.martinfrank.games.llmquestgenerator.quest.Quest;
import com.github.martinfrank.games.llmquestgenerator.quest.QuestFactory;
import com.github.martinfrank.games.llmquestgenerator.quest.Task;
import com.github.martinfrank.games.llmquestgenerator.quest.TaskType;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameGenerator {


    public Game generate() {
        List<Location> locations = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Quest> quests = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        List<GameChange> changes = new ArrayList<>();

        //LOCATIONS
        //start city
        Location nowhere = LocationFactory.createLocation(LocationType.NONE, "NOWHERE", "a undefined place", locations);

        Location marketPlace = LocationFactory.createLocation(LocationType.MARKET_PLACE, "MARKET_PLACE", "a small market place in a small village", locations);
        Location smithy = LocationFactory.addLocation(LocationType.SMITHY, "SMITHY", "a small smithy, mainly producing agricultural tools", marketPlace, locations);
        Location inn = LocationFactory.addLocation(LocationType.INN, "INN", "a small but fine inn, serving an astonishing good beer", marketPlace, locations);
        Location apothecary = LocationFactory.addLocation(LocationType.APOTHECARY, "APOTHECARY", "the village's only herb shop that sells local herbs but also some magic utilities", marketPlace, locations);
        Location villageElderHut = LocationFactory.addLocation(LocationType.ELDER_HUT, "ELDER_HUT", "a small hut for the village elder", marketPlace, locations);
        Location library = LocationFactory.addLocation(LocationType.LIBRARY, "LIBRARY", "a small room inside the village elder hut, that holds lots of books", villageElderHut, locations);

        //way to quest
        Location field = LocationFactory.addLocation(LocationType.FIELDS, "FIELD", "the fileds outside the village where the citizens grow their food. some interesting flowers grow there", marketPlace, locations);
        Location crossroad = LocationFactory.addLocation(LocationType.MEADOW, "CROSSROAD", "a crossroad in the fields", field, locations);
        Location hamlet = LocationFactory.addLocation(LocationType.HAMLET, "HAMLET", "a small hamlet, with a nice flower garden", crossroad, locations);
        Location quarry = LocationFactory.addLocation(LocationType.QUARRY, "QUARRY", "a small quarry, with a hut where miners can live. source for stone for the village", crossroad, locations);
        Location forest = LocationFactory.addLocation(LocationType.GROVE, "FOREST", "the outer skirts of a vast forest. the forest is light and easy to walk",  crossroad, locations);

        //at the quest
        Location deepForest = LocationFactory.addLocation(LocationType.GROVE, "DEEP_FOREST", "the deeper parts of the forest, it is a darker wood, with many unknown plants", forest, locations);
        Location wayToLair = LocationFactory.addLocation(LocationType.FOREST, "WAY_TO_LAIR", "this is a part of the forest, that leads even deeper into the forest. its humid here", deepForest, locations);
        Location lair = LocationFactory.addLocation(LocationType.FOREST, "LAIR", "there are very much small and big spiders in this part of the forest", wayToLair, locations);//side attraction

        Location wayToGrave = LocationFactory.addLocation(LocationType.WOODS, "WAY_TO_GRAVE", "this part of the woods looks darker than the rest and it makes your heart feel heavier", deepForest, locations);
        Location graveLocation = LocationFactory.addLocation(LocationType.WOODS, "GHOSTS_GRAVE", "there is a grave in a clearing of the forest", wayToGrave, locations);
        Location druidsHutLocation = LocationFactory.addLocation(LocationType.CLEARING, "DRUIDS_HUT", "there is a small hut for in a big tree", graveLocation, locations);

        Location wayToMine = LocationFactory.addLocation(LocationType.FOREST, "WAY_TO_MINE", "there is a path in the forest that even deeper into the woods", deepForest, locations);
        Location mine = LocationFactory.addLocation(LocationType.DUNGEON, "MINE", "there is an entrance to the mines, the mine is already abandoned", wayToMine, locations);
        Location deepMine = LocationFactory.addLocation(LocationType.DUNGEON, "DEEP_MINE", "these are the deeper parts of the mine, it makes you feel fear", mine, locations);//side attraction

        //ACTORS
        //town citizens
        Actor smith = ActorFactory.addPerson(ActorType.PERSON, "SMITH", smithy.id, actors);
        Actor innKeeper = ActorFactory.addPerson(ActorType.PERSON, "INN_KEEPER", inn.id, actors);
        Actor apothecarist = ActorFactory.addPerson(ActorType.PERSON, "APOTHECARIST", apothecary.id, actors);
        Actor villageElder = ActorFactory.addPerson(ActorType.PERSON, "VILLAGE_ELDER", villageElderHut.id, actors);
        Actor librarian = ActorFactory.addPerson(ActorType.PERSON, "LIBRARIAN", library.id, actors);
        Actor bard = ActorFactory.addPerson(ActorType.PERSON, "BARD", inn.id, actors);

        //citizens outside of town
        Actor farmer = ActorFactory.addPerson(ActorType.PERSON, "FARMER", hamlet.id, actors);
        Actor miner = ActorFactory.addPerson(ActorType.PERSON, "MINER", quarry.id, actors);

        //quest person
        Actor ghostPeaceful = ActorFactory.addPerson(ActorType.PERSON, "GHOST_PEACEFUL", graveLocation.id, actors);
        Actor ghostMenace = ActorFactory.addPerson(ActorType.PERSON, "GHOST_MENACING", nowhere.id, actors);

        //QUESTS
        Quest mainQuest = QuestFactory.createQuest("BRINGING_PEACE_TO_GHOST(MAIN)", quests);
        Quest talkToVillageElder = QuestFactory.addSubQuest("QUEST_TALK_TO_VILLAGE_ELDER", mainQuest, quests);
        QuestFactory.addTask(talkToVillageElder, TaskType.TALK_TO, "TASK_TALK_TO_VILLAGE_ELDER", villageElder.id, tasks);

        //teil 1
        Quest findReason = QuestFactory.addSubQuest("FIND_REASON", mainQuest, quests);
        findReason.addPrerequisiteQuest(talkToVillageElder.id);
        Quest talkToGhost = QuestFactory.addSubQuest("TALK_TO_GHOST", findReason, quests);
        QuestFactory.addTask(talkToGhost, TaskType.TALK_TO, "TALK_TO_GHOST", ghostPeaceful.id, tasks);

        //teil 1.5, hihi
        Quest talkToLibrarian = QuestFactory.addSubQuest("TALK_TO_LIBRARIAN", mainQuest, quests);
        QuestFactory.addTask(talkToLibrarian, TaskType.TALK_TO, "TALK_TO_LIBRARIAN", librarian.id, tasks);
        talkToLibrarian.addPrerequisiteQuest(talkToGhost.id);

        Quest talkToMiner = QuestFactory.addSubQuest("TALK_MINER", findReason, quests);
        QuestFactory.addTask(talkToMiner, TaskType.TALK_TO, "TALK_TO_MINER", miner.id, tasks);
        talkToMiner.addPrerequisiteQuest(talkToGhost.id);

        Quest findRing = QuestFactory.addSubQuest("FIND_RING", findReason, quests); //
        QuestFactory.addTask(findRing, TaskType.SEARCH_AT, "SEARCH_RING", deepMine.id, tasks);
        findRing.addPrerequisiteQuest(talkToMiner.id);

        //teil2
        Quest bringPeaceToGhost = QuestFactory.addSubQuest("REMOVE_EVIL", mainQuest, quests);
        bringPeaceToGhost.addPrerequisiteQuest(findReason.id);

        Quest findFlowers = QuestFactory.addSubQuest("FIND_FLOWERS", bringPeaceToGhost, quests); //
        QuestFactory.addTask(findFlowers, TaskType.SEARCH_AT, "SEARCH_FLOWERS", hamlet.id, tasks);
        findFlowers.addPrerequisiteQuest(talkToLibrarian.id);

        Quest findSongOfSorrows = QuestFactory.addSubQuest("FIND_SONG_OF_SORROWS", bringPeaceToGhost, quests); //
        QuestFactory.addTask(findSongOfSorrows, TaskType.TALK_TO, "FIND_SONG_OF_SORROW", bard.id, tasks);
        findSongOfSorrows.addPrerequisiteQuest(talkToLibrarian.id);

        Quest awakenGhost = QuestFactory.addSubQuest("AWAKEN_GHOST", bringPeaceToGhost, quests); //
        awakenGhost.addPrerequisiteQuest(findSongOfSorrows.id);
        awakenGhost.addPrerequisiteQuest(findFlowers.id);
        QuestFactory.addTask(awakenGhost, TaskType.DEPOSIT_AT, "DEPOSIT_RING", graveLocation.id, tasks);
        QuestFactory.addTask(awakenGhost, TaskType.DEPOSIT_AT, "DEPOSIT_FLOWERS", graveLocation.id, tasks);
        QuestFactory.addTask(awakenGhost, TaskType.DEPOSIT_AT, "SONG_SONG", graveLocation.id, tasks);
        QuestFactory.addGameChange(awakenGhost, GameChangeType.MOVE_PERSON, "REMOVE_PEACEFUL_GHOST", ghostPeaceful.id, nowhere.id, changes);
        QuestFactory.addGameChange(awakenGhost, GameChangeType.MOVE_PERSON, "ADD_MENACING_GHOST", ghostMenace.id, graveLocation.id, changes);

        Quest settleGhost = QuestFactory.addSubQuest("SETTLE_GHOST", bringPeaceToGhost, quests); //
        settleGhost.addPrerequisiteQuest(awakenGhost.id);
        QuestFactory.addTask(settleGhost, TaskType.FIGHT, "FIGHT_MENANCING_GHOST", ghostMenace.id, tasks);

        Game game = new Game(
                locations,
                actors,
                quests,
                tasks,
                changes);

        return game;
    }
}
