package com.github.martinfrank.games.llmquestgenerator.helpdesk;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/helpdesk")
public class HelpDeskController {
    private final HelpDeskChatbotAgentService helpDeskChatbotAgentService;
    private final SimpleChatbotAgentService simpleChatbotAgentService;

    public HelpDeskController(HelpDeskChatbotAgentService helpDeskChatbotAgentService, SimpleChatbotAgentService simpleChatbotAgentService) {
        this.helpDeskChatbotAgentService = helpDeskChatbotAgentService;
        this.simpleChatbotAgentService = simpleChatbotAgentService;
    }

    @PostMapping("/chat")
    public ResponseEntity<HelpDeskResponse> chat(@RequestBody HelpDeskRequest helpDeskRequest) {
        var chatResponse = helpDeskChatbotAgentService.call(helpDeskRequest.getPromptMessage(), helpDeskRequest.getHistoryId());
        return new ResponseEntity<>(new HelpDeskResponse(chatResponse), HttpStatus.OK);
    }

    @PostMapping("/simple-chat")
    public ResponseEntity<String> chat2(@RequestBody HelpDeskRequest helpDeskRequest) {
        String chatResponse = simpleChatbotAgentService.call(helpDeskRequest.getPromptMessage(), helpDeskRequest.getHistoryId());
        return new ResponseEntity<>(chatResponse, HttpStatus.OK);
    }
}
