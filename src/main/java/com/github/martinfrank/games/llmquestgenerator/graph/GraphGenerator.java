package com.github.martinfrank.games.llmquestgenerator.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphGenerator {

    private static List<String> NAMES = List.of(
            "Anna", "Ben", "Clara", "David", "Emma", "Felix", "Greta", "Henry", "Isabel",
            "Jonas", "Karla", "Leon", "Mia", "Noah", "Olivia", "Paul", "Quinn", "Ria", "Simon",
            "Tilda", "Uwe", "Vicky", "Wilhelm", "Xenia", "Yannik", "Zoe", "Aylin", "Bruno",
            "Charlotte", "Damian", "Elena", "Finn", "Giulia", "Hannes", "Ida", "Jakob", "Kian",
            "Lara", "Milan", "Nora", "Oskar", "Pia", "Ruben", "Selina", "Tim", "Ulla", "Valentin",
            "Wanda", "Xaver", "Zora");

    private List<String> names = new ArrayList<>();

    public Graph generate() {

        GraphModel graphModel = generateModel();
        return new Graph(graphModel.getNodes().stream()
                .map(nm -> new Node(nm.getName(), nm.getNodeType(),
                        nm.getConnected().stream().map(NodeModel::getName).toList()
                )).toList());
    }

    private List<String> generateNames() {
        List<String> theNames = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                String first = "" + ((char) ('A' + i));
                String second = "" + ((char) ('A' + j));
                theNames.add(first + second);
            }
        }
        return theNames;
    }

    private GraphModel generateModel() {
        names = generateNames();
        GraphModel graph = new GraphModel();

        int horizontalLength = 4; //+1 start
        NodeModel townCenter = new NodeModel(names.removeFirst());
        townCenter.setNodeType(NodeType.TOWN_CENTER);
        graph.add(townCenter);
        addNodes(graph, townCenter, horizontalLength, NodeType.PATH);

        NodeModel middleJunction = graph.getNodes().get(2);
        middleJunction.setNodeType(NodeType.QUEST_JUNCTION);

        NodeModel lastJunction = graph.getNodes().getLast();
        lastJunction.setNodeType(NodeType.QUEST_JUNCTION);

        int numberOfTownLocations = 4 + (int)(Math.random()*3);
        for(int i = 0; i < numberOfTownLocations; i++){
            addNode(graph, townCenter, NodeType.TOWN_ADDON);
        }

        int numberOfSideLocation = 3 + (int)(Math.random()*3);
        for(int i = 0; i < numberOfSideLocation; i++){
            addNode(graph, middleJunction, NodeType.QUEST_AREA);
        }


        int numberOfQuestLocation = 4 + (int)(Math.random()*3);
        for(int i = 0; i < numberOfQuestLocation; i++){
            addNode(graph, lastJunction, NodeType.QUEST_AREA);
        }

        return graph;
    }

    private void addNode(GraphModel graph, NodeModel townCenter, NodeType nodeType) {
        addNodes(graph, townCenter, 1, nodeType);
    }

    private void addNodes(GraphModel graph, NodeModel current, int horizontalLength, NodeType type) {
        for (int i = 0; i < horizontalLength; i++) {
            String name = names.removeFirst();
            NodeModel next = new NodeModel(name);
            next.setNodeType(type);
            graph.add(next);
            next.connect(current);
            current = next;
        }
    }


}
