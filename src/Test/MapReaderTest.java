/**
 * 
 */
package Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Game.Map;
import Game.MapReader;
import Game.Player;
import mapWorks.MapEditor;
import mapWorks.MapSaver;
import Game.ArmyAllocator;
import Game.GamePlay;
import Game.PlayerAllocator;

/**
 * @author divya_000
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
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
	mapEditor= new MapEditor();
	mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
	mapEditor.editCountry(("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa").split(" "));
	mapEditor.editNeighbor(("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
	testMap= mapEditor.getMap();
	mapReader= new MapReader();
	reinforceArmies = new GamePlay();
	player= new PlayerAllocator();
	Player A= new Player();
	Player B= new Player();
	Player C= new Player();
	player.listOfPlayers.add(A);
	player.listOfPlayers.add(B);
	player.listOfPlayers.add(C);
	armyAlloc= new ArmyAllocator();
	mapSaver = new MapSaver();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link Game.MapReader#validateMap()}.
	 */
	@Test
	public void testValidateMap() {
	
		assertEquals(0,mapReader.validateMap(testMap));
		
	}
	
	/**
	 * Method to check parsing of the input file
	 */
	@Test
	public void testParseMap() {
		String filename= "ameroki.map";
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\" + filename;
		File newFile = new File(currentPath);
		int test=mapReader.parseMapFile(newFile);
		
		assertEquals(1, test);
		
	}
	/**
	 * Function to test the gameplayer command
	 */
	@Test
	public void testGameplayerCommand() {
		PlayerAllocator p=new PlayerAllocator();
		int result=p.validate("gameplayer");
		System.out.println(result);
		assertTrue(result == 0);
		result=p.validate("gameplayer -add--add");
		System.out.println(result);
		assertTrue(result == 0);
		result=p.validate("gameplayer -add A -add B -remove B -remove A -add C");
		System.out.println(result);
		assertTrue(result==1);
		result=p.validate("populatecountries");
		System.out.println(result);
		assertTrue(result==1);	
	}
	
	/*
	 * method to check calculation of number of reinforcement armies for a player
	 */
	@Test
	public void testcalculateReinforceArmies() {
		
		player.populateCountries(testMap);
		int reinforce= reinforceArmies.calculateReinforceArmies((ArrayList<Player>) player.listOfPlayers, testMap, 1);
		assertEquals(3, reinforce);
	}
	
	/*
	 * method to check the number of assigned armies to players
	 */
	@Test
	public void testNumerOfAssignedArmies() {
		player.populateCountries(testMap);
		int result= armyAlloc.calculateTotalArmies((ArrayList<Player>) player.listOfPlayers, testMap,1 );
		assertEquals(35, result);
	}
	
	/**
	 * method to test whether the bridge between two continents is removed
	 */
	@Test
	public void testRemoveBridges() {
		assertTrue(mapEditor.removeBridge("asia", "africa", "india", "congo"));
	}
	
	@Test
	public void testValidateContinent() {
		
		assertEquals(0,mapReader.validateContinent(testMap));
		
	}
	
	@Test
	public void testMapSaver() {
		
		try {
			mapSaver.saveMap(testMap, "testMap1");
			//String filename= "ameroki.map";
			String currentPath = System.getProperty("user.dir");
			currentPath += "\\Maps\\" + "testMap1.map";
			File newFile = new File(currentPath);
			assertTrue(newFile.exists());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
