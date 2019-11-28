package test.pattern.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import mapworks.MapEditor;
import pattern.adapter.ConquestReaderWriter;
import pattern.adapter.DominationReaderWriter;

/**
 * This class tests the functions in ConquestReaderWriter.java class
 * 
 * @author divya_000
 *
 */
public class ConquestReaderWriterTest {

		/**
		 * Object of Map
		 */
		static Map testMap;
		/**
		 * Object of MapEditor
		 */
		static MapEditor mapEditor;
		/**
		 * Object of ConquestReaderWriter
		 */
		static ConquestReaderWriter crw;
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
		public static void beforeClass() {
			mapEditor = new MapEditor();
			mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
			mapEditor.editCountry(
					("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
							.split(" "));
			mapEditor.editNeighbor(
					("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
			testMap = mapEditor.getMap();

			crw = new ConquestReaderWriter();

			Country country1 = new Country();
			country1.setName("Barron");

			Country country2 = new Country();
			country2.setName("Banyan");

			Country country3 = new Country();
			country3.setName("Cyan");
			
			Country country4 = new Country();
			country4.setName("Coron");

			testlistofCountries = new ArrayList<Country>();
			testlistofCountries.add(country1);
			testlistofCountries.add(country2);
			testlistofCountries.add(country3);
			testlistofCountries.add(country4);

		}

		/**
		 * Method to test {@link pattern.adapter.ConquestReaderWriter#parseMapFile(Map, File)}
		 */
		@Test
		public void testParseMap() {
			crw = new ConquestReaderWriter();
			testMap = new Map();
			String filename = "TestConquest.map";
			String currentPath = System.getProperty("user.dir");
			currentPath += "\\Maps\\" + filename;
			File newFile = new File(currentPath);
			int test = crw.parseMapFile(testMap,newFile);
			assertEquals(1, test);
		}

		/**
		 * Method to test {@link pattern.adapter.ConquestReaderWriter#loadContinents(Map)}
		 */
		@Test
		public void testLoadContinents() {

			crw = new ConquestReaderWriter();
			testMap = new Map();
			Continent continent1 = new Continent();
			continent1.setName("Berga");
			continent1.setContinentValue(4);

			List<Continent> testlistOfContinent = new ArrayList<Continent>();
			testlistOfContinent.add(continent1);

			String testname = " ";
			int testcontVal = 0;

			testname = testlistOfContinent.get(0).getName();
			testcontVal = testlistOfContinent.get(0).getContinentValue();

			try {
				String filename = "TestConquest.map";
				String currentPath = System.getProperty("user.dir");
				currentPath += "\\Maps\\" + filename;
				File newFile = new File(currentPath);
				crw.parseMapFile(testMap,newFile);
			} catch (Exception e) {
				// TODO: handle exception
			}

			String name = " ";
			int contVal = 0;

			name = testMap.getListOfContinent().get(0).getName();
			contVal = testMap.getListOfContinent().get(0).getContinentValue();

			assertEquals(testname, name);
			assertEquals(testcontVal, contVal);
		}

		/**
		 * Method to test {@link pattern.adapter.ConquestReaderWriter#loadTerritories(Map)}
		 */
		@Test
		public void testLoadTerritories() {
			crw = new ConquestReaderWriter();
			testMap = new Map();

			String[] testNameofCountry = new String[testlistofCountries.size()];
			for (int i = 0; i < testlistofCountries.size(); i++) {
				testNameofCountry[i] = testlistofCountries.get(i).getName();
			}

			try {
				String filename = "TestConquest.map";
				String currentPath = System.getProperty("user.dir");
				currentPath += "\\Maps\\" + filename;
				File newFile = new File(currentPath);
				crw.parseMapFile(testMap,newFile);
			} catch (Exception e) {
				// TODO: handle exception
			}

			String[] nameOfCountry = new String[testMap.getListOfCountries().size()];
			for (int i = 0; i < testMap.getListOfCountries().size(); i++) {
				nameOfCountry[i] = testMap.getListOfCountries().get(i).getName();
			}

			for (int i = 0; i < testNameofCountry.length; i++) {
				assertEquals(testNameofCountry[i], nameOfCountry[i]);
			}

		}

		
		/**
		 * Method to test {@link pattern.adapter.ConquestReaderWriter#saveMap(Map, String)}
		 */
		@Test
		public void testMapSaver() {
			try {
				crw.saveMap(testMap, "testMap1");
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

