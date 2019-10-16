package Game;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is created to represent the Continent details
 * 
 * @author Mitalee Naik
 * @since 1.0.0
 */
public class Continent {
	private String name;
	private int continentValue;
	private List<String> countries;
	private List<Bridge> bridges;
	
	private int continentIndexInListOfContinent;
	
	public Continent(){
		this.countries=new ArrayList<String>();
		this.bridges = new ArrayList<Bridge>();
	}
	/**
	 * 
	 */
	public Continent(Continent c){
		this.name = c.name;
		this.continentValue = c.continentValue;
		this.countries=new ArrayList<String>(c.getCountries());
		this.bridges = new ArrayList<Bridge>();
		for(Bridge b :c.getBridges()) {
			Bridge newBridge = new Bridge(b);
			this.bridges.add(newBridge);
		}
	}
	
/**
	 * @return the bridges
	 */
	public List<Bridge> getBridges() {
		return bridges;
	}

	/**
	 * @param bridges the bridges to set
	 */
	public void setBridges(List<Bridge> bridges) {
		this.bridges = bridges;
	}

/**
	 * @return the continentIndexInListOfContinent
	 */
	public int getContinentIndexInListOfContinent() {
		return continentIndexInListOfContinent;
	}

	/**
	 * @param continentIndexInListOfContinent the continentIndexInListOfContinent to set
	 */
	public void setContinentIndexInListOfContinent(int continentIndexInListOfContinent) {
		this.continentIndexInListOfContinent = continentIndexInListOfContinent;
	}


	
	/**
	 * This method returns the name of the continent
	 * @return name of the country
	 */
	public String getName() {
		return name;
	}
	/**
	 * This method sets the name of the continent
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * This method returns the continent value of the continent
	 * @return continentValue
	 */
	public int getContinentValue() {
		return continentValue;
	}
	/**
	 * This method sets the continent value of the continent
	 * @param continentValue
	 */
	public void setContinentValue(int continentValue) {
		this.continentValue = continentValue;
	}
	/**
	 * This method gets the countries of the continent
	 * @return
	 */
	public List<String> getCountries() {
		return countries;
	}
	/**
	 * This method sets the countries of the continent
	 * @param countries
	 */
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}
	
	
	
}
