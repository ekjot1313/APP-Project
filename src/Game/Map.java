package Game;
import java.util.ArrayList;
import java.util.List;

/**
 * class to define map
 * 
 * @author ekjot
 *
 */

// pending pic, map, card, prv 

public class Map {
	public String message;
	public String mapName;
	public List<Continent> listOfContinent;
	public List<Country> listOfCountries;
	
	public Map() {
		this.listOfContinent=new ArrayList<Continent>();
		this.listOfCountries=new ArrayList<Country>();
	}

	/**
	 * @return the countries
	 */
	public List<Country> getCountries() {
		return listOfCountries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<Country> countries) {
		this.listOfCountries = countries;
	}

	/**
	 * @return the continents
	 */
	public List<Continent> getContinents() {
		return listOfContinent;
	}

	/**
	 * @param continents the continents to set
	 */
	public void setContinents(List<Continent> continents) {
		this.listOfContinent = continents;
	}

	/**
	 * @return the mapName
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * @param mapName the mapName to set
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
