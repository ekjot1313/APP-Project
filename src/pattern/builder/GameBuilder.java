package pattern.builder;

import dao.Map;

/**
 * Abstract Class Game Builder
 * 
 * @author Mitalee
 */
public abstract class GameBuilder {
	/**
	 * Object of Game
	 */
	protected Game gameProduct;

	/**
	 * This method returns the Game Object
	 * 
	 * @return Game Object
	 */
	public Game getGameProduct() {
		return gameProduct;
	}
	
	/**
	 * This method creates an instance of game
	 */
	public void createNewGame() {
		gameProduct = new Game();
	}
	
	/**
	 * Abstract method to build complex map object
	 * 
	 * @param filename Name of the file
	 * @param map Map Object
	 */
	abstract void buildMap(String filename ,Map map) ;
	
	/**
	 * Abstract method to build player object
	 * 
	 * @param filename Name of the file
	 * @param player Player Object
	 */
	abstract void buildPlayer(String filename ,String player);
	
	/**
	 * Abstract method to build Phase
	 * 
	 * @param filename Name of the file
	 * @param phase Phase
	 */
	abstract void buildPhase(String filename ,String phase);
	
}
