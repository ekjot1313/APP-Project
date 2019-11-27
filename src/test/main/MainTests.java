package test.main;

import static org.junit.Assert.assertArrayEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import GameEngine.Main;

/**
 * This class tests the functions in Main.java class
 * 
 * @author divya_000
 *
 */
public class MainTests {

	static Main m;
	
	/**
	 * This method is used for initialization and set up before running tests
	 */
	@BeforeClass
	public static void beforeClass() {
		m = new Main();
	}
	
	/**
	 * Method to test String[][] tournamentMode(String[] listOfMapFiles, String[] listOfPlayerStrategies, int numberOfGames, int maxTurns)
	 * @throws Exception 
	 */
	@Test
	public void testTournamentMode() throws Exception {
		
		String [] testListOfMapFiles = {"TestConquest"};
		String [] testListOfStrategies = {"Benevolent", "Cheater", "Random"};
		int testNumberOfGames = 2;
		int testMaxTurns = 10;

		String [][] output = {{"Cheater", "Cheater"}};
		String [][] testOutput = m.tournamentMode(testListOfMapFiles, testListOfStrategies, testNumberOfGames, testMaxTurns);
		
		assertArrayEquals(output, testOutput);
	}
	
}
