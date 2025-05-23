package com.github.martinfrank.games.llmquestgenerator.location;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationGenerator locationGenerator;

    @Autowired
    public LocationController(LocationGenerator locationGenerator) {
        this.locationGenerator = locationGenerator;
    }


    @PostMapping("/generate")
    public ResponseEntity<String> chat(@RequestBody String locationDescription) {
        locationGenerator.generate(locationDescription);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
