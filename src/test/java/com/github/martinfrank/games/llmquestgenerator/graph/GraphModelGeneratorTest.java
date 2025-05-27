package com.github.martinfrank.games.llmquestgenerator.graph;

import com.github.martinfrank.games.llmquestgenerator.json.JsonMapper;
import org.junit.jupiter.api.Test;

class GraphModelGeneratorTest {

    @Test
    void generateTest(){
        GraphGenerator generator = new GraphGenerator();
        Graph graph = generator.generate();
        System.out.println(graph);
        System.out.println(graph.locations().size());

//        for(int i = 0; i < 20; i ++){
//            System.out.println( generator.generate().getNodes().size());
//        }

        String graphJson = JsonMapper.toJson(graph);
        System.out.println(graphJson);
    }

}