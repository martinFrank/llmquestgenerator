package com.github.martinfrank.games.llmquestgenerator.aigeneration;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ChatServiceTest {

    @Test
    void testChatService() throws IOException, InterruptedException {
        //given
        Prompt prompt = Prompt.builder()
                .userPrompt("why is the sky blue?")
                .build();

        //when
        String response = ChatService.request(prompt);

        //then:
        System.out.println(response);

    }

}