import java.util.*;
/**
 * This class is created to represent the Player details
 * @author Piyush
 *
 */
public class Player {
	String name;
	int no_of_armies;
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
	public int getNo_of_armies() {
		return no_of_armies;
	}
	/**
	 * This method sets the no of armies for a player.
	 * @param no_of_armies
	 */
	public void setNo_of_armies(int no_of_armies) {
		this.no_of_armies = no_of_armies;
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
