package Game;

/**
 * Bridge to store details of bridge between two continents
 * 
 * @author ekjot
 *
 */
public class Bridge {

	public Continent neigCont;

	/**
	 * @return the neigCont
	 */
	public Continent getNeigCont() {
		return neigCont;
	}

	/**
	 * @param neigCont the neigCont to set
	 */
	public void setNeigCont(Continent neigCont) {
		this.neigCont = neigCont;
	}

	public Country getCount1() {
		return count1;
	}

	/**
	 * @param count1 the count1 to set
	 */
	public void setCount1(Country count1) {
		this.count1 = count1;
	}

	/**
	 * @return the count2
	 */
	public Country getCount2() {
		return count2;
	}

	/**
	 * @param count2 the count2 to set
	 */
	public void setCount2(Country count2) {
		this.count2 = count2;
	}

	public Country count1, count2;
	
	public Bridge(Continent neigCont,Country count1,Country count2) {
		this.neigCont=neigCont;
		this.count1=count1;
		this.count2=count2;
	}

}
