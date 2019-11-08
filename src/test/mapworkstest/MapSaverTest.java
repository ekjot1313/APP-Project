package test.mapworkstest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import mapWorks.MapEditor;
import mapWorks.MapReader;
import mapWorks.MapSaver;

/**
 * This class tests the functions in MapSaver.java class
 * 
 * @author divya_000
 *
 */
public class MapSaverTest {

	/**
	 * Object of Map
	 */
	static Map testMap;
	/**
	 * Object of MapEditor
	 */
	static MapEditor mapEditor;
	/**
	 * Object of MapReader
	 */
	static MapReader mapReader;
	/**
	 * Object of MapSaver
	 */
	static MapSaver mapSaver;
	/**
	 * Object of Country
	 */
	static Country country;
	/**
	 * Object of Continent
	 */
	static Continent continent;

	/**
	 * This method is used for initialization and set up before running tests
	 */
	@BeforeClass
	public static void before() {
		mapEditor = new MapEditor();
		mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		mapEditor.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
				.split(" "));
		mapEditor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
		testMap = mapEditor.getMap();
		mapReader = new MapReader();
		mapSaver = new MapSaver();
		country = new Country();
		continent = new Continent();
	}

	/**
	 * Method to test {@link mapWorks.MapSaver#MapSaver()}
	 */
	@Test
	public void testMapSaver() {
		try {
			mapSaver.saveMap(testMap, "testMap1");
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
