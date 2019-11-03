package pattern;
/**
 * This is interface which is implemented by observer class
 * @author divya_000
 *
 */
public interface Observer {

	/**
	 * This method updates the observer 
	 * @param obj object of observable object
	 */
	public void update(Observable obj);
}
