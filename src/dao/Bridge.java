package dao;

/**
 * This class stores details of the bridges between two continents
 * 
 * @author Ekjot
 *
 */
public class Bridge {
	/**
	 * To store neighboring continent
	 */
	private String neigContinent;
	/**
	 * To store from country
	 */
	private String country1;
	/**
	 * To store To country
	 */
	private String country2;
	
	/**
	 * Constructor to initialize variables
	 * @param neigContinent Neighboring Continent
	 * @param country1 First Country
	 * @param country2 Second Country
	 */
	public Bridge(String neigContinent,String country1,String country2) {
		this.neigContinent = neigContinent;
		this.country1 = country1;
		this.country2 = country2;
	}
	
	public Bridge(Bridge b) {
		this.neigContinent = b.neigContinent;
		this.country1 = b.country1;
		this.country2 = b.country2;
	}
	

	/**
	 * This method returns the neighboring continent
	 * @return Neighboring Continent
	 */
	public String getNeigContinent() {
		return neigContinent;
	}

	/**
	 * This method sets the neighboring continent
	 * @param neigCont Neighboring Continent
	 */
	public void setNeigContinent(String neigCont) {
		this.neigContinent = neigCont;
	}
	
	/**
	 * This method returns the name of first country
	 * @return Country Name
	 */
	public String getCountry1() {
		return country1;
	}

	/**
	 * This method sets the name of first country
	 * @param count1 Country Name
	 */
	public void setCountry1(String count1) {
		this.country1 = count1;
	}

	/**
	 * This method returns the name of the second country
	 * @return Country Name
	 */
	public String getCountry2() {
		return country2;
	}

	/**
	 * This method sets the name of second country
	 * @param count2 Country Name
	 */
	public void setCountry2(String count2) {
		this.country2 = count2;
	}

}
