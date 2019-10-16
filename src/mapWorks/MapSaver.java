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

public class MapSaver { 
	public Map map;
	public String fileName;

	public String message1;
	public String message2;
	public String mapName;
	public List<Continent> listOfContinent;
	public List<Country> listOfCountries;


	public void saveMap(Map map, String fileName) throws IOException {

		this.message1 = map.getMessage1();
		this.message2 = map.getMessage2();
		this.mapName = map.getMapName();
		this.listOfContinent = map.getListOfContinent();
		this.listOfCountries = map.getListOfCountries();

		String currentPath = System.getProperty("user.dir") + "\\Maps\\";
		String mapPath = currentPath + fileName + ".map";

		BufferedWriter bwFile = new BufferedWriter(new FileWriter(mapPath));

		String content = "";
		
		

		content+=(message1 + "\r\n");
		content+=("\r\nname " + mapName + " Map\r\n");
		content+=("\r\n"+message2 + "\r\n");
		content+=("\r\n[continents]\r\n");

		for (Continent continent : map.getListOfContinent()) {
			content+=(continent.getName() + " " + continent.getContinentValue() + " 00000\r\n");
		}
		content+=("\r\n[countries]\r\n");
		
		String borders="";
		
		for (Country country : map.getListOfCountries()) {
			int countryIndex=map.getListOfCountries().indexOf(country)+1;
			int continentIndex = map.getListOfContinent().indexOf(map.getContinentFromName(country.getContinentName()))+1;
			
			content+=(countryIndex + " " + country.getName() + " " + continentIndex + "\r\n");
			borders+=(countryIndex+"");
			for(String neighborName:country.getNeighbors()) {
				int neighborIndex=map.getListOfCountries().indexOf(map.getCountryFromName(neighborName))+1;
				borders+=(" "+neighborIndex);
			}
			borders+=("\r\n");
			
		}

		content+=("\r\n[borders]\r\n"+borders);
		
		bwFile.write(content);
		bwFile.close();
		
		

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
