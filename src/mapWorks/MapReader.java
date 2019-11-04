package mapWorks;

import dao.Bridge;
import dao.Continent;
import dao.Country;
import dao.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reads the map file and displays it to user
 * 
 * @author Piyush
 * @author Mitalee Naik
 * @since 1.0.0
 *
 */
public class MapReader {

	/**
	 * Map to store the current map object
	 */
	public Map map;
	/**
	 * BufferedReader to process map file
	 */
	private BufferedReader bufferReaderForFile;
	/**
	 * CurrentLine to store the current line of parsing
	 */
	private String currentLine;
	

	/**
	 * Default Constructor
	 */
	public MapReader() {
		this.map = new Map();
	}

	/**
	 * This method parses the map file
	 * 
	 * @param file Map file to be parsed
	 * @return 1 if successful else 0
	 */
	public int parseMapFile(File file) {
		map = new Map();

		try {

			bufferReaderForFile = new BufferedReader(new FileReader(file));

			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[continents]")) {
					loadContinents();
				}

				if (currentLine.contains("[countries]")) {
					loadCountries();
				}
				if (currentLine.contains("[borders]")) {
					loadBorders();
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
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	private void loadBorders() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

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
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	private void loadCountries() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] countryDetails = currentLine.split(" ");
			Country country = new Country();
			country.setName(countryDetails[1]);
			country.setContinentName(map.getListOfContinent().get((Integer.parseInt(countryDetails[2])) - 1).getName());
			map.getListOfCountries().add(country);
			map.getListOfContinent().get((Integer.parseInt(countryDetails[2])) - 1).getCountries()
					.add(countryDetails[1]);
		}

	}

	/**
	 * This method loads the continents to the map object
	 * 
	 * @throws NumberFormatException for Buffered Reader
	 * @throws IOException           for Buffered Reader
	 */
	private void loadContinents() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

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
	 * This method returns the currently loaded map
	 * 
	 * @return Map Object
	 */
	public Map getMap() {
		return this.map;
	}



}