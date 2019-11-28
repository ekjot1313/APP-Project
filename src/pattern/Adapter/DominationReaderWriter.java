package pattern.Adapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import dao.Bridge;
import dao.Continent;
import dao.Country;
import dao.Map;

/**
 * This class is used for reading and writing map files in Domination format
 *
 */
public class DominationReaderWriter {

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
	 * @param map  Map Object
	 * @param file Map file to be parsed
	 * @return 1 if successful else 0
	 */
	public int parseMapFile(Map map, File file) {

		try {

			bufferReaderForFile = new BufferedReader(new FileReader(file));

			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[continents]")) {
					loadContinents(map);
				}

				if (currentLine.contains("[countries]")) {
					loadCountries(map);
				}
				if (currentLine.contains("[borders]")) {
					loadBorders(map);
				}

			}
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
			System.out.println(e);
			return 0;
		}
		return 1;
	}

	/**
	 * This method loads borders to the map object
	 * 
	 * @param map Map Object
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	public void loadBorders(Map map) throws NumberFormatException, IOException {
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] neighbourDetails = currentLine.split(" ");
			for (int i = 0; i < neighbourDetails.length - 1; i++) {
				map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getNeighbors()
						.add(map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1).getName());

				if (!map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getContinentName().equals(
						map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1).getContinentName()))

				{
					map.getContinentFromName(
							map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getContinentName())
							.getBridges()
							.add(new Bridge(
									map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1)
											.getContinentName(),
									map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getName(),
									map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1)
											.getName()));
				}
			}

		}

	}

	/**
	 * This method loads countries to the map object
	 * 
	 * @param map Map Object
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	public void loadCountries(Map map) throws NumberFormatException, IOException {
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] countryDetails = currentLine.split(" ");
			Country country = new Country();
			country.setName(countryDetails[1]);
			country.setContinentName(map.getListOfContinent().get((Integer.parseInt(countryDetails[2])) - 1).getName());
			map.addCountry(country);

			map.getListOfContinent().get((Integer.parseInt(countryDetails[2])) - 1).getCountries()
					.add(countryDetails[1]);
		}

	}

	/**
	 * This method loads the continents to the map object
	 * 
	 * @param map Map Object
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	public void loadContinents(Map map) throws NumberFormatException, IOException {

		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] continentDetails = currentLine.split(" ");
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

		String message1 = " ";
		String message2 = " ";
		String mapName;
		message1 = map.getMessage1();
		message2 = map.getMessage2();
		mapName = map.getMapName();
		String currentPath = System.getProperty("user.dir") + "\\Maps\\";
		String mapPath = currentPath + fileName + ".map";
		BufferedWriter bwFile = new BufferedWriter(new FileWriter(mapPath));
		String content = ";Map";
		content += (message1 + "\r\n");
		content += ("\r\nname " + mapName + " Map\r\n");
		content += ("\r\n" + message2 + "\r\n");
		content += ("\r\n[continents]\r\n");
		for (Continent continent : map.getListOfContinent()) {
			content += (continent.getName() + " " + continent.getContinentValue() + " 00000\r\n");
		}
		content += ("\r\n[countries]\r\n");
		String borders = "";
		for (Country country : map.getListOfCountries()) {
			int countryIndex = map.getListOfCountries().indexOf(country) + 1;
			int continentIndex = map.getListOfContinent().indexOf(map.getContinentFromName(country.getContinentName()))
					+ 1;

			content += (countryIndex + " " + country.getName() + " " + continentIndex + "\r\n");
			borders += (countryIndex + "");
			for (String neighborName : country.getNeighbors()) {
				int neighborIndex = map.getListOfCountries().indexOf(map.getCountryFromName(neighborName)) + 1;
				borders += (" " + neighborIndex);
			}
			borders += ("\r\n");
		}
		content += ("\r\n[borders]\r\n" + borders);
		bwFile.write(content);
		bwFile.close();
		System.out.println("Map file saved as: " + fileName + ".map");
	}
}
