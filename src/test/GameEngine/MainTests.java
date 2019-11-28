package test.GameEngine;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import GameEngine.Main;

/**
 * This class tests the functions in Main class
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
	 * Method to test String[][] tournamentMode(String[] listOfMapFiles, String[]
	 * listOfPlayerStrategies, int numberOfGames, int maxTurns)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTournamentMode() throws Exception {

		// invalid commands

		// M=1to5 different
		// M=0
		String[] testListOfMapFiles1 = { "" };
		String[] testListOfStrategies = { "Benevolent", "Cheater", "Random" };
		int testNumberOfGames = 2;
		int testMaxTurns = 10;

		String[][] testOutput = Main.tournamentMode(testListOfMapFiles1, testListOfStrategies, testNumberOfGames,
				testMaxTurns);

		assertNull(testOutput);

		// M=6
		String[] testListOfMapFiles2 = { "a", "b", "c", "d", "e", "f" };
		testOutput = Main.tournamentMode(testListOfMapFiles2, testListOfStrategies, testNumberOfGames, testMaxTurns);

		assertNull(testOutput);

		// same M
		String[] testListOfMapFiles3 = { "a", "b", "c", "d", "a" };
		testOutput = Main.tournamentMode(testListOfMapFiles3, testListOfStrategies, testNumberOfGames, testMaxTurns);

		assertNull(testOutput);

		// null M
		String[] testListOfMapFiles4 = { "a", "b", "c", "d", "" };
		testOutput = Main.tournamentMode(testListOfMapFiles4, testListOfStrategies, testNumberOfGames, testMaxTurns);

		assertNull(testOutput);

		// invalid file in M
		String[] testListOfMapFiles5 = { "Given_file" };
		testOutput = Main.tournamentMode(testListOfMapFiles5, testListOfStrategies, testNumberOfGames, testMaxTurns);

		assertNull(testOutput);

		String[] testListOfMapFilesValid = { "TestConquest" };

		// P = 2 to 5, different, computer
		// P<2
		String[] testListOfStrategies1 = { "Benevolent" };
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategies1, testNumberOfGames,
				testMaxTurns);

		assertNull(testOutput);
		// P>4
		String[] testListOfStrategies2 = { "Benevolent", "Random", "Aggressive", "Cheater", "Random", "Random" };
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategies2, testNumberOfGames,
				testMaxTurns);

		assertNull(testOutput);
		// same P
		String[] testListOfStrategies3 = { "Benevolent", "Benevolent" };
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategies3, testNumberOfGames,
				testMaxTurns);

		assertNull(testOutput);
		// null P
		String[] testListOfStrategies4 = { "Benevolent", "" };

		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategies4, testNumberOfGames,
				testMaxTurns);

		assertNull(testOutput);
		// invalid P
		String[] testListOfStrategies5 = { "Benevolent", "Bene" };
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategies5, testNumberOfGames,
				testMaxTurns);

		assertNull(testOutput);

		String[] testListOfStrategiesValid = { "Benevolent", "Cheater", "Random" };

		// G=1to5
		// G<1
		int testNumberOfGames1 = 0;
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategiesValid, testNumberOfGames1,
				testMaxTurns);

		assertNull(testOutput);
		// G>5
		int testNumberOfGames2 = 6;
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategiesValid, testNumberOfGames2,
				testMaxTurns);

		assertNull(testOutput);

		int testNumberOfGamesValid = 2;

		// D=10to50
		// D<10
		int testMaxTurns1 = 0;
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategiesValid, testNumberOfGamesValid,
				testMaxTurns1);

		assertNull(testOutput);
		// D>50
		int testMaxTurns2 = 100;
		testOutput = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategiesValid, testNumberOfGamesValid,
				testMaxTurns2);

		assertNull(testOutput);

		int testMaxTurnsValid = 10;

		// valid command
		String[][] outputValid = { { "Cheater", "Cheater" } };
		String[][] testOutputValid = Main.tournamentMode(testListOfMapFilesValid, testListOfStrategiesValid,
				testNumberOfGamesValid, testMaxTurnsValid);

		assertArrayEquals(outputValid, testOutputValid);

	}

}
