package pattern.builder;

import dao.Map;

/**
 * Abstract Class Game Builder
 * @author Mitalee
 *
 */
public abstract class GameBuilder {
	/**
	 * Object of Game
	 */
	protected Game gameProduct;

	/**
	 * To return game 
	 * @return
	 */
	public Game getGameProduct() {
		return gameProduct;
	}
	
	/**
	 * Creates an instance of game
	 */
	public void createNewGame() {
		gameProduct = new Game();
	}
	
	/**
	 * To build complex map object
	 * @param filename
	 * @param map
	 */
	abstract void buildMap(String filename ,Map map) ;
	/**
	 * To build player
	 * @param filename
	 * @param map
	 */
	abstract void buildPlayer(String filename ,String player);
	/**
	 * To build Phase
	 * @param filename
	 * @param map
	 */
	abstract void buildPhase(String filename ,String phase);
	


}
