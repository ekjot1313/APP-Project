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
	
	public void constructGame(String fileName, Map map,String player ,String phase) {
		gbuilder.createNewGame();
		gbuilder.buildMap(fileName, map);
		gbuilder.buildPhase(fileName, phase);
		gbuilder.buildPlayer(fileName, player);
	}
}
