package test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import java.util.ArrayList;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import mapWorks.MapEditor;
import mapWorks.MapReader;
import mapWorks.MapSaver;

/**
 * This class tests the functions in MapReader.java class
 * 
 * @author divya_000
 *
 */
public class MapReaderTest {

	static Map testMap;
	static MapEditor mapEditor;
	static MapReader mapReader;
	static MapSaver mapSaver;
	static List<Country> testlistofCountries;
	static List<String> testListofNeighbors1;
	static List<String> testListofNeighbors2;
	static List<String> testListofNeighbors3;

	/**
	 * This method is used for initialization and set up before running tests
	 */
	@BeforeClass
	public static void beforeClass() {
		mapEditor = new MapEditor();
		mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		mapEditor.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
						.split(" "));
		mapEditor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
		testMap = mapEditor.getMap();

		mapSaver = new MapSaver();

		Country country1 = new Country();
		country1.setName("india");

		Country country2 = new Country();
		country2.setName("china");

		Country country3 = new Country();
		country3.setName("pakistan");

		testlistofCountries = new ArrayList<Country>();
		testlistofCountries.add(country1);
		testlistofCountries.add(country2);
		testlistofCountries.add(country3);

		testListofNeighbors1 = new ArrayList<String>();
		testListofNeighbors1.add("pakistan");
		testListofNeighbors1.add("china");
		country1.setNeighbors(testListofNeighbors1);

		testListofNeighbors2 = new ArrayList<String>();
		testListofNeighbors2.add("india");
		country2.setNeighbors(testListofNeighbors2);

		testListofNeighbors3 = new ArrayList<String>();
		testListofNeighbors3.add("india");
		country3.setNeighbors(testListofNeighbors3);

	}

	/**
	 * Method to check parsing of the input file
	 */
	@Test
	public void testParseMap() {
		mapReader = new MapReader();
		String filename = "ameroki.map";
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\" + filename;
		File newFile = new File(currentPath);
		int test = mapReader.parseMapFile(newFile);
		assertEquals(1, test);
	}

	/**
	 * Method to check the loadContinent()
	 */
	@Test
	public void testLoadContinent() {

		mapReader = new MapReader();
		Continent continent1 = new Continent();
		continent1.setName("asia");
		continent1.setContinentValue(11);

		List<Continent> testlistOfContinent = new ArrayList<Continent>();
		testlistOfContinent.add(continent1);

		String testname = " ";
		int testcontVal = 0;

		testname = testlistOfContinent.get(0).getName();
		testcontVal = testlistOfContinent.get(0).getContinentValue();

		try {
			String filename = "abcd.map";
			String currentPath = System.getProperty("user.dir");
			currentPath += "\\Maps\\" + filename;
			File newFile = new File(currentPath);
			mapReader.parseMapFile(newFile);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String name = " ";
		int contVal = 0;

		name = mapReader.getMap().getListOfContinent().get(0).getName();
		contVal = mapReader.getMap().getListOfContinent().get(0).getContinentValue();

		assertEquals(testname, name);
		assertEquals(testcontVal, contVal);
	}

	/**
	 * This method tests the loadCountries()
	 */
	@Test
	public void testLoadCountries() {
		mapReader = new MapReader();

		String[] testNameofCountry = new String[testlistofCountries.size()];
		for (int i = 0; i < testlistofCountries.size(); i++) {
			testNameofCountry[i] = testlistofCountries.get(i).getName();
		}

		try {
			String filename = "abcd.map";
			String currentPath = System.getProperty("user.dir");
			currentPath += "\\Maps\\" + filename;
			File newFile = new File(currentPath);
			mapReader.parseMapFile(newFile);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String[] nameOfCountry = new String[mapReader.getMap().getListOfCountries().size()];
		for (int i = 0; i < mapReader.getMap().getListOfCountries().size(); i++) {
			nameOfCountry[i] = mapReader.getMap().getListOfCountries().get(i).getName();
		}

		for (int i = 0; i < testNameofCountry.length; i++) {
			assertEquals(testNameofCountry[i], nameOfCountry[i]);
		}

	}

	/**
	 * This method tests the loadBorders()
	 */
	@Test
	public void testLoadBorders() {

		mapReader = new MapReader();

		try {
			String filename = "abcd.map";
			String currentPath = System.getProperty("user.dir");
			currentPath += "\\Maps\\" + filename;
			File newFile = new File(currentPath);
			mapReader.parseMapFile(newFile);
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<String> country1Neighbors = new ArrayList<String>();
		List<String> country2Neighbors = new ArrayList<String>();
		List<String> country3Neighbors = new ArrayList<String>();

		for (int i = 0; i < mapReader.getMap().getListOfCountries().get(0).getNeighbors().size(); i++) {
			country1Neighbors.add(mapReader.getMap().getListOfCountries().get(0).getNeighbors().get(i));
		}

		for (int i = 0; i < mapReader.getMap().getListOfCountries().get(1).getNeighbors().size(); i++) {
			country2Neighbors.add(mapReader.getMap().getListOfCountries().get(1).getNeighbors().get(i));
		}

		for (int i = 0; i < mapReader.getMap().getListOfCountries().get(2).getNeighbors().size(); i++) {
			country3Neighbors.add(mapReader.getMap().getListOfCountries().get(2).getNeighbors().get(i));
		}

		for (int i = 0; i < testListofNeighbors1.size(); i++)
			assertEquals(testListofNeighbors1.get(i), country1Neighbors.get(i));

		for (int i = 0; i < testListofNeighbors2.size(); i++)
			assertEquals(testListofNeighbors2.get(i), country2Neighbors.get(i));

		for (int i = 0; i < testListofNeighbors3.size(); i++)
			assertEquals(testListofNeighbors3.get(i), country3Neighbors.get(i));

	}
}