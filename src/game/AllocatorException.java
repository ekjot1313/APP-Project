package game;

/**
 * This class is used for handling exceptions in ArmyAllocator and
 * PlayerAllocator classes
 *
 */
public class AllocatorException extends Exception {

	public AllocatorException(String message) {
		super(message);
	}
}
