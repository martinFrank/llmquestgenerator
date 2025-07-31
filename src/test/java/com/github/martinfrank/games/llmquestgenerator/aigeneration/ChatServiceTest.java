package com.github.martinfrank.games.llmquestgenerator.aigeneration;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ChatServiceTest {

    @Test
    void testChatService() throws IOException, InterruptedException {
        //given
        Prompt prompt = new Prompt("warum ist der himmel blau?");
        ChatService chatService = new ChatService();

        //when
        String response = chatService.request(prompt);

        //then:
        System.out.println(response);

    }

}