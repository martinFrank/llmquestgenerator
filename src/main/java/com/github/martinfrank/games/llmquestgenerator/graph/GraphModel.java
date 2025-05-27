package com.github.martinfrank.games.llmquestgenerator.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphModel {

    private List<NodeModel> nodeModels = new ArrayList<>();

    public GraphModel() {
    }

    public NodeModel getNode(String name) {
        return nodeModels.stream().filter(n -> n.isName(name)).findFirst().orElse(null);
    }

    public void add(NodeModel nodeModel) {
        nodeModels.add(nodeModel);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "locations=" + nodeModels +
                '}';
    }

    public List<NodeModel> getNodes() {
        return nodeModels;
    }
}
