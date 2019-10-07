package Game;

/**
 * Bridge to store details of bridge between two continents
 * 
 * @author ekjot
 *
 */
public class Bridge {

	private String neigContinent;
	private String country1;
	private String country2;
	
	public Bridge(String neigContinent,String country1,String country2) {
		this.neigContinent=neigContinent;
		this.country1=country1;
		this.country2=country2;
	}
	
	

	/**
	 * @return the neigCont
	 */
	public String getNeigContinent() {
		return neigContinent;
	}

	/**
	 * @param neigCont the neigCont to set
	 */
	public void setNeigContinent(String neigCont) {
		this.neigContinent = neigCont;
	}

	public String getCountry1() {
		return country1;
	}

	/**
	 * @param count1 the count1 to set
	 */
	public void setCountry1(String count1) {
		this.country1 = count1;
	}

	/**
	 * @return the count2
	 */
	public String getCountry2() {
		return country2;
	}

	/**
	 * @param count2 the count2 to set
	 */
	public void setCountry2(String count2) {
		this.country2 = count2;
	}

	
	
	

}
