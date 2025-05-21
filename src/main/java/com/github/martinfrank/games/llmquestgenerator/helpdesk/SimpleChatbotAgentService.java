
package com.github.martinfrank.games.llmquestgenerator.helpdesk;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SimpleChatbotAgentService {


    private static final Map<String, List<HistoryEntry>> conversationalHistoryStorage = new HashMap<>();

    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatClient;

    public SimpleChatbotAgentService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    public List<HistoryEntry> getConversationalHistoryById(String conversationId) {
        return conversationalHistoryStorage.get(conversationId);
    }

    public String call(String userMessage, String historyId) {
//        var currentHistory = conversationalHistoryStorage.computeIfAbsent(historyId, k -> new ArrayList<>());
//
//        var historyPrompt = new StringBuilder();
//        currentHistory.forEach(entry -> historyPrompt.append(entry.toString()));
//
//        var contextSystemMessage = new SystemMessage(historyPrompt.toString());
//
//        Prompt prompt = new Prompt(List.of(contextSystemMessage));

        var currentPromptMessage = new UserMessage(userMessage);

        Prompt prompt = new Prompt(currentPromptMessage);

        String response = ollamaChatClient.call(prompt)
                .getResult()
                .getOutput()
//                .getContent();
                .getText();
//        var contextHistoryEntry = new HistoryEntry(userMessage, response);
//        currentHistory.add(contextHistoryEntry);

        return response;
    }
}
