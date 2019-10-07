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

	/**
	 * @return the neigCont
	 */
	public String getNeigCont() {
		return neigContinent;
	}

	/**
	 * @param neigCont the neigCont to set
	 */
	public void setNeigCont(String neigCont) {
		this.neigContinent = neigCont;
	}

	public String getCount1() {
		return country1;
	}

	/**
	 * @param count1 the count1 to set
	 */
	public void setCount1(String count1) {
		this.country1 = count1;
	}

	/**
	 * @return the count2
	 */
	public String getCount2() {
		return country2;
	}

	/**
	 * @param count2 the count2 to set
	 */
	public void setCount2(String count2) {
		this.country2 = count2;
	}

	
	
	public Bridge(String neigCont,String count1,String count2) {
		this.neigContinent=neigCont;
		this.country1=count1;
		this.country2=count2;
	}

}
