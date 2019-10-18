package dao;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines Map
 * 
 * @author Ekjot
 *
 */

// pending pic, map, card, prv 

public class Map {
	/**
	 * To store a message
	 */
	private String message1;
	/**
	 * To store a message
	 */
	private String message2;
	/**
	 * To stroe the the map name
	 */
	private String mapName;
	/**
	 * To store the list of continents
	 */
	private List<Continent> listOfContinent;
	/**
	 * To store the list of countries
	 */
	private List<Country> listOfCountries;

	/**
	 * Constructor initializes list of countries and continents
	 */
	public Map() {
		this.listOfContinent = new ArrayList<Continent>();
		this.listOfCountries = new ArrayList<Country>();
	}

	/**
	 * Constructor initializes list of countries and continents 
	 * and then copy values of passed map to local map object
	 * 
	 * @param m Map Object
	 */
	public Map(Map m) {
		listOfContinent = new ArrayList<Continent>();
		listOfCountries = new ArrayList<Country>();
		for (Continent c : m.getListOfContinent()) {
			Continent newc = new Continent(c);
			listOfContinent.add(newc);
		}
		for (Country c : m.getListOfCountries()) {
			Country newc = new Country(c);
			listOfCountries.add(newc);
		}
	}

	/**
	 * This method returns the first message that is displayed in map file
	 * 
	 * @return First Message
	 */
	public String getMessage1() {
		return message1;
	}

	/**
	 * This method sets the first message that is displayed in map file
	 * 
	 * @param message1 First Message
	 */
	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	/**
	 * This method returns the second message that is displayed in map file
	 * 
	 * @return Second Message
	 */
	public String getMessage2() {
		return message2;
	}

	/**
	 * This method sets the second message that is displayed in map file
	 * 
	 * @param message2 Second Message
	 */
	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	/**
	 * This method returns the list of continents
	 * 
	 * @return Continent List
	 */
	public List<Continent> getListOfContinent() {
		return listOfContinent;
	}

	/**
	 * This method sets the list of continents
	 * 
	 * @param listOfContinent Continent List
	 */
	public void setListOfContinent(List<Continent> listOfContinent) {
		this.listOfContinent = listOfContinent;
	}

	/**
	 * This method returns the list of countries
	 * 
	 * @return Country List
	 */
	public List<Country> getListOfCountries() {
		return listOfCountries;
	}

	/**
	 * This method sets the list of countries
	 * 
	 * @param listOfCountries Country List
	 */
	public void setListOfCountries(List<Country> listOfCountries) {
		this.listOfCountries = listOfCountries;
	}

	/**
	 * This method returns the name of the map
	 * 
	 * @return Map Name
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * This method sets the name of the map
	 * 
	 * @param mapName Map Name
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * This method returns the object of a country using the given country name
	 * 
	 * @param givenCountryName Country Name
	 * @return Country Object
	 */
	public Country getCountryFromName(String givenCountryName) {

		for (Country country : this.listOfCountries) {
			if (country.getName().equals(givenCountryName)) {
				return country;
			}
		}

		return null;
	}

	/**
	 * This method returns the object of a continent using the given continent name
	 * 
	 * @param givenContinentName Continent Name
	 * @return Continent Object
	 */
	public Continent getContinentFromName(String givenContinentName) {
		for (Continent continent : this.listOfContinent) {
			if (continent.getName().equals(givenContinentName)) {
				return continent;
			}
		}
		return null;
	}

}
