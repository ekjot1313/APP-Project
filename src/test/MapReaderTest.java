
package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import mapWorks.MapEditor;
import mapWorks.MapSaver;
import dao.Continent;
import dao.Country;
import dao.Map;
import mapWorks.MapReader;
import dao.Player;
import game.ArmyAllocator;
import game.GamePlay;
import game.PlayerAllocator;

/**
 * Test Class
 * 
 * @author divya_000
 * @author Ekjot
 * 
 */
public class MapReaderTest {

	static Map testMap;
	static MapEditor mapEditor;
	static MapReader mapReader;
	static GamePlay reinforceArmies;
	static PlayerAllocator player;
	static ArmyAllocator armyAlloc;
	static MapSaver mapSaver;
	static Country country;
	static Continent continent;

	/**
	 * This method is used for initialization and set up before running tests
	 */
	@Before
	public void setUp() {
		mapEditor = new MapEditor();
		mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		mapEditor.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
						.split(" "));
		mapEditor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
		testMap = mapEditor.getMap();
		mapReader = new MapReader();
		reinforceArmies = new GamePlay();
		player = new PlayerAllocator();
		Player A = new Player();
		Player B = new Player();
		Player C = new Player();
		player.listOfPlayers.add(A);
		player.listOfPlayers.add(B);
		player.listOfPlayers.add(C);
		armyAlloc = new ArmyAllocator();
		mapSaver = new MapSaver();
		country = new Country();
		continent = new Continent();
	}

	/**
	 * Test method for checking the map is valid or not.
	 */
	@Test
	public void testValidateMap() {

		assertEquals(0, mapReader.validateMap(testMap));

		MapEditor me = new MapEditor();
		me.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		me.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
						.split(" "));
		me.editNeighbor(("editneighbor -add india pakistan -add india congo").split(" "));

		Map tempMap = new Map();
		tempMap = me.getMap();
		assertEquals(1, mapReader.validateMap(tempMap));

	}

	/**
	 * Method to check parsing of the input file
	 */
	@Test
	public void testParseMap() {
		String filename = "ameroki.map";
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\" + filename;
		File newFile = new File(currentPath);
		int test = mapReader.parseMapFile(newFile);
		assertEquals(1, test);
	}

	/**
	 * Method to test the 'gameplayer' command
	 */
	@Test
	public void testGameplayerCommand() {
		PlayerAllocator p = new PlayerAllocator();
		int result = p.validate("gameplayer");
		System.out.println(result);
		assertTrue(result == 0);
		result = p.validate("gameplayer -add--add");
		System.out.println(result);
		assertTrue(result == 0);
		result = p.validate("gameplayer -add A -add B -remove B -remove A -add C");
		System.out.println(result);
		assertTrue(result == 1);
		result = p.validate("populatecountries");
		System.out.println(result);
		assertTrue(result == 1);
	}

	/**
	 * Method to check calculation of number of reinforcement armies for a player
	 */
	@Test
	public void testcalculateReinforceArmies() {
		player.populateCountries(testMap);
		int reinforce = reinforceArmies.calculateReinforceArmies((ArrayList<Player>) player.listOfPlayers, testMap, 1);
		assertEquals(3, reinforce);
	}

	/**
	 * Method to check the number of assigned armies to players
	 */
	@Test
	public void testNumerOfAssignedArmies() {
		player.populateCountries(testMap);
		int result = armyAlloc.calculateTotalArmies((ArrayList<Player>) player.listOfPlayers, testMap, 1);
		assertEquals(35, result);
	}

	/**
	 * Method to test whether the bridge between two continents is removed
	 */
	@Test
	public void testRemoveBridges() {
		assertTrue(mapEditor.removeBridge("asia", "africa", "india", "congo"));
	}

	/**
	 * Method to test whether the continent is valid or not
	 */
	@Test
	public void testValidateContinent() {

		assertEquals(0, mapReader.validateContinent(testMap));
	}

	/**
	 * Method to test whether the map is saved
	 */
	@Test
	public void testMapSaver() {
		try {
			mapSaver.saveMap(testMap, "testMap1");
			// String filename= "ameroki.map";
			String currentPath = System.getProperty("user.dir");
			currentPath += "\\Maps\\" + "testMap1.map";
			File newFile = new File(currentPath);
			assertTrue(newFile.exists());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to check whether there exists duplicate elements in Map (duplicate continents and countries)
	 */
	/**
	 * 
	 */
	@Test
	public void testCheckDuplicates() {
		
		Continent A= new Continent();
		List<Continent> listOfContinent = new ArrayList<Continent>();
		listOfContinent.add(A);
		
		Country Q= new Country();
		Country W= new Country();
		Country E= new Country();
		List<Country> listOfCountry = new ArrayList<Country>();
		listOfCountry.add(Q);
		listOfCountry.add(W);
		listOfCountry.add(E);
				
		for(int i=0;i<listOfCountry.size();i++)
			listOfCountry.get(i).setName("India");
		
		listOfContinent.get(0).setName("Asia");
		
		Map tempMap= new Map();
		tempMap.setListOfContinent(listOfContinent);
		tempMap.setListOfCountries(listOfCountry);
		mapReader.display(tempMap);
		assertEquals(1, mapReader.checkDuplicates(tempMap));
	}
	
	/**
	 * Method to check whether the given country exists or not
	 */
	@Test
	public void testCountryFromName() {

		country = testMap.getCountryFromName("india");
		assertEquals("india", country.getName());
	}

	/**
	 * Method to check whether the given continent exists or not
	 */
	@Test
	public void testContinentFromName() {

		continent = testMap.getContinentFromName("asia");
		assertEquals("asia", continent.getName());
	}
}
