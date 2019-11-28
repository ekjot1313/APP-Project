package pattern.builder;

import dao.Map;

/**
 * This is the director class for builder pattern
 *
 */
public class Director {

	private GameBuilder gbuilder;

	/**
	 * This method returns the Game Object
	 * 
	 * @return Game Object
	 */
	public Game getGame() {
		return gbuilder.getGameProduct();
	}

	/**
	 * This method is used to set the GameBuilder Object
	 * 
	 * @param gbuilder GameBuilder Object
	 */
	public void setGbuilder(GameBuilder gbuilder) {
		this.gbuilder = gbuilder;
	}

	/**
	 * This method is used to construct the game
	 * 
	 * @param fileName Name of the file
	 * @param map      Map Object
	 * @param player   player
	 * @param phase    Phase
	 */
	public void constructGame(String fileName, Map map, String player, String phase) {
		gbuilder.createNewGame();
		gbuilder.buildMap(fileName, map);
		gbuilder.buildPhase(fileName, phase);
		gbuilder.buildPlayer(fileName, player);
	}
}
