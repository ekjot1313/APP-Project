package test.pattern.builder;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.BeforeClass;
import org.junit.Test;

import dao.Map;
import mapworks.MapEditor;
import pattern.builder.Director;
import pattern.builder.SaveGameBuilder;


public class SaveGameBuilderTest {
	
	static Map map,testMap;
	static File newFile;
	static boolean flag= false;
	/**
	 * Object of MapEditor
	 */
	static MapEditor mapEditor;
	
	/**
	 * This method is used for initialization and set up before running tests
	 */
	@BeforeClass
	public static void BeforeClass() {
		map = new Map();
		
		mapEditor = new MapEditor();
		mapEditor.editContinent(("editcontinent -add asia 10 -add africa 14").split(" "));
		mapEditor.editCountry(
				("editcountry -add india asia -add pakistan asia -add china asia -add congo africa -add uganda africa")
						.split(" "));
		mapEditor.editNeighbor(
				("editneighbor -add india pakistan -add pakistan china -add india congo -add congo uganda").split(" "));
		testMap = mapEditor.getMap();

		Director d= new Director();
		d.setGbuilder(new SaveGameBuilder());
		d.constructGame("SaveGameTestMap", testMap, "testPlayer", "Attack");
		
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\SavedGame\\" + "SaveGameTestMap.txt";
		newFile = new File(currentPath);
		flag= false;
	}

	/**
	 * Test method for BuildMap(filename, map)
	 */
	@Test
	public void testbuildMap() {
		
		try {
			BufferedReader bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			
			String currentLine="";
			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[Continents]")) {
					flag = true;
				}

				if (currentLine.contains("[Territories]")) {
					flag = true;
				}
				
				if(currentLine.contains("[PlayerList]")) {
					flag = true;
				}
				
				if(currentLine.contains("[CardDetails]")) {
					flag = true;
				}
			}
			assertTrue(flag);
		
		}catch (Exception e) {
		// TODO: handle exception
			}
	}
	
	/**
	 * Test method for BuildPLayer(filename, player)
	 */
	@Test
	public void testBuildPlayer() {
		
		try {
			flag = false;
			BufferedReader bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			
			String currentLine="";
			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[CurrentPlayer]")) {
					flag = true;
				}
			}
			assertTrue(flag);
		
		}catch (Exception e) {
		// TODO: handle exception
			}
	}
	
	/**
	 * Test method for BuildPhase(filename, phase)
	 */
	@Test
	public void testBuildPhase() {
		
		try {
			flag = false;
			BufferedReader bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			
			String currentLine="";
			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[CurrentPhase]")) {
					flag = true;
				}
			}
			assertTrue(flag);
		
		}catch (Exception e) {
		// TODO: handle exception
			}
	}
}
