package Game;

import java.util.*;

/**
 * This class represents the Player details
 * @author Piyush
 *
 */
public class Player {
	private String name;
	private int noOfArmies;
	private int unassignedarmies ;
	private List<Country> assigned_countries;
	
	/**
	 * Constructor
	 */
	public Player(){
		assigned_countries = new ArrayList<Country>();
	}

	/**
	 *  This method returns the number of unassigned armies.
	 * @return Number of Unassigned Armies
	 */
	public int getUnassignedarmies() {
		return unassignedarmies;
	}

	/**
	 *  This method sets the number of unassigned armies
	 * @param unassignedarmies Number of unassigned armies
	 */

	public void setUnassignedarmies(int unassignedarmies) {
		this.unassignedarmies = unassignedarmies;
	}

	/**
	 *  This method returns the name of the player.
	 * @return Player Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the player.
	 * @param name Player Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 *  This method returns the number of armies for a player.
	 * @return Number of armies for a player
	 */
	public int getNoOfArmies() {
		return noOfArmies;
	}
	
	/**
	 * This method sets the number of armies for a player.
	 * @param noOfArmies Number of armies of a particular player
	 */
	public void setNoOfArmies(int noOfArmies) {
		this.noOfArmies = noOfArmies;
	}
	
	/**
	 * This method returns the list of countries belonging to a player.
	 * @return List of countries belonging to a player
	 */
	public List<Country> getAssigned_countries() {
		return assigned_countries;
	}
	
	/**
	 * This method sets the list of countries belonging to a player.
	 * @param assigned_countries Countries assigned to a player
	 */
	public void setAssigned_countries(List<Country> assigned_countries) {
		this.assigned_countries = assigned_countries;
	}
}
