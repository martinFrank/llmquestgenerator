package com.github.martinfrank.games.llmquestgenerator.quest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quest")
public class QuestController {

//    private final QuestGeneratorChatbotAgentService questGeneratorChatbotAgentService;
//
//    @Autowired
//    public QuestController(QuestGeneratorChatbotAgentService questGeneratorChatbotAgentService) {
//        this.questGeneratorChatbotAgentService = questGeneratorChatbotAgentService;
//    }

    private final QuestGenerator questGenerator;

    @Autowired
    public QuestController(QuestGenerator questGenerator) {
        this.questGenerator = questGenerator;
    }

//    @PostMapping("/generate/action")
//    public ResponseEntity<String> generateAction(@RequestBody HelpDeskRequest helpDeskRequest) {
//        var chatResponse = questGeneratorChatbotAgentService.call(helpDeskRequest.getPromptMessage(), helpDeskRequest.getHistoryId());
//        var stripped = chatResponse.replace("\n", "").replace("`", "");
//        System.out.println(stripped);
////        QuestAction questAction = new Gson().fromJson(stripped, QuestAction.class);
//
//        return new ResponseEntity<>(stripped, HttpStatus.OK);
//    }

//    @PostMapping("/generate")
//    public ResponseEntity<QuestAction> chat(@RequestBody QuestRequest questRequest) {
//        var quest = strip(questGeneratorChatbotAgentService.requestQuest(questRequest.promptMessage));
//        System.out.println(quest);
//        QuestSummary questSummary = new Gson().fromJson(quest, QuestSummary.class);
//
//        var tasks = strip(questGeneratorChatbotAgentService.requestTasks(questSummary.quest.task+". the reason for the task is "+questSummary.quest.endboss));
//        System.out.println(tasks);
//        QuestAction questAction = new Gson().fromJson(tasks, QuestAction.class);
//
//        return new ResponseEntity<>(questAction, HttpStatus.OK);
//    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateQuestSummary(@RequestBody String questIdea) {
        String result = questGenerator.generate(questIdea);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
