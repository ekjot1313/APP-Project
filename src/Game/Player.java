package Game;
import java.util.*;
/**
 * This class is created to represent the Player details
 * @author Piyush
 *
 */
public class Player {
	String name;
	int noOfArmies;
	List<Country> assigned_countries;
	/**
	 *  This method returns the name of the player.
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * This method sets the name of the player.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 *  This method returns the no of armies for a player.
	 * @return
	 */
	public int getNoOfArmies() {
		return noOfArmies;
	}
	/**
	 * This method sets the no of armies for a player.
	 * @param noOfArmies
	 */
	public void setNoOfArmies(int noOfArmies) {
		this.noOfArmies = noOfArmies;
	}
	/**
	 * This method returns the list of countries belonging to a player.
	 * @return
	 */
	public List<Country> getAssigned_countries() {
		return assigned_countries;
	}
	/**
	 * This method sets the list of countries belonging to a player.
	 * @param assigned_countries
	 */
	public void setAssigned_countries(List<Country> assigned_countries) {
		this.assigned_countries = assigned_countries;
	}
}
