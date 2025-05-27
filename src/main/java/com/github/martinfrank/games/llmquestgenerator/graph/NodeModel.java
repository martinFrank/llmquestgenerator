package com.github.martinfrank.games.llmquestgenerator.graph;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NodeModel {

    private String name;
    private NodeType nodeType = null;
    private final List<NodeModel> connected = new ArrayList<>();

    public NodeModel(String name) {
        this.name = name;
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }

    public void connect(NodeModel nodeModel) {
        if (!connected.contains(nodeModel)) {
            connected.add(nodeModel);
            if (!nodeModel.connected.contains(this)) {
                nodeModel.connected.add(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeModel nodeModel = (NodeModel) o;
        return Objects.equals(name, nodeModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + name + '\'' +
                ", nodeType=" + nodeType +
                ", connected={" + connected.stream().map(n -> n.name).collect(Collectors.joining(",")) +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public List<NodeModel> getConnected() {
        return connected;
    }
}
