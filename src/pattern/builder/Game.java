package pattern.builder;

import dao.Map;

/**
 * This is the product class of builder pattern
 * 
 * @author divya_000
 */
public class Game {

	/**
	 * Object of Map class
	 */
	private Map map;

	/**
	 * To store the current player
	 */
	private String currentPlayer;

	/**
	 * To store the current phase
	 */
	private String currentPhase;
	
	/**
	 * This method is used to set the map object
	 * 
	 * @param map object of Map
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	/**
	 * 
	 * This method is used to set the current player
	 * 
	 * @param currentPlayer Current Player
	 */
	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * 
	 * This method is used to set the current phase
	 * 
	 * @param currentPhase Current Phase
	 */
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
	
	/**
	 * This method returns the map object
	 * 
	 * @return Map Object
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * This method returns the current player
	 * 
	 * @return Current Player
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * This method returns the current phase
	 * 
	 * @return Current Phase
	 */
	public String getCurrentPhase() {
		return currentPhase;
	}
	
	
}
