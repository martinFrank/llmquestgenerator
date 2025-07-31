package com.github.martinfrank.games.llmquestgenerator;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.LocationGenerator;
import com.github.martinfrank.games.llmquestgenerator.game.Game;
import com.github.martinfrank.games.llmquestgenerator.game.SimpleGameGenerator;
import com.github.martinfrank.games.llmquestgenerator.location.Location;
import com.google.gson.Gson;

import java.util.List;

public class DemoApplication {

	public static void main(String[] args) {
		SimpleGameGenerator gameGenerator = new SimpleGameGenerator();
		Game game = gameGenerator.generate();
		System.out.println(new Gson().toJson(game));

		LocationGenerator locationGenerator = new LocationGenerator();
//		List<Location> shortList = List.of(game.locations.get(2));
//		List<Location> generatedLocations = locationGenerator.generate(shortList);
		List<Location> generatedLocations = locationGenerator.generate(game.locations);
		for(Location location : generatedLocations){
			System.out.println(location.type.toString()+" "+location.id);
			System.out.println(location.getDetails());
		}

	}

}
