/**
 * 
 */
package test.pattern.Strategy;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import pattern.Strategy.CheaterStrategy;

/**
 * @author ekjot
 *
 */
public class CheaterStrategyTest {

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
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

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
		A.setStrategy(new CheaterStrategy());
		B.setName("B");
		B.setNoOfArmies(40);
		B.setStrategy(new CheaterStrategy());
		B.getAssigned_countries().add(china);
		listOfPlayers = new ArrayList<Player>();
		listOfPlayers.add(A);
		listOfPlayers.add(B);
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#reinforcement(dao.Map, java.util.ArrayList, dao.Player)}.
	 */
	@Test
	public void testReinforcement() {
		int[] intialArmies = new int[A.getAssigned_countries().size()];

		for (int i = 0; i < A.getAssigned_countries().size(); i++) {
			intialArmies[i] = A.getAssigned_countries().get(i).getNoOfArmies();

		}

		A.executeReinforcement(testMap, listOfPlayers);
		boolean result = true;
		for (int i = 0; i < A.getAssigned_countries().size(); i++) {
			if (A.getAssigned_countries().get(i).getNoOfArmies() != 2 * intialArmies[i]) {
				result = false;
				break;
			}
		}
		assertTrue(result);

	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#attack(dao.Map, java.util.ArrayList, dao.Player)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAttack() throws Exception {
		
		B.getCards().add("test card");
		
		HashSet<String> allNeighbors = new HashSet<String>();
		for (Country country : A.getAssigned_countries()) {
			for (String neighbor : country.getNeighbors()) {
				if (!testMap.getCountryFromName(neighbor).getOwner().equals(A.getName())) {
					// if neighbor country belongs to other player
					allNeighbors.add(neighbor);

				}
			}
		}

		A.executeAttack(testMap, listOfPlayers);
		boolean result = true;

		for (String countryName : allNeighbors) {

			if (!testMap.getCountryFromName(countryName).getOwner().equals(A.getName())) {

				result = false;
				break;
			}
		}

		assertTrue(result);

	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#fortification(dao.Map, java.util.ArrayList, java.lang.String, dao.Player)}.
	 */
	@Test
	public void testFortification() {
		
		HashSet<String> allBoundaries = new HashSet<String>();
		for (Country country : A.getAssigned_countries()) {
			for (String neighbor : country.getNeighbors()) {
				if (!testMap.getCountryFromName(neighbor).getOwner().equals(A.getName())) {
					// if neighbor country belongs to other player
					allBoundaries.add(country.getName());

				}
			}
		}
		
		int[] armies=new int[allBoundaries.size()];
		int i=0;
		for(String country:allBoundaries) {
			armies[i++]=testMap.getCountryFromName(country).getNoOfArmies();
		}
		
		A.executeFortification(testMap, listOfPlayers, "");
		boolean result=true;
		i=0;
		for(String country:allBoundaries) {
			if(testMap.getCountryFromName(country).getNoOfArmies()!=2*armies[i++]) {
				result=false;
				break;
				
			}
		}
		assertTrue(result);
		
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#endGame(java.util.ArrayList)}.
	 * @throws Exception 
	 */
	@Test
	public void testEndGame() throws Exception {
		int i=A.executeAttack(testMap, listOfPlayers);
		assertTrue(i==1);
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#validate(java.lang.String, dao.Map, dao.Player)}.
	 */
	@Test
	public void testValidate() {
		assertTrue(A.getStrategy().validate("", testMap, A)==0);
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#attackMove(java.lang.String, dao.Country, dao.Country, int, dao.Player)}.
	 */
	@Test
	public void testAttackMove() {
		assertTrue(A.getStrategy().attackMove("", new Country(), new Country(), 0, A)==0);
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#attackDeadlock(dao.Map, dao.Player)}.
	 */
	@Test
	public void testAttackDeadlock() {
		assertTrue(A.getStrategy().attackDeadlock(testMap, A)==0);
	}

	/**
	 * Test method for
	 * {@link pattern.Strategy.CheaterStrategy#calculateReinforceArmies(dao.Map, dao.Player)}.
	 */
	@Test
	public void testCalculateReinforceArmies() {
		assertTrue(A.getStrategy().calculateReinforceArmies(testMap, A)==0);
	}

}
