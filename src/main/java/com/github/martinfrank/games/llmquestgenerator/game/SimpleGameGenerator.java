package com.github.martinfrank.games.llmquestgenerator.game;

import com.github.martinfrank.games.llmquestgenerator.actor.Actor;
import com.github.martinfrank.games.llmquestgenerator.actor.ActorType;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.github.martinfrank.games.llmquestgenerator.location.LocationType;
import com.github.martinfrank.games.llmquestgenerator.quest.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameGenerator {

    List<Location> locations = new ArrayList<>();
    List<Actor> actors = new ArrayList<>();
//    List<Quest> quests = new ArrayList<>();
//    List<Task> tasks = new ArrayList<>();
//    List<GameChange> changes = new ArrayList<>();

    public Game generate() {
        locations.clear();
        actors.clear();
//        AddList<Actor> actors = new AddList<>();
        AddList<Quest> quests = new AddList<>();
        AddList<Task> tasks = new AddList<>();
        AddList<GameChange> changes = new AddList<>();

        String plot = """
                the player visits a small village named ember brooke, where a ghost is haunting the people. the village elder
                asks the player to investigate the reason of trouble. the ghost of the old druid is angry, because his ring
                was stolen from his grave. He will come to peace if the ring is returned and a soothing ritual is performed.
                before he will find peace he will attack the players in a final battle. The ring was stolen from the miner,
                but ridden from regret he disposed the ring deep within the mines.
                """;

        //LOCATIONS
        //start city
        Location marketPlace = addLocation(LocationType.MARKET_PLACE, "MARKET_PLACE", "a small market place with very limited access to good. This is the village center. there is a small fountain in the center and tree surrounding it.");
        Location smithy = addLocation(LocationType.SMITHY, "SMITHY", "This is a small smithy. it provides solid tools and some surprisingly excellent weapons.", marketPlace);
        Location inn = addLocation(LocationType.INN, "INN", "The small inn serves good food and has four rooms to rent. Sometimes bards stop here and play music.", marketPlace);
        Location apothecary = addLocation(LocationType.APOTHECARY, "APOTHECARY", "this shop sells fine herbs and some minor magic artefacts.", marketPlace);
        Location villageElderHouse = addLocation(LocationType.ELDER_HUT, "ELDER_HUT", "this is the small hut from the village elder. it also functions as town hall", marketPlace);
        Location library = addLocation(LocationType.LIBRARY, "LIBRARY", "this small library is part of the village elders hut. Usually it is closed, but opens on request", villageElderHouse);

        //way to quest
        Location field = addLocation(LocationType.FIELDS, "VILLAGE_FIELDS", "behind the village start the fields from the citicens. A path leads towards them to the outback", marketPlace);
        Location crossroad = addLocation(LocationType.MEADOW, "CROSSROAD", "there is a junction here. Two Trees grow here. Sadly no signpost is here.", field);
        Location hamlet = addLocation(LocationType.HAMLET, "HAMLET", "there is a bigger farm, surrounded by crop fields and meadows full of flowers.", crossroad);
        Location quarry = addLocation(LocationType.QUARRY, "QUARRY", "the quarry provides building material for the village. there is a small hut, where the worker lives. Some carts and piles of bricks can be seen.", crossroad);
        Location edgeOfWood = addLocation(LocationType.GROVE, "EDGE_OF_WOOD", "the meadows slowly turn into a forest. it looks beautiful, and you provides cool-off on hot days. It smells like game and mushrooms", crossroad);

        //at the quest
        Location deepForest = addLocation(LocationType.GROVE, "DEEP_FOREST", "the forest is darker here and far more silent than expected. It so dark it makes you feel eerie. There are paths leading even deeper into the forest.", edgeOfWood);
        Location wayToLair = addLocation(LocationType.FOREST, "WAY_TO_LAIR", "this part of the forest is deep and the trees are full of cobwebs. it is surprising that the path is still passable.", deepForest);
        Location lair = addLocation(LocationType.FOREST, "LAIR", "the path leads into a lair full of spiders. These spiders are far bigger than anything you ever saw before. The forrest around this lair grants no other exit than that where you came from.", wayToLair);//side attraction

        Location wayToGrave = addLocation(LocationType.WOODS, "WAY_TO_GRAVE", "this part of the forest is lighter. But it also makes you feel sad, for no reason.", deepForest);
        Location graveLocation = addLocation(LocationType.CLEARING, "GHOSTS_GRAVE", "there is a clearing in the woods. in the clearing is a grave and a tombstone. this place gives you the chills.", wayToGrave);
        Location druidsHutLocation = addLocation(LocationType.CLEARING, "DRUIDS_HUT", "the druids hut in the clearing has been abandoned long time ago and lies in ruins now", graveLocation);

        Location wayToMine = addLocation(LocationType.FOREST, "WAY_TO_MINE", "the forest smells fresh here, but the trees look a bit warped. there are many dead tress", deepForest);
        Location mine = addLocation(LocationType.DUNGEON, "MINE", "this is an abandoned mine in the forest. there are still some carts here. inside is the air dry. it looks like this mine leads deep into the mountain.", wayToMine);
        Location deepMine = addLocation(LocationType.DUNGEON, "DEEP_MINE", "these part of the mines are older than the upper parts. it looks they have been build by ancient miners a very very long time ago. the upper mines must have breached into here. its hot here and you feel scary.", mine);//side attraction

        //ACTORS
        //town citizens
        Actor smith = addActor(ActorType.PERSON, "SMITH", "An exiled dwarf living now among humans. He doesn't like questions about his past. ", smithy);
        Actor innKeeper = addActor(ActorType.PERSON, "INN_KEEPER", "an old woman with a disabled leg. she also cooks and her meals and drinks taste very good. once she also met a king in the very south. she is witty and funny", inn);
        Actor apothecarist = addActor(ActorType.PERSON, "APOTHECARIST", "and elder man who seems to be more interested in nature than in people. know very much about herbs and also has some minor magic abilities.", apothecary);
        Actor villageElder = addActor(ActorType.PERSON, "VILLAGE_ELDER", "the village elder is a quite young man for an elder. he looks slightly troubles, since there are problems in the village. he is a bit corpulent and talks often of his family. he knows how to handle weapons and people", villageElderHouse);
        Actor librarian = addActor(ActorType.PERSON, "LIBRARIAN", "this is a grand dad and works on volunteer base in the library. most time the library is closed, but he has very deep knowledge about history and farming.", library);
        Actor bard = addActor(ActorType.PERSON, "BARD", "a lively young elf woman with a recorder. she knows how to perform and she knows songs that have been long forgotten. her cloth are colorful, her body is fit", inn);

        //citizens outside of town
        Actor farmer = addActor(ActorType.PERSON, "FARMER", "he is the head of a small hamlet where he and his family lives. they produce most of the food for the village and even have a small mill running. he also brews beer.", hamlet);
        Actor miner = addActor(ActorType.PERSON, "MINER", "a strong young man working in the quarry. he does not talk much. he has a small collection of stones, some look like gems.", quarry);

        //quest person
        Actor ghostPeaceful = addActor(ActorType.PERSON, "GHOST_PEACEFUL", "the peaceful version of the ghost of the druid. he can talk to uninvolved people. he is angry because his ring was stolen", graveLocation);
        Actor ghostMenace = addActor(ActorType.PERSON, "GHOST_MENACING", "the ghostly obsession takes over and make the once harmles druid ghost into a meanancing dire ghost with red eyes");

        //QUESTS
        //starter-quest
        Quest talkToVillageElderQuest = quests.add(Quest.builder().id("QUEST_TALK_TO_VILLAGE_ELDER")
                .plot("you have been asked to talk to the village elderly and help him").build());
        Task talkToVillageElderTask = tasks.add(TalkTask.builder().id("TASK_TALK_TO_VILLAGE_ELDER_TO_START").actor(villageElder)
                .mystery("the village is haunted and the actor wants you to solve it").build());
        talkToVillageElderQuest.addTask(talkToVillageElderTask.id);

        //main quest
        Quest mainQuest = quests.add(Quest.builder().id("BRINGING_PEACE_TO_GHOST")
                .plot("people and animals feel sick. nightmares and fear strikes the village. find the reason and remove it.").addPrequisite(talkToVillageElderQuest).build());

        //Teil 1 find reason
        Quest findReasonQuest = quests.add(Quest.builder().id("FIND_REASON").parent(mainQuest)
                .plot("find out what reason causes the terror").build());

        Quest talkToVillagersQuest = quests.add(Quest.builder().id("TALK_TO_VILLAGERS").parent(findReasonQuest)
                .plot("ask around and find a clue").build());
        Task talkToSmithTask = tasks.add(TalkTask.builder().id("TALK_TO_SMITH_ABOUT_GHOST").actor(smith).optional()
                .mystery("knows nothing").build());
        Task talkToInnKeeperTask = tasks.add(TalkTask.builder().id("TALK_TO_INN_KEEPER_ABOUT_GHOST").actor(innKeeper).optional()
                .mystery("knows nothing").build());
        Task talkToLibrarianTask = tasks.add(TalkTask.builder().id("TALK_TO_LIBRARIAN_ABOUT_GHOST").actor(librarian).optional()
                .mystery("knows nothing").build());
        Task talkToApothecaristTask = tasks.add(TalkTask.builder().id("TALK_TO_APOTHECARIST_ABOUT_GHOST").actor(apothecarist).optional()
                .mystery("knows nothing").build());
        Task talkToFarmerTask = tasks.add(TalkTask.builder().id("TALK_TO_FARMER_ABOUT_GHOST").actor(farmer)
                .mystery("saw the ghost in the forrest").build());
        talkToVillagersQuest.addTask(talkToSmithTask.id);
        talkToVillagersQuest.addTask(talkToInnKeeperTask.id);
        talkToVillagersQuest.addTask(talkToLibrarianTask.id);
        talkToVillagersQuest.addTask(talkToApothecaristTask.id);
        talkToVillagersQuest.addTask(talkToFarmerTask.id);

        Quest investigateTheGhostQuest = quests.add(Quest.builder().id("INVESITGATE_THE_GHOST").parent(findReasonQuest)
                .plot("now its clear that there is a ghost - lets investigate the ghost in the forest").build());
        investigateTheGhostQuest.addPrerequisiteQuest(talkToVillagersQuest.id);
        Task searchTheGraveTask = tasks.add(SearchTask.builder().id("SEARCH_THE_GRAVE_TO_INVESTIGATE").location(graveLocation)
                .desiredObject("the peaceful ghost").build());
        Task talkToGhostTask = tasks.add(TalkTask.builder().id("TALK_TO_GHOST_TO_INVESTIGATE").actor(ghostPeaceful)
                .mystery("the ring has been stolen from the ghost and this makes the ghost unrest").build());
        investigateTheGhostQuest.addTask(searchTheGraveTask.id);
        investigateTheGhostQuest.addTask(talkToGhostTask.id);

        Quest learnTheRemedyQuest = quests.add(Quest.builder().id("LEARN_REMEDY").parent(findReasonQuest)
                .plot("you hope that the librarian knows how to settle a ghost").build());
        learnTheRemedyQuest.addPrerequisiteQuest(investigateTheGhostQuest.id);
        Task talkToLibrarianForRemedyTask = tasks.add(TalkTask.builder().id("TALK_TO_LIBRARIAN_FOR_REMEDY").actor(librarian)
                .mystery("to settle the ghost you need to return the ring, plant flowers, and sing the song of sorrows").build());
        learnTheRemedyQuest.addTask(talkToLibrarianForRemedyTask.id);

        //Teil2 - bring peace
        Quest bringPeaceToGhostQuest = quests.add(Quest.builder().id("REMOVE_EVIL").parent(mainQuest).addPrequisite(findReasonQuest)
                .plot("find a way to set the ghost to rest").build());

        Quest findRingStealerQuest = quests.add(Quest.builder().id("FIND_RING_STEALER").parent(bringPeaceToGhostQuest)
                .plot("find out who stole the ring").build());
        Task talkToSmithAboutRingTask = tasks.add(TalkTask.builder().id("TALK_TO_SMITH_ABOUT_RING_STEALER").actor(smith).optional()
                .mystery("the smith didn't take the ring").build());
        Task talkToInnKeeperAboutRingTask = tasks.add(TalkTask.builder().id("TALK_TO_INN_KEEPER_ABOUT_RING_STEALER").actor(innKeeper).optional()
                .mystery("the inn keeper didn't take the ring").build());
        Task talkToApothecaristAboutRingTask = tasks.add(TalkTask.builder().id("TALK_TO_APOTHECARIST_ABOUT_RING_STEALER").actor(apothecarist).optional()
                .mystery("the apothecary didn't take the ring").build());
        Task talkToSmithTaskAboutRingTask = tasks.add(TalkTask.builder().id("TALK_TO_MINER_ABOUT_RING_STEALER").actor(miner)
                .mystery("the miner took the ring, regretted sealing it and hid it into deep mines").build());
        findRingStealerQuest.addTask(talkToSmithAboutRingTask.id);
        findRingStealerQuest.addTask(talkToInnKeeperAboutRingTask.id);
        findRingStealerQuest.addTask(talkToApothecaristAboutRingTask.id);
        findRingStealerQuest.addTask(talkToSmithTaskAboutRingTask.id);

        Quest findRingQuest = quests.add(Quest.builder().id("FIND_RING").parent(findReasonQuest).addPrequisite(findRingStealerQuest)
                .plot("find the ring that causes the ghost to roam").build());
        Task searchForRingTask = tasks.add(SearchTask.builder().id("SEARCH_RING_IN_MINES").location(deepMine)
                .desiredObject("the gold ring with a red pearl from the unrest ghost").build());
        findRingQuest.addTask(searchForRingTask.id);

        Quest findFlowersQuest = Quest.builder().id("FIND_FLOWERS").parent(bringPeaceToGhostQuest)
                .plot("find flowers for the ritual").build();
        Task searchForFlowersTask = tasks.add(SearchTask.builder().id("SEARCH_FLOWERS_ON_FIELD").location(hamlet)
                .desiredObject("the blue flowers").build());
        findFlowersQuest.addTask(searchForFlowersTask.id);

        Quest findSongOfSorrowsQuest = Quest.builder().id("FIND_SONG_OF_SORROWS").parent(bringPeaceToGhostQuest)
                .plot("find song for the ritual").build();
        Task searchForSongOfSorrowTask = tasks.add(TalkTask.builder().id("FIND_SONG_OF_SORROW_IN_INN").actor(bard)
                .mystery("the ancient elfen song of sorrows").build());
        findSongOfSorrowsQuest.addTask(searchForSongOfSorrowTask.id);

        Quest awakenTheGhostQuest = quests.add(Quest.builder().id("AWAKEN_GHOST").parent(bringPeaceToGhostQuest)
                .addPrequisite(findSongOfSorrowsQuest).addPrequisite(findFlowersQuest).addPrequisite(findRingQuest)
                .plot("perform the ritual").build());
        Task depositFlowersTask = tasks.add(ApplyTask.builder().id("DEPOSIT_FLOWERS_TO_AWAKEN_GHOST").location(graveLocation)
                .applyAction("you place the flowers on the grave").build());
        Task depositRingTask = tasks.add(ApplyTask.builder().id("DEPOSIT_RING_TO_AWAKEN_GHOST").location(graveLocation)
                .applyAction("you return the ring to the grave").build());
        Task singSongOfSorrowTask = tasks.add(ApplyTask.builder().id("SING_SONG_TO_AWAKEN_GHOST").location(graveLocation)
                .applyAction("you sing the song of sorrows in front of the grave").build());
        awakenTheGhostQuest.addTask(depositFlowersTask.id);
        awakenTheGhostQuest.addTask(depositRingTask.id);
        awakenTheGhostQuest.addTask(singSongOfSorrowTask.id);
        GameChange removePeacefulGhost = changes.add(GameChange.builder().id("REMOVE_PEACEFUL_GHOST").objectId(ghostPeaceful.id).objectParameter(null).build());
        GameChange addHostileGhost = changes.add(GameChange.builder().id("ADD_MENACING_GHOST").objectId(ghostMenace.id).objectParameter(graveLocation.id).build());
        awakenTheGhostQuest.addGameChange(removePeacefulGhost.id);
        awakenTheGhostQuest.addGameChange(addHostileGhost.id);

        Quest settleGhostQuest = quests.add(Quest.builder().id("SETTLE_GHOST").parent(bringPeaceToGhostQuest).addPrequisite(awakenTheGhostQuest)
                .plot("the possessed form of the ghost appeared and you have to destroy it").build());
        Task destroyGhostTask = tasks.add(FightTask.builder().id("FIGHT_MENACING_GHOST").actor(ghostMenace).location(graveLocation)
                .result("you destroyed the hostile ghost. this brings peace to the soul and the village is saved").build());
        settleGhostQuest.addTask(destroyGhostTask.id);

        Game game = new Game(plot,
                locations,
                actors,
                quests.toList(),
                tasks.toList(),
                changes.toList());

        return game;
    }

    private Actor addActor(ActorType type, String id, String description, Location location) {
        String locationId = location == null ? null : location.id;
        Actor actor = new Actor(type, id, description, locationId);
        this.actors.add(actor);
        return actor;
    }

    private Actor addActor(ActorType type, String id, String description) {
        return addActor(type, id, description, null);
    }

    private Location addLocation(LocationType type, String id, String description, Location connectTo) {
        Location location = new Location(type, id, description);
        locations.add(location);
        if (connectTo != null) {
            location.connect(connectTo);
        }
        return location;
    }

    private Location addLocation(LocationType type, String id, String description) {
        return addLocation(type, id, description, null);
    }
}
