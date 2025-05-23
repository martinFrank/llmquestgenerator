package com.github.martinfrank.games.llmquestgenerator.speech;


import com.github.martinfrank.games.llmquestgenerator.quest.QuestGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/speech")
public class SpeechController {


    private final QuestGenerator questGenerator;

    @Autowired
    public SpeechController(QuestGenerator questGenerator) {
        this.questGenerator = questGenerator;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> chat(@RequestBody String text) {
        questGenerator.speak(text);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }


}
