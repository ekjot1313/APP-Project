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
	public Map(Map m) {
		listOfContinent = new ArrayList<Continent>();
		listOfCountries = new ArrayList<Country>();
		for(Continent c :m.getListOfContinent()) {
			Continent newc = new Continent(c);
			listOfContinent.add(newc);
		}
		for(Country c :m.getListOfCountries()) {
			Country newc = new Country(c);
			listOfCountries.add(newc);
		}
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
	
	public Country getCountryFromName(String givenCountryName) {
		
			for(Country country:this.listOfCountries) {
				if(country.getName().equals(givenCountryName)) {
					return country;
				}
			}
		
		return null;
	}
	
	public Continent getContinentFromName(String givenContinentName) {
		for(Continent continent:this.listOfContinent) {
			if(continent.getName().equals(givenContinentName)) {
				return continent;
			}
		}
		return null;}
	
	
	
	



}
