package pattern.builder;

import dao.Map;

/**
 * This is the product class of builder pattern
 * @author divya_000
 *
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
	 * Method to set the map object
	 * @param map object of Map
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	/**
	 * Method to set the current player
	 * @param currentPlayer
	 */
	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	/**
	 * Method to set the current phase
	 * @param currentPhase
	 */
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}
}
