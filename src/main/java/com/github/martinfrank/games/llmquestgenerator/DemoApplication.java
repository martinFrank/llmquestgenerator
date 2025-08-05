package com.github.martinfrank.games.llmquestgenerator;

import com.github.martinfrank.games.llmquestgenerator.aigeneration.actor.ActorGenerator;
import com.github.martinfrank.games.llmquestgenerator.aigeneration.location.LocationGenerator;
import com.github.martinfrank.games.llmquestgenerator.game.Game;
import com.github.martinfrank.games.llmquestgenerator.game.SimpleGameGenerator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoApplication {

	private static final Logger LOGGER = LogManager.getLogger(DemoApplication.class);

	public static void main(String[] args) {

		SimpleGameGenerator gameGenerator = new SimpleGameGenerator();
		Game game = gameGenerator.generate();
		System.out.println(new Gson().toJson(game));

		LocationGenerator.generate(game.locations);
		ActorGenerator.generate(game.actors, game.locations);

	}

}
