/**
 * @author ekjot
 */
package test.pattern.Strategy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import pattern.Strategy.RandomStrategy;

/**
 * @author ekjot
 *
 */
public class RandomStrategyTest {

	/**
	 * Object of Map
	 */
	Map testMap;

	Continent asia;
	/**
	 * Object of Country india
	 */
	Country india;
	/**
	 * Object of Country pakistan
	 */
	Country pakistan;
	/**
	 * Object of Country china
	 */
	Country china;
	/**
	 * Object of Player A
	 */
	Player A;
	/**
	 * Object of Player B
	 */
	Player B;
	/**
	 * ArrayList to store the list of players
	 */
	ArrayList<Player> listOfPlayers;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		testMap = new Map();
		asia = new Continent();
		india = new Country();
		pakistan = new Country();
		china = new Country();
		A = new Player();
		B = new Player();

		india.setName("india");
		india.setContinentName("asia");
		india.setNoOfArmies(20);
		india.setOwner("A");
		india.getNeighbors().add("pakistan");
		india.getNeighbors().add("china");

		pakistan.setName("pakistan");
		pakistan.setContinentName("asia");
		pakistan.setNoOfArmies(20);
		pakistan.setOwner("A");
		pakistan.getNeighbors().add("india");
		// pakistan.getNeighbors().add(china);
		china.setName("china");
		china.setContinentName("asia");
		china.setNoOfArmies(40);
		china.setOwner("B");
		china.getNeighbors().add("india");
		testMap.getListOfCountries().add(india);
		testMap.getListOfCountries().add(pakistan);
		testMap.getListOfCountries().add(china);
		asia = new Continent();
		testMap.getListOfContinent().add(asia);

		A.setName("A");
		A.setNoOfArmies(40);
		A.getAssigned_countries().add(india);
		A.getAssigned_countries().add(pakistan);
		A.setStrategy(new RandomStrategy());
		B.setName("B");
		B.setNoOfArmies(40);
		B.setStrategy(new RandomStrategy());
		B.getAssigned_countries().add(china);
		listOfPlayers = new ArrayList<Player>();
		listOfPlayers.add(A);
		listOfPlayers.add(B);

	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#reinforcement(dao.Map, java.util.ArrayList, dao.Player)}.
	 */
	@Test
	public void testReinforcement() {

		// simple test without card exchange
		int initialArmies = 0;

		for (Country country : A.getAssigned_countries()) {

			initialArmies += country.getNoOfArmies();
		}

		A.executeReinforcement(testMap, listOfPlayers);

		int reinforcedArmies = 0;
		for (Country country : A.getAssigned_countries()) {
			reinforcedArmies += country.getNoOfArmies();
		}
		reinforcedArmies -= initialArmies;
		assertTrue(reinforcedArmies == 3);

		// test with 3 same card types
		initialArmies = 0;

		for (Country country : A.getAssigned_countries()) {

			initialArmies += country.getNoOfArmies();
		}

		Player.deck = new ArrayList<String>();

		A.getCards().add("Test1 infantry");
		A.getCards().add("Test2 infantry");
		A.getCards().add("Test3 infantry");

		A.executeReinforcement(testMap, listOfPlayers);

		reinforcedArmies = 0;
		for (Country country : A.getAssigned_countries()) {
			reinforcedArmies += country.getNoOfArmies();
		}
		reinforcedArmies -= initialArmies;
		System.out.println(reinforcedArmies + " 222222222222222222222222222222");
		assertTrue(reinforcedArmies == 8 || reinforcedArmies == 13);

		// test with 3 different card types

		initialArmies = 0;

		for (Country country : A.getAssigned_countries()) {

			initialArmies += country.getNoOfArmies();
		}

		Player.deck = new ArrayList<String>();
		// test with 3 same cards
		A.getCards().add("Test1 infantry");
		A.getCards().add("Test2 cavalry");
		A.getCards().add("Test3 artillery");

		A.executeReinforcement(testMap, listOfPlayers);

		reinforcedArmies = 0;
		for (Country country : A.getAssigned_countries()) {
			reinforcedArmies += country.getNoOfArmies();
		}
		reinforcedArmies -= initialArmies;
		System.out.println(reinforcedArmies + " 333333333333333333333333");
		assertTrue(reinforcedArmies == 18 || reinforcedArmies == 13);

	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#attack(dao.Map, java.util.ArrayList, dao.Player)}.
	 */
	@Test
	public void testAttack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#fortification(dao.Map, java.util.ArrayList, java.lang.String, dao.Player)}.
	 */
	@Test
	public void testFortification() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#calculateReinforceArmies(dao.Map, dao.Player)}.
	 */
	@Test
	public void testCalculateReinforceArmies() {
		// based on only countries
		int armies = A.getStrategy().calculateReinforceArmies(testMap, A);

		assertTrue(armies == 3);

		// including continent
		asia.setOwner(A.getName());
		asia.setContinentValue(10);
		armies = A.getStrategy().calculateReinforceArmies(testMap, A);

		assertTrue(armies == 13);

		// including 5 different cards
		Player.deck = new ArrayList<String>();
		A.getCards().add("Test1 infantry");
		A.getCards().add("Test2 cavalry");
		A.getCards().add("Test3 artillery");
		A.getCards().add("Test4 infantry");
		A.getCards().add("Test5 cavalry");
		armies = A.getStrategy().calculateReinforceArmies(testMap, A);
		System.out.println(armies + "...................");

	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#attackMove(java.lang.String, dao.Country, dao.Country, dao.Player)}.
	 */
	@Test
	public void testAttackMoveStringCountryCountryPlayer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#attackDeadlock(dao.Map, dao.Player)}.
	 */
	@Test
	public void testAttackDeadlock() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#endGame(java.util.ArrayList)}.
	 */
	@Test
	public void testEndGame() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#validate(java.lang.String, dao.Map, dao.Player)}.
	 */
	@Test
	public void testValidate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.RandomStrategy#attackMove(java.lang.String, dao.Country, dao.Country, int, dao.Player)}.
	 */
	@Test
	public void testAttackMoveStringCountryCountryIntPlayer() {
		fail("Not yet implemented");
	}

}
