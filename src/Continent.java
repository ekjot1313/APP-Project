import java.util.List;

/**
 * This class is created to represent the Continent details
 * 
 * @author Mitalee Naik
 * @since 1.0.0
 */
public class Continent {
	private String name;
	private int continent_value;
	private List<Country> countries;
	
	
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
	public int getContinent_Value() {
		return continent_value;
	}
	/**
	 * This method sets the continent value of the continent
	 * @param continentValue
	 */
	public void setContinent_Value(int continent_value) {
		this.continent_value = continent_value;
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
