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
	private String message1;
	private String message2;
	private String mapName;
	private List<Continent> listOfContinent;
	private List<Country> listOfCountries;
	
	public Map() {
		this.listOfContinent = new ArrayList<Continent>();
		this.listOfCountries = new ArrayList<Country>();
		
	}
	
	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public List<Continent> getListOfContinent() {
		return listOfContinent;
	}

	public void setListOfContinent(List<Continent> listOfContinent) {
		this.listOfContinent = listOfContinent;
	}

	public List<Country> getListOfCountries() {
		return listOfCountries;
	}

	public void setListOfCountries(List<Country> listOfCountries) {
		this.listOfCountries = listOfCountries;
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
	
	/*public void updateListOfCountries() {
		this.listOfCountries.clear();
		for(Continent continent:this.listOfContinent) {
			for(Country country:continent.countries) {
				this.listOfCountries.add(country);
			}
		}
	}*/
	
	public Country getCountry(String givenCountryName) {
		
			for(Country country:this.listOfCountries) {
				if(country.getName().equals(givenCountryName)) {
					return country;
				}
			}
		
		return null;
	}
	
	public Continent getContinent(String givenContinentName) {
		for(Continent continent:this.listOfContinent) {
			if(continent.getName().equals(givenContinentName)) {
				return continent;
			}
		}
		return null;}
	



}
