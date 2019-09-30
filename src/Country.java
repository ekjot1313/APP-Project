import java.util.*;
/**
 * Country class to set and get data members of this class .
 * @author Piyush
 *
 */
public class Country {
	public String name;
	public Player owner;
	public Continent continent_name;
	public int no_of_armies;
	public List<Country> neighbours;
	public String getName() {
		return name;
	}
	/**
	 * This method sets the name of the country.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * This method returns the owner of the country.
	 * @return
	 */
	public Player getOwner() {
		return owner;
	}
	/**
	 * This method sets the owner of the country.
	 * @param owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	/**
	 * This method returns the continent of the country.
	 * @return
	 */
	public Continent getContinent_name() {
		return continent_name;
	}
	/**
	 * This method sets the continent of the country.
	 * @param continent_name
	 */
	public void setContinent_name(Continent continent_name) {
		this.continent_name = continent_name;
	}
	/**
	 *  This method returns the no of armies present in the country.
	 * @return
	 */
	public int getNo_of_armies() {
		return no_of_armies;
	}
	/**
	 * This method sets the no of armies of the country.
	 * @param no_of_armies
	 */
	public void setNo_of_armies(int no_of_armies) {
		this.no_of_armies = no_of_armies;
	}
	/**
	 * This method returns the list of the neighbours of the country.
	 * @return
	 */
	public List<Country> getNeighbours() {
		return neighbours;
	}
	/**
	 *  This method sets the list of the neighbours of the country.
	 * @param neighbours
	 */
	public void setNeighbours(List<Country> neighbours) {
		this.neighbours = neighbours;
	}
	

}
