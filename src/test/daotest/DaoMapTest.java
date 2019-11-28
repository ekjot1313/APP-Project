/**
 * 
 */
package test.daotest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;

import game.PlayerAllocator;
import mapWorks.MapEditor;

/**
 * Test class to check the Map class
 * @author ekjot
 *
 */
public class DaoMapTest {

	/**
	 * This method is used for initialization and set up before running tests
	 * 
	 * @throws java.lang.Exception exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * Object of Map 
	 */
	private Map testMap;
	/**
	 * Object of MapEditor 
	 */
	private MapEditor mapEditor;
	/**
	 * Object of PlayerAllocator
	 */
	private PlayerAllocator playerAllocator;
	/**
	 * Object of Country
	 */
	private Country country;
	/**
	 * Object of Continent
	 */
	private Continent continent;
	/**
	 * Object of Player
	 */
	private Player player;
	
	/**
	 * Set up method for initializing the objects 
	 * 
	 * @throws java.lang.Exception exception
	 */
	@Before
	public void setUp() throws Exception {

		mapEditor = new MapEditor();
		mapEditor.print = false;
		mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		mapEditor.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
						.split(" "));
		mapEditor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
		testMap = mapEditor.getMap();
		testMap.printFlag = false;

		playerAllocator = new PlayerAllocator();
		Player A = new Player();
		Player B = new Player();
		Player C = new Player();
		playerAllocator.listOfPlayers.add(A);
		playerAllocator.listOfPlayers.add(B);
		playerAllocator.listOfPlayers.add(C);
		playerAllocator.listOfPlayers.get(0).setName("A");
		playerAllocator.listOfPlayers.get(1).setName("B");
		playerAllocator.listOfPlayers.get(2).setName("C");

		testMap.setListOfPlayers(playerAllocator.listOfPlayers);
		country = new Country();
		continent = new Continent();
		player = new Player();

	}

	/**
	 * Test method for {@link dao.Map#getCountryFromName(java.lang.String)}.
	 */
	@Test
	public final void testGetCountryFromName() {
		assertEquals("india", testMap.getCountryFromName("india").getName());
	}

	/**
	 * Test method for {@link dao.Map#getContinentFromName(java.lang.String)}.
	 */
	@Test
	public final void testGetContinentFromName() {
		assertEquals("asia", testMap.getContinentFromName("asia").getName());
	}

	/**
	 * Test method for {@link dao.Map#display()}.
	 */
	@Test
	public final void testDisplay() {
		testMap.printFlag = false;
		testMap.display();

		Map emptyMap = new Map();
		emptyMap.printFlag = false;
		emptyMap.display();

	}

	/**
	 * Test method for {@link dao.Map#validateContinent(dao.Map)}.
	 */
	@Test
	public final void testValidateContinent() {

		assertEquals(0, testMap.validateContinent(testMap));
		mapEditor.editNeighbor(("editneighbor -remove india pakistan").split(" "));

		testMap = mapEditor.getMap();
		testMap.printFlag = false;
		assertEquals(1, testMap.validateContinent(testMap));
	}

	/**
	 * Test method for {@link dao.Map#displayAll()}.
	 */
	@Test
	public final void testDisplayAll() {

		testMap.displayAll();

		Map emptyMap = new Map();
		emptyMap.printFlag = false;
		emptyMap.displayAll();
	}

	/**
	 * Test method for {@link dao.Map#validateMap()}.
	 */
	@Test
	public final void testValidateMap() {

		assertEquals(0, testMap.validateMap());

		mapEditor.editNeighbor(("editneighbor -remove india pakistan").split(" "));

		testMap = mapEditor.getMap();
		testMap.printFlag = false;
		assertEquals(1, testMap.validateMap());

		Continent copyContinent = new Continent();
		copyContinent.setName("asia");
		testMap.addContinent(copyContinent);
		assertEquals(1, testMap.validateMap());
	}

	/**
	 * Test method for {@link dao.Map#isValid(Map)}.
	 */
	@Test
	public final void testIsValid() {

		assertTrue(testMap.isValid(testMap));

		mapEditor.editNeighbor(("editneighbor -remove india pakistan").split(" "));

		testMap = mapEditor.getMap();
		testMap.printFlag = false;
		assertFalse(testMap.isValid(testMap));

		Map emptyMap = new Map();
		emptyMap.printFlag = false;
		assertFalse(emptyMap.isValid(emptyMap));
	}

	/**
	 * Test method for {@link dao.Map#checkDuplicates()}.
	 */
	@Test
	public final void testCheckDuplicates() {
		country.setName("demo");
		testMap.addCountry(country);
		assertEquals(0, testMap.checkDuplicates());

		continent.setName("demo");
		testMap.addContinent(continent);
		assertEquals(0, testMap.checkDuplicates());

		Country copyCountry = new Country();
		copyCountry.setName("demo");
		testMap.addCountry(copyCountry);
		assertEquals(1, testMap.checkDuplicates());

		Continent copyContinent = new Continent();
		copyContinent.setName("demo");
		testMap.addContinent(continent);
		assertEquals(1, testMap.checkDuplicates());

	}

	/**
	 * Test method for {@link dao.Map#addPlayer(dao.Player)}.
	 */
	@Test
	public final void testAddPlayer() {

		player.setName("demo");
		testMap.addPlayer(player);
		assertNotNull(testMap.getPlayerFromName("demo"));
		testMap.removePlayer(player);
		assertNull(testMap.getPlayerFromName("demo"));
	}

	/**
	 * Test method for {@link dao.Map#addContinent(dao.Continent)}.
	 */
	@Test
	public final void testAddContinent() {

		continent.setName("demo");
		testMap.addContinent(continent);
		assertNotNull(testMap.getContinentFromName("demo"));
	}

	/**
	 * Test method for {@link dao.Map#addCountry(dao.Country)}.
	 */
	@Test
	public final void testAddCountry() {

		country.setName("demo");
		testMap.addCountry(country);
		assertNotNull(testMap.getCountryFromName("demo"));
	}

	/**
	 * Test method for {@link dao.Map#removePlayer(dao.Player)}.
	 */
	@Test
	public final void testRemovePlayer() {
		player.setName("demo");
		testMap.addPlayer(player);
		testMap.removePlayer(player);
		assertFalse(testMap.getListOfPlayers().contains(player));
	}

}
