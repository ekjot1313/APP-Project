package mapWorks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.Bridge;
import dao.Continent;
import dao.Country;
import dao.Map;

public class ConquestReaderWriter {
	
	/**
	 * BufferedReader to process map file
	 */
	private BufferedReader bufferReaderForFile;
	/**
	 * CurrentLine to store the current line of parsing
	 */
	private String currentLine;
	
	
	/**
	 * This method parses the map file
	 * 
	 * @param file Map file to be parsed
	 * @return 1 if successful else 0
	 */

	public int parseMapFile(Map map,File file) {
		

		try {

			bufferReaderForFile = new BufferedReader(new FileReader(file));

			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[Continents]")) {
					loadContinents(map);
				}

				if (currentLine.contains("[Territories]")) {
					loadTerritories(map);
				}
				

			}
			
			loadBridges(map);
			
			// validate map call
			int notConnected = map.validateMap();
			int notConnectedSubGraph = map.validateContinent(map);
			System.out.println();
			if (notConnected == 0 && notConnectedSubGraph == 0) {
				System.out.println("Valid Map");
			} else {
				System.out.println("Invalid map");
				return 0;
			}

		} catch (Exception e) {
			if (e.toString().contains("FileNotFoundException"))
				System.out.println("Invalid filename");
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * This method loads the bridges to the continents
	 * 
	 */
	public void loadBridges(Map map) {
		// TODO Auto-generated method stub
		for(Country c: map.getListOfCountries()) {
			for(String neighborCountry :c.getNeighbors() ) {
				if(!map.getCountryFromName(neighborCountry).getContinentName().equals(c.getContinentName())) {
					Bridge bridge = new Bridge(map.getCountryFromName(neighborCountry).getContinentName(),c.getName() ,neighborCountry);
					map.getContinentFromName(c.getContinentName()).getBridges().add(bridge);
				}
			}
		}
	}

	/**
	 * This method loads the territories to the map object
	 * 
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	public void loadTerritories(Map map) throws NumberFormatException, IOException {
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			
			if (currentLine.length() == 0) {
				continue;
			}
			String[] countryDetails = currentLine.split(",");
			Country c = new Country();
			c.setName(countryDetails[0]);
			c.setContinentName(countryDetails[3]);
			Continent continent = map.getContinentFromName(countryDetails[3]);
			continent.getCountries().add(c.getName());
			List<String> neighbours =  new ArrayList<String>();
			for (int i = 4; i < countryDetails.length; i++) {
				neighbours.add(countryDetails[i]);
			}
			c.setNeighbors(neighbours);
			map.addCountry(c);

		}
		
	}
	/**
	 * This method loads the continents to the map object
	 * 
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	public void loadContinents(Map map) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] continentDetails = currentLine.split("=");
			Continent continent = new Continent();
			continent.setName(continentDetails[0]);
			continent.setContinentValue(Integer.parseInt(continentDetails[1]));
			map.addContinent(continent);
			

		}

	}

	/**
	 * This method saves the map to .map file
	 * 
	 * @param map      Map Object
	 * @param fileName Name of the file
	 * @throws IOException for Buffered Reader
	 */
	public void saveMap(Map map, String fileName) throws IOException {
		
		String message1;
		String message2;
		String mapName;
		message1 = map.getMessage1();
		message2 = map.getMessage2();
		mapName = map.getMapName();
		String currentPath = System.getProperty("user.dir") + "\\Maps\\";
		String mapPath = currentPath + fileName + ".map";
		BufferedWriter bwFile = new BufferedWriter(new FileWriter(mapPath));
		String content = "; map";
		content += (message1 + "\r\n");
		content += ("\r\nname " + mapName + " Map\r\n");
		content += ("\r\n" + message2 + "\r\n");
		content += ("\r\n[Continents]\r\n");
		for (Continent continent : map.getListOfContinent()) {
			content += (continent.getName() + "=" + continent.getContinentValue()+"\n");
		}
		content += ("\r\n[Territories]\r\n");
		String borders = "";
		for (Country country : map.getListOfCountries()) {
			content += (country.getName()+ ",0,0," + country.getContinentName()+ ",");
			for(String neighbor: country.getNeighbors()) {
				content +=(neighbor+",");
			}
			content += "\n";
		}
		bwFile.write(content);
		bwFile.close();
		System.out.println("Map file saved as: " + fileName + ".map");
	}

}
