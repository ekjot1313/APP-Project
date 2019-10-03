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
	public List<Country> countries;
	
public Continent(){
	this.countries=new ArrayList<Country>();
	
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
	public List<Country> getCountries() {
		return countries;
	}
	/**
	 * This method sets the countries of the continent
	 * @param countries
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	
	
}
