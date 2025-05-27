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



    private final String SUMMARY_JSON = """
            {
              "quest_name": "Whispers in the Woods",
              "start_location": "Village Elder's Hut",
              "quest_giver": "Elder Willow",
              "endboss": "Spirit of the Whispering Woods",
              "quest_summary": "Appease the angered spirit of the forest by completing a ritual.",
              "quest_details": "The villagers are plagued by whispers and visions. They believe an ancient spirit is angered. To appease it, you must find three artifacts from its past: a ceremonial axe, a song scroll, and a locket containing a feather. ",
              "sub_tasks":[
                  {
                      "subtask_description":"Find the Ceremonial Axe",
                      "subtask_location":"Forgotten Ruins",
                      "subtask_object":"Ceremonial Axe",
                      "subtask_person":"",
                      "subtask_type":"search",
                      "clue":{
                          "hint":"The axe was last used in a battle to protect the forest.",
                          "reveal_type":"talk",
                          "source":"Village Historian",
                          "location":"Library"
                      }
                  },
                  {
                      "subtask_description":"Locate the Song Scroll",
                      "subtask_location":"Spirit Grove",
                      "subtask_object":"",
                      "subtask_person":"Keeper of the Grove",
                      "subtask_type":"talk",
                      "clue":{
                          "hint":"The scroll holds a song that can soothe troubled spirits.",
                          "reveal_type":"rumor",
                          "source":"Villager at Tavern",
                          "location":"Tavern"
                      }
                  },
                  {
                      "subtask_description":"Retrieve the Locket with the Feather",
                      "subtask_location":"Ancient Tree Sanctuary",
                      "subtask_object":"Locket with feather",
                      "subtask_person":"",
                      "subtask_type":"search",
                      "clue":{
                          "hint":"The locket belonged to a spirit guide who protected the forest.",
                          "reveal_type":"riddle",
                          "source":"Mysterious Figure in Woods",
                          "location":"Forest Path"
                      }
                  }
              ]
            }
            """;

    private final LocationGenerator locationGenerator;

    @Autowired
    public LocationController(LocationGenerator locationGenerator) {
        this.locationGenerator = locationGenerator;
    }


    @PostMapping("/generate/details")
    public ResponseEntity<String> generateDetails(@RequestBody String location) {
        locationGenerator.generateDetails(location);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateLocations() {
        locationGenerator.generateQuestLocations(SUMMARY_JSON);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
