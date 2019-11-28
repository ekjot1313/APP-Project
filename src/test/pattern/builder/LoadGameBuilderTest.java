package test.pattern.builder;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

import dao.Continent;
import dao.Country;
import dao.Map;
import mapworks.MapEditor;
import pattern.adapter.DominationReaderWriter;
import pattern.builder.Director;
import pattern.builder.Game;
import pattern.builder.LoadGameBuilder;

/**
 * This class test methods in LoadGameBuilder class
 *
 */
public class LoadGameBuilderTest {

	static Map map, testMap;
	/**
	 * Object of MapEditor
	 */
	static MapEditor mapEditor;
	/**
	 * Object of DominationReaderWriter
	 */
	static DominationReaderWriter drw;
	/**
	 * List of objects of Countries
	 */
	static List<Country> testlistofCountries;
	/**
	 * List of objects of Neighbors of countries
	 */
	static List<String> testListofNeighbors1;
	/**
	 * List of objects of Neighbors of countries
	 */
	static List<String> testListofNeighbors2;
	/**
	 * List of objects of Neighbors of countries
	 */
	static List<String> testListofNeighbors3;

	/**
	 * This method is used for initialization and set up before running tests
	 */
	@BeforeClass
	public static void BeforeClass(){
		map =new Map();
		
		mapEditor = new MapEditor();
		mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		mapEditor.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
						.split(" "));
		mapEditor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
		testMap = mapEditor.getMap();
		drw = new DominationReaderWriter();

		Country country1 = new Country();
		country1.setName("siberia");

		Country country2 = new Country();
		country2.setName("worrick");

		Country country3 = new Country();
		country3.setName("yazteck");

		testlistofCountries = new ArrayList<Country>();
		testlistofCountries.add(country1);
		testlistofCountries.add(country2);
		testlistofCountries.add(country3);

		testListofNeighbors1 = new ArrayList<String>();
		testListofNeighbors1.add("egypt");
		testListofNeighbors1.add("great_britain");
		country1.setNeighbors(testListofNeighbors1);

		testListofNeighbors2 = new ArrayList<String>();
		testListofNeighbors2.add("siberia");
		country2.setNeighbors(testListofNeighbors2);

		testListofNeighbors3 = new ArrayList<String>();
		testListofNeighbors3.add("worrick");
		country3.setNeighbors(testListofNeighbors3);
	}

	/**
	 * Method to test buildMap
	 */
	@Test
	public void testBuildMap() {
		
		Continent continent1 = new Continent();
		continent1.setName("azio");
		continent1.setContinentValue(5);

		List<Continent> testlistOfContinent = new ArrayList<Continent>();
		testlistOfContinent.add(continent1);

		String testname = " ";
		int testcontVal = 0;

		testname = testlistOfContinent.get(0).getName();
		testcontVal = testlistOfContinent.get(0).getContinentValue();

		try {
			Director d = new Director();
			d.setGbuilder(new LoadGameBuilder());
			d.constructGame("thirteen", map, " ", " ");
			Game game=d.getGame();
			map = game.getMap();
		} catch (Exception e) {
			// TODO: handle exception
		}

		String name = " ";
		int contVal = 0;

		name = map.getListOfContinent().get(0).getName();
		contVal = map.getListOfContinent().get(0).getContinentValue();

		assertEquals(testname, name);
		assertEquals(testcontVal, contVal);
		
		String[] testNameofCountry = new String[testlistofCountries.size()];
		for (int i = 0; i < testlistofCountries.size(); i++) {
			testNameofCountry[i] = testlistofCountries.get(i).getName();
		}

		String[] nameOfCountry = new String[map.getListOfCountries().size()];
		for (int i = 0; i < map.getListOfCountries().size(); i++) {
			nameOfCountry[i] = map.getListOfCountries().get(i).getName();
		}

		for (int i = 0; i < testNameofCountry.length; i++) {
			assertEquals(testNameofCountry[i], nameOfCountry[i]);
		}

		List<String> country1Neighbors = new ArrayList<String>();
		List<String> country2Neighbors = new ArrayList<String>();
		List<String> country3Neighbors = new ArrayList<String>();

		for (int i = 0; i < map.getListOfCountries().get(0).getNeighbors().size(); i++) {
			country1Neighbors.add(map.getListOfCountries().get(0).getNeighbors().get(i));
		}

		for (int i = 0; i < map.getListOfCountries().get(1).getNeighbors().size(); i++) {
			country2Neighbors.add(map.getListOfCountries().get(1).getNeighbors().get(i));
		}

		for (int i = 0; i < map.getListOfCountries().get(2).getNeighbors().size(); i++) {
			country3Neighbors.add(map.getListOfCountries().get(2).getNeighbors().get(i));
		}

		for (int i = 0; i < testListofNeighbors1.size(); i++)
			assertEquals(testListofNeighbors1.get(i), country1Neighbors.get(i));

		for (int i = 0; i < testListofNeighbors2.size(); i++)
			assertEquals(testListofNeighbors2.get(i), country2Neighbors.get(i));

		for (int i = 0; i < testListofNeighbors3.size(); i++)
			assertEquals(testListofNeighbors3.get(i), country3Neighbors.get(i));
	}
	
	/**
	 * Method to test build Player
	 */
	@Test
	public void testBuildPlayer() {
		
		String player= "";
		try {
			Director d = new Director();
			d.setGbuilder(new LoadGameBuilder());
			d.constructGame("thirteen", map, " ", " ");
			Game game=d.getGame();
			map = game.getMap();
			player = game.getCurrentPlayer();
		} catch (Exception e) {
			// TODO: handle exception
		}

		String testCurrPlayer = "c";
		assertEquals(testCurrPlayer, player);
		
	}
	
	/**
	 * Method to test build phase
	 */
	@Test
	public void testBuildPhase() {
		
		String phase= "";
		try {
			Director d = new Director();
			d.setGbuilder(new LoadGameBuilder());
			d.constructGame("thirteen", map, " ", " ");
			Game game=d.getGame();
			map = game.getMap();
			phase = game.getCurrentPhase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		String testCurrPhase = "Reinforcement";
		assertEquals(testCurrPhase, phase);
		
	}
}
