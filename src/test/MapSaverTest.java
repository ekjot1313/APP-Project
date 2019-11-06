package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import mapWorks.MapEditor;
import mapWorks.MapReader;
import mapWorks.MapSaver;

public class MapSaverTest {
	
	static Map testMap;
	static MapEditor mapEditor;
	static MapReader mapReader;
	static MapSaver mapSaver;
	static Country country;
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

}
