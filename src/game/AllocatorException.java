package game;

/**
 * This class is used for handling exceptions in ArmyAllocator and
 * PlayerAllocator classes
 *
 */
public class AllocatorException extends Exception {
	/**
	 * Constructor
	 * @param message to set message
	 */
	public AllocatorException(String message) {
		super(message);
	}
}
