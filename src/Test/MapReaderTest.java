/**
 * 
 */
package Test;

import static org.junit.Assert.*;

import java.io.File;
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
	
	@Test
	public void testParseMap() {
		String filename= "ameroki.map";
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\" + filename;
		File newFile = new File(currentPath);
		int test=mapReader.parseMapFile(newFile);
		
		assertEquals(1, test);
		
	}

	/*@Test
	public void testcalculateReinforceArmies() {
		
		player.allocate();
		player.populateCountries(testMap);
		
		int reinforce= reinforceArmies.calculateReinforceArmies((ArrayList<Player>) player.listOfPlayers, testMap, 1);
		
		assertEquals(3, reinforce);
	}*/


}
