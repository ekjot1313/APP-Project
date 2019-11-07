package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.Bridge;
import dao.Map;
import mapWorks.MapEditor;
import mapWorks.MapReader;

public class MapEditorTest {

	static Map testMap;
	static MapEditor mapEditor;
	static MapReader mapReader;
	
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
		mapReader = new MapReader();
	}
	
	/**
	 * Method to test editContinent()
	 */
	@Test
	public void testEditContinent() {
		MapEditor mapEditorContinent = new MapEditor();
		mapEditorContinent.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		assertTrue(mapEditorContinent.good);
	
		mapEditorContinent.editContinent(("editcontinent asia 10 -add africa 14").split(" "));
		assertFalse(mapEditorContinent.good);
	
	}
	
	/**
	 * Method to test the editCountry()
	 */
	@Test
	public void testEditCountry() {

		MapEditor  mapEditorCountry = new MapEditor();
		mapEditorCountry.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa").split(" "));
		assertTrue(mapEditorCountry.good);
		
		mapEditorCountry.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add africa -add uganda africa").split(" "));
		assertFalse(mapEditorCountry.good);
	}
	
	/**
	 * Method to test the editNeighbor()
	 */
	@Test
	public void testEditNeighbor() {
		
		MapEditor mapEditorNeighbor = new MapEditor();
		mapEditorNeighbor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
		assertTrue(mapEditorNeighbor.good);
		
		mapEditorNeighbor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add").split(" "));
		assertFalse(mapEditorNeighbor.good);
	}
	
	/**
	 * Method to test addContinentToQueue(String continentName, String continentValue, ArrayList<ArrayList<String>> queue)
	 */
	@Test
	public void testAddContinentToQueue() {
		
		MapEditor mapEditorAddContinent = new MapEditor();
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
		String continentName = "asia";
		String continentValue = "11";
		queue= mapEditorAddContinent.addContinentToQueue(continentName, continentValue, queue);
		
		ArrayList<ArrayList<String>> testqueue = new ArrayList<ArrayList<String>>();
		testqueue.add(new ArrayList<>(Arrays.asList("add", "asia", "11")));
		
		assertEquals(testqueue, queue);
		
		String wrongcontinentName = "-asia";
		String wrongcontinentValue = "11";
		assertNull(mapEditorAddContinent.addContinentToQueue(wrongcontinentName, wrongcontinentValue, queue));
		
	}
	
	/**
	 * Method to test removeContinentToQueue(String continentName, ArrayList<ArrayList<String>> queue)
	 */
	@Test
	public void testremoveContinentToQueue(){
		
		MapEditor mapEditorRemoveContinent = new MapEditor();
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
		String continentName = "asia";
		queue= mapEditorRemoveContinent.removeContinentToQueue(continentName, queue);
		
		ArrayList<ArrayList<String>> testqueue = new ArrayList<ArrayList<String>>();
		testqueue.add(new ArrayList<>(Arrays.asList("remove", "asia")));
	
		assertEquals(testqueue, queue);
		
		String wrongcontinentName = "-asia";
		assertNull(mapEditorRemoveContinent.removeContinentToQueue(wrongcontinentName, queue));
		
	}
	
	/**
	 * Method to test addCountryToQueue(String countryName, String continentName, ArrayList<ArrayList<String>> queue)
	 */
	@Test
	public void testAddCountryToQueue() {
		
		MapEditor mapEditorAddCountry = new MapEditor();
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
		String continentName = "asia";
		String countryName = "india";
		queue= mapEditorAddCountry.addCountryToQueue(continentName, countryName , queue);
		
		ArrayList<ArrayList<String>> testqueue = new ArrayList<ArrayList<String>>();
		testqueue.add(new ArrayList<>(Arrays.asList("add", "asia", "india")));
		
		assertEquals(testqueue, queue);
		
		String wrongcontinentName = "asia";
		String wrongcountryName = "-india";
		assertNull(mapEditorAddCountry.addCountryToQueue(wrongcontinentName, wrongcountryName, queue));
		
	}
	
	/**
	 * Method to test removeCountryToQueue(String countryName, ArrayList<ArrayList<String>> queue)
	 */
	@Test
	public void testremoveCountryToQueue(){
		
		MapEditor mapEditorRemoveCountry = new MapEditor();
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
		String countryName = "china";
		queue= mapEditorRemoveCountry.removeCountryToQueue(countryName, queue);
		
		ArrayList<ArrayList<String>> testqueue = new ArrayList<ArrayList<String>>();
		testqueue.add(new ArrayList<>(Arrays.asList("remove", "china")));
	
		assertEquals(testqueue, queue);
		
		String wrongcountryName = "-china";
		assertNull(mapEditorRemoveCountry.removeCountryToQueue(wrongcountryName, queue));
		
	}
	
	/**
	 * Method to test addNeighborToQueue(String countryName, String neighborCountryName,ArrayList<ArrayList<String>> queue)
	 */
	@Test
	public void testAddNeighborToQueue() {
		
		MapEditor mapEditorAddNeighbor = new MapEditor();
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
		String neighborCountryName = "china";
		String countryName = "india";
		queue= mapEditorAddNeighbor.addNeighborToQueue(neighborCountryName, countryName , queue);
		
		ArrayList<ArrayList<String>> testqueue = new ArrayList<ArrayList<String>>();
		testqueue.add(new ArrayList<>(Arrays.asList("add", "china", "india")));
		
		assertEquals(testqueue, queue);
		
		String wrongNeighborCountryName = "china";
		String wrongcountryName = "-india";
		assertNull(mapEditorAddNeighbor.addNeighborToQueue(wrongNeighborCountryName, wrongcountryName, queue));
		
	}
	
	/**
	 * Method to test removeNeighborToQueue(String countryName, String neighborCountryName,ArrayList<ArrayList<String>> queue)
	 */
	@Test
	public void testRemoveNeighborToQueue(){
		
		MapEditor mapEditorRemoveNeighbor = new MapEditor();
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
		String countryName = "china";
		String neighborCountryName = "india";
		queue= mapEditorRemoveNeighbor.removeNeighborToQueue(countryName, neighborCountryName, queue);
		
		ArrayList<ArrayList<String>> testqueue = new ArrayList<ArrayList<String>>();
		testqueue.add(new ArrayList<>(Arrays.asList("remove", "china", "india")));
	
		assertEquals(testqueue, queue);
		
		String wrongCountryName = "-china";
		String wrongNeighborCountryName = "india";
		assertNull(mapEditorRemoveNeighbor.removeNeighborToQueue(wrongCountryName, neighborCountryName, queue));
		
	}
	
	
	
	/**
	 * Method to test whether the bridge between two continents is removed
	 */
	@Test
	public void testRemoveBridges() {
		assertTrue(mapEditor.removeBridge("asia", "africa", "india", "congo"));
	}


}