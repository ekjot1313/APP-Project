package mapWorks;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Game.Continent;
import Game.Country;
import Game.Map;

/**
 * This Class will save the given Map object to .map file
 * 
 * @author ekjot
 *
 */
public class MapSaver {        //UNDER CONSTRUCTION
	public Map map;
	public String fileName;
	
	public String message1;
	public String message2;
	public String mapName;
	public List<Continent> listOfContinent;
	public List<Country> listOfCountries;

	public static void main(String[] args) {
		// (new MapSaver()).saveMap();
	}
	public void saveMap(Map map,String fileName) throws IOException {
		
		this.message1 = map.message1;
		this.message2 = map.message2;
		this.mapName = map.mapName;
		this.listOfContinent = map.listOfContinent;
		this.listOfCountries = map.listOfCountries;
		
		String currentPath = System.getProperty("user.dir")+"\\src\\Maps\\";
		String mapPath=currentPath+fileName+".map";
		
		BufferedWriter bwFile=new BufferedWriter(new FileWriter(mapPath));
		
		bwFile.write(message1 + "/n");
		bwFile.write("name " + mapName + " Map/n");
		bwFile.write(message2 + "/n");
		
		bwFile.write("[Continents]/n");
		
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

}
