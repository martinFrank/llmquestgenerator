package com.github.martinfrank.games.llmquestgenerator.person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonGenerator personGenerator;

    @Autowired
    public PersonController(PersonGenerator personGenerator) {
        this.personGenerator = personGenerator;
    }


    @PostMapping("/generate")
    public ResponseEntity<String> chat(@RequestBody String personDescription) {
        personGenerator.generate(personDescription);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
