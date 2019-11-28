package test.GameEngine;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

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
		
		//invalid commands
		
		//M=1to5 different
		//M=0
		String [] testListOfMapFiles1 = {""};
		String [] testListOfStrategies = {"Benevolent", "Cheater", "Random"};
		int testNumberOfGames = 2;
		int testMaxTurns = 10;

		
		String [][] testOutput = Main.tournamentMode(testListOfMapFiles1, testListOfStrategies, testNumberOfGames, testMaxTurns);
		
		assertNull(testOutput);
		
		//M=6
		String [] testListOfMapFiles2 = {"a","b","c","d","e","f"};
		testOutput = Main.tournamentMode(testListOfMapFiles2, testListOfStrategies, testNumberOfGames, testMaxTurns);
		
		assertNull(testOutput);
		
		//same M
		String [] testListOfMapFiles3 = {"a","b","c","d","a"};
		testOutput = Main.tournamentMode(testListOfMapFiles3, testListOfStrategies, testNumberOfGames, testMaxTurns);
		
		assertNull(testOutput);
		
		//null M
		String [] testListOfMapFiles4 = {"a","b","c","d",""};
		testOutput = Main.tournamentMode(testListOfMapFiles4, testListOfStrategies, testNumberOfGames, testMaxTurns);
		
		assertNull(testOutput);
		
		
		
		
		
		//valid command
		String [] testListOfMapFilesValid = {"TestConquest"};
		String [] testListOfStrategiesValid = {"Benevolent", "Cheater", "Random"};
		int testNumberOfGamesValid = 2;
		int testMaxTurnsValid = 10;

		String [][] outputValid = {{"Cheater", "Cheater"}};
		String [][] testOutputValid = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategiesValid, testNumberOfGamesValid, testMaxTurnsValid);
		
		assertArrayEquals(outputValid, testOutputValid);
		
		
		
		
	}
	
}
