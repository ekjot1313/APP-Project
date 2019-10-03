package Game;
/**
 * Bridge to store details of bridge between two continents
 * @author ekjot
 *
 */
public class Bridge {
	
	public Continent cont1, cont2;
	/**
	 * @return the cont1
	 */
	public Continent getCont1() {
		return cont1;
	}
	/**
	 * @param cont1 the cont1 to set
	 */
	public void setCont1(Continent cont1) {
		this.cont1 = cont1;
	}
	/**
	 * @return the cont2
	 */
	public Continent getCont2() {
		return cont2;
	}
	/**
	 * @param cont2 the cont2 to set
	 */
	public void setCont2(Continent cont2) {
		this.cont2 = cont2;
	}
	/**
	 * @return the count1
	 */
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
	

}
