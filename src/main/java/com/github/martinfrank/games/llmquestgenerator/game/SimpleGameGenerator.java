package com.github.martinfrank.games.llmquestgenerator.game;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.actor.ActorType;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.github.martinfrank.games.llmquestgenerator.location.LocationType;
import com.github.martinfrank.games.llmquestgenerator.quest.Quest;
import com.github.martinfrank.games.llmquestgenerator.quest.Task;
import com.github.martinfrank.games.llmquestgenerator.quest.TaskType;

public class SimpleGameGenerator {


    public Game generate() {
        AddList<Location> locations = new AddList<>();
        AddList<Actor> actors = new AddList<>();
        AddList<Quest> quests = new AddList<>();
        AddList<Task> tasks = new AddList<>();
        AddList<GameChange> changes = new AddList<>();

        //LOCATIONS
        //start city
        Location nowhere = locations.add(Location.builder().type(LocationType.NONE).id("NOWHERE").build());

        Location marketPlace = locations.add(Location.builder().type(LocationType.MARKET_PLACE).id("MARKET_PLACE").build());
        Location smithy = locations.add(Location.builder().type(LocationType.SMITHY).id("SMITHY").connect(marketPlace).build());
        Location inn = locations.add(Location.builder().type(LocationType.INN).id("INN").connect(marketPlace).build());
        Location apothecary = locations.add(Location.builder().type(LocationType.APOTHECARY).id("APOTHECARY").connect(marketPlace).build());
        Location villageElderHut = locations.add(Location.builder().type(LocationType.ELDER_HUT).id("ELDER_HUT").connect(marketPlace).build());
        Location library = locations.add(Location.builder().type(LocationType.LIBRARY).id("LIBRARY").connect(villageElderHut).build());

        //way to quest
        Location field = locations.add(Location.builder().type(LocationType.FIELDS).id("FIELD").connect(marketPlace).build());
        Location crossroad = locations.add(Location.builder().type(LocationType.MEADOW).id("CROSSROAD").connect(field).build());
        Location hamlet = locations.add(Location.builder().type(LocationType.HAMLET).id("HAMLET").connect(crossroad).build());
        Location quarry = locations.add(Location.builder().type(LocationType.QUARRY).id("QUARRY").connect(crossroad).build());
        Location forest = locations.add(Location.builder().type(LocationType.GROVE).id("FOREST").connect(crossroad).build());

        //at the quest
        Location deepForest = locations.add(Location.builder().type(LocationType.GROVE).id("DEEP_FOREST").connect(forest).build());
        Location wayToLair = locations.add(Location.builder().type(LocationType.FOREST).id("WAY_TO_LAIR").connect(deepForest).build());
        Location lair = locations.add(Location.builder().type(LocationType.FOREST).id("LAIR").connect(wayToLair).build());//side attraction

        Location wayToGrave = locations.add(Location.builder().type(LocationType.WOODS).id("WAY_TO_GRAVE").connect(deepForest).build());
        Location graveLocation = locations.add(Location.builder().type(LocationType.WOODS).id("GHOSTS_GRAVE").connect(wayToGrave).build());
        Location druidsHutLocation = locations.add(Location.builder().type(LocationType.CLEARING).id("DRUIDS_HUT").connect(graveLocation).build());

        Location wayToMine = locations.add(Location.builder().type(LocationType.FOREST).id("WAY_TO_MINE").connect(deepForest).build());
        Location mine = locations.add(Location.builder().type(LocationType.DUNGEON).id("MINE").connect(wayToMine).build());
        Location deepMine = locations.add(Location.builder().type(LocationType.DUNGEON).id("DEEP_MINE").connect(mine).build());//side attraction

        //ACTORS
        //town citizens
        Actor smith = actors.add(Actor.builder().type(ActorType.PERSON).id("SMITH").location(smithy).build());
        Actor innKeeper = actors.add(Actor.builder().type(ActorType.PERSON).id("INN_KEEPER").location(inn).build());
        Actor apothecarist = actors.add(Actor.builder().type(ActorType.PERSON).id("APOTHECARIST").location(apothecary).build());
        Actor villageElder = actors.add(Actor.builder().type(ActorType.PERSON).id("VILLAGE_ELDER").location(villageElderHut).build());
        Actor librarian = actors.add(Actor.builder().type(ActorType.PERSON).id("LIBRARIAN").location(library).build());
        Actor bard = actors.add(Actor.builder().type(ActorType.PERSON).id("BARD").location(inn).build());

        //citizens outside of town
        Actor farmer = actors.add(Actor.builder().type(ActorType.PERSON).id("FARMER").location(hamlet).build());
        Actor miner = actors.add(Actor.builder().type(ActorType.PERSON).id("MINER").location(quarry).build());

        //quest person
        Actor ghostPeaceful = actors.add(Actor.builder().type(ActorType.PERSON).id("GHOST_PEACEFUL").location(graveLocation).build());
        Actor ghostMenace = actors.add(Actor.builder().type(ActorType.PERSON).id("GHOST_MENACING").location(nowhere).build());

        //QUESTS
        //starter-quest
        Quest talkToVillageElderQuest = quests.add(Quest.builder().id("QUEST_TALK_TO_VILLAGE_ELDER")
                .plot("you have been asked to talk to the village elderly and help him").build());
        Task talkToVillageElderTask = tasks.add(Task.builder().id("TASK_TALK_TO_VILLAGE_ELDER").type(TaskType.TALK_TO).objectId(villageElder.id)
                .result("you find out that the village is haunted and the village elder wants you to solve it").build());
        talkToVillageElderQuest.addTask(talkToVillageElderTask.id);

        //main quest
        Quest mainQuest = quests.add(Quest.builder().id("BRINGING_PEACE_TO_GHOST")
                .plot("people and animals feel sick. nightmares and fear strikes the village. find the reason and remove it.").addPrequisite(talkToVillageElderQuest).build());

        //Teil 1 find reason
        Quest findReasonQuest = quests.add(Quest.builder().id("FIND_REASON").parent(mainQuest)
                .plot("find out what reason causes the terror").build());

        Quest talkToVillagersQuest = quests.add(Quest.builder().id("TALK_TO_VILLAGERS").parent(findReasonQuest)
                .plot("ask around and find a clue").build());
        Task talkToSmithTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_SMITH").objectId(smith.id)
                .result("knows nothing").build();
        Task talkToInnKeeperTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_INN_KEEPER").objectId(innKeeper.id)
                .result("knows nothing").build();
        Task talkToLibrarianTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_LIBRARIAN").objectId(librarian.id)
                .result("knows nothing").build();
        Task talkToApothecaristTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_APOTHECARIST").objectId(apothecarist.id)
                .result("knows nothing").build();
        Task talkToFarmerTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_APOTHECARIST").objectId(farmer.id)
                .result("saw the ghost in the forrest").build();
        talkToVillagersQuest.addTask(talkToSmithTask.id);
        talkToVillagersQuest.addTask(talkToInnKeeperTask.id);
        talkToVillagersQuest.addTask(talkToLibrarianTask.id);
        talkToVillagersQuest.addTask(talkToApothecaristTask.id);
        talkToVillagersQuest.addTask(talkToFarmerTask.id);

        Quest investigateTheGhostQuest = quests.add(Quest.builder().id("INVESITGATE_THE_GHOST").parent(findReasonQuest)
                .plot("now its clear that there is a ghost - lets investigate the ghost in the forest").build());
        investigateTheGhostQuest.addPrerequisiteQuest(talkToVillagersQuest.id);
        Task searchTheGraveTask = Task.builder().type(TaskType.SEARCH_AT).id("SEARCH_THE_GRAVE").objectId(graveLocation.id)
                .result("you find the unrest ghost and he wants to talk to you").build();
        Task talkToGhostTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_GHOST").objectId(ghostPeaceful.id)
                .result("the ring has been stolen and this makes the ghost unrest").build();
        investigateTheGhostQuest.addTask(searchTheGraveTask.id);
        investigateTheGhostQuest.addTask(talkToGhostTask.id);

        Quest learnTheRemedyQuest = quests.add(Quest.builder().id("LEARN_REMEDY").parent(findReasonQuest)
                .plot("you hope that the librarian knows how to settle a ghost").build());
        learnTheRemedyQuest.addPrerequisiteQuest(investigateTheGhostQuest.id);
        Task talkToLibrarianForRemedyTask = tasks.add(Task.builder().id("TALK_TO_LIBRARIAN_FOR_REMEDY").objectId(librarian.id)
                .result("the librarian says to settle the ghost you need to return the ring, plant flowers, and sing the song of sorrows").build());
        learnTheRemedyQuest.addTask(talkToLibrarianForRemedyTask.id);

        //Teil2 - bring peace
        Quest bringPeaceToGhostQuest = quests.add(Quest.builder().id("REMOVE_EVIL").parent(mainQuest).addPrequisite(findReasonQuest)
                .plot("find a way to set the ghost to rest").build());

        Quest findRingStealerQuest = quests.add(Quest.builder().id("FIND_RING_STEALER").parent(bringPeaceToGhostQuest)
                .plot("find out who stole the ring").build());
        Task talkToSmithAboutRingTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_SMITH_ABOUT_RING").objectId(smith.id)
                .result("didn't take the ring").build();
        Task talkToInnKeeperAboutRingTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_INN_KEEPER_ABOUT_RING").objectId(innKeeper.id)
                .result("didn't take the ring").build();
        Task talkToApothecaristAboutRingTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_APOTHECARIST_ABOUT_RING").objectId(apothecarist.id)
                .result("didn't take the ring").build();
        Task talkToSmithTaskAboutRingTask = Task.builder().type(TaskType.TALK_TO).id("TALK_TO_MINER_ABOUT_RING").objectId(miner.id)
                .result("took the ring, regretted it, threw into deep mines").build();
        findRingStealerQuest.addTask(talkToSmithAboutRingTask.id);
        findRingStealerQuest.addTask(talkToInnKeeperAboutRingTask.id);
        findRingStealerQuest.addTask(talkToApothecaristAboutRingTask.id);
        findRingStealerQuest.addTask(talkToSmithTaskAboutRingTask.id);

        Quest findRingQuest = quests.add(Quest.builder().id("FIND_RING").parent(findReasonQuest).addPrequisite(findRingStealerQuest)
                .plot("find the ring that causes the ghost to roam").build());
        Task searchForRingTask = Task.builder().type(TaskType.SEARCH_AT).id("SEARCH_RING").objectId(deepMine.id)
                .result("you found the ring").build();
        findRingQuest.addTask(searchForRingTask.id);

        Quest findFlowersQuest = Quest.builder().id("FIND_FLOWERS").parent(bringPeaceToGhostQuest)
                .plot("find flowers for the ritual").build();
        Task searchForFlowersTask = Task.builder().type(TaskType.SEARCH_AT).id("SEARCH_FLOWERS").objectId(hamlet.id)
                .result("you found the flowers").build();
        findFlowersQuest.addTask(searchForFlowersTask.id);

        Quest findSongOfSorrowsQuest = Quest.builder().id("FIND_SONG_OF_SORROWS").parent(bringPeaceToGhostQuest)
                .plot("find song for the ritual").build();
        Task searchForSongOfSorrowTask = Task.builder().type(TaskType.TALK_TO).id("FIND_SONG_OF_SORROW").objectId(bard.id)
                .result("you found the flowers").build();
        findSongOfSorrowsQuest.addTask(searchForSongOfSorrowTask.id);

        Quest awakenTheGhostQuest = quests.add(Quest.builder().id("AWAKEN_GHOST").parent(bringPeaceToGhostQuest)
                .addPrequisite(findSongOfSorrowsQuest).addPrequisite(findFlowersQuest).addPrequisite(findRingQuest)
                .plot("perform the ritual").build());
        Task depositFlowersTask = Task.builder().type(TaskType.DEPOSIT_AT).id("DEPOSIT_FLOWERS").objectId(graveLocation.id)
                .result("you place the flowers on the grave").build();
        Task depositRingTask = Task.builder().type(TaskType.DEPOSIT_AT).id("DEPOSIT_RING").objectId(graveLocation.id)
                .result("you return the ring").build();
        Task singSongOfSorrowTask = Task.builder().type(TaskType.DEPOSIT_AT).id("SING_SONG").objectId(graveLocation.id)
                .result("you sing the song of sorrows").build();
        awakenTheGhostQuest.addTask(depositFlowersTask.id);
        awakenTheGhostQuest.addTask(depositRingTask.id);
        awakenTheGhostQuest.addTask(singSongOfSorrowTask.id);
        GameChange removePeacefulGhost = changes.add(GameChange.builder().id("REMOVE_PEACEFUL_GHOST").objectId(ghostPeaceful.id).objectParameter( nowhere.id).build());
        GameChange addHostileGhost = changes.add(GameChange.builder().id("ADD_MENACING_GHOST").objectId(ghostMenace.id).objectParameter( graveLocation.id).build());
        awakenTheGhostQuest.addGameChange(removePeacefulGhost.id);
        awakenTheGhostQuest.addGameChange(addHostileGhost.id);

        Quest settleGhostQuest = quests.add(Quest.builder().id("SETTLE_GHOST").parent(bringPeaceToGhostQuest).addPrequisite(awakenTheGhostQuest)
                .plot("the possessed form of the ghost appeared and you have to destroy it").build());
        Task destroyGhostTask = Task.builder().type(TaskType.FIGHT).id("FIGHT_MENANCING_GHOST").objectId(ghostMenace.id)
                .result("you destroyed the hostile ghost. this brings peace to the soul and the village is saved").build();
        settleGhostQuest.addTask(destroyGhostTask.id);

        Game game = new Game(
                locations.toList(),
                actors.toList(),
                quests.toList(),
                tasks.toList(),
                changes.toList());

        return game;
    }
}
