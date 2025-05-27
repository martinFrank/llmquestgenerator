package com.github.martinfrank.games.llmquestgenerator.graph;

import java.util.List;

public record Node(String id, NodeType type, List<String> connectedIds) {
}
