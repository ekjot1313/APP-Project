package pattern.builder;

import dao.Map;

public class Director {
	
	private GameBuilder gbuilder;

	public GameBuilder getGbuilder() {
		return gbuilder;
	}

	public void setGbuilder(GameBuilder gbuilder) {
		this.gbuilder = gbuilder;
	}
	
	public void constructGame(String fileName, Map map) {
		gbuilder.createNewGame();
		gbuilder.buildMap(fileName, map);
		gbuilder.buildPhase(fileName, map);
		gbuilder.buildPlayer(fileName, map);
	}
}
