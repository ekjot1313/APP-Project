package mapWorks;

import dao.Bridge;
import dao.Continent;
import dao.Country;
import dao.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import mapWorks.MapReader;

/**
 * This class is used to edit the map. This will be called when user will enter
 * 'editmap' command
 * 
 * @author Ekjot
 * @author Hartaj
 *
 */
public class MapEditor {
	/**
	 * static flag to store the status
	 */
	public static boolean good;
	/**
	 * static flag to print or not
	 */
	public static boolean print=true;
	/**
	 * to store map object
	 */
	public Map map;

	/**
	 * This method returns map object
	 * 
	 * @return Map Object
	 */
	public Map getMap() {
		return this.map;

	}

	/**
	 * This method sets map object
	 * 
	 * @param map Map Object
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * Default Constructor
	 */
	public MapEditor() {
		MapEditor.good = true;
		this.map = new Map();
	}

	
	/**
	 * This method initializes map editor
	 * 
	 * @param map Map Object
	 * @return Map object
	 * @throws IOException BufferedReader used for user input
	 */
	public Map mapeditorInit(Map map) throws IOException {
		BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));

		if (map == null)
			this.map = new Map();
		else
			this.map = map;

		String command[];
		good = true;

		command = brConsole.readLine().split(" ");

		while (good) {

			switch (command[0]) {
			case "editcontinent": {
				editContinent(command);

				break;
			}
			case "editcountry": {
				editCountry(command);
				break;
			}
			case "editneighbor": {
				editNeighbor(command);
				break;
			}
			case "showmap": {
				showMap(this.map);
				break;
			}
			case "validatemap": {
				validateMap(this.map);
				break;
			}
			case "savemap": {
				if (command.length == 2) {
					good = false;
					continue;
				} else
					print("Invalid Command. Type Again.");
				break;
			}
			default: {
				print("Invalid Command. Type Again.");
			}
			}

			command = brConsole.readLine().split(" ");
			good = true;

		}
		MapSaver ms = new MapSaver();
		MapReader mr = new MapReader();
		mr.map = this.map;
		if (mr.validateMap(mr.map) == 0 && mr.validateContinent(mr.map) == 0) {
			ms.saveMap(mr.map, command[1]);
			return mr.map;
		} else {
			print("The map file cannot be saved as the map is not connected");
			return null;
		}
	}

	/**
	 * This method is used to validate the map object
	 * 
	 * @param map Map object to be validated
	 */
	private void validateMap(Map map) {
		// TODO Auto-generated method stub
		int notConnected = (new MapReader()).validateMap(map);
		int notConnectedSubGraph = (new MapReader()).validateContinent(map);
		if (notConnected == 0 && notConnectedSubGraph == 0) {
			print("Map is valid");
		} else {
			print("Map is invalid");
		}

	}

	/**
	 * This method displays the map
	 * 
	 * @param map Map Object
	 */
	public void showMap(Map map) {
		// TODO Auto-generated method stub

		(new MapReader()).display(map);

	}

	/**
	 * This method is used to edit neighbor
	 * 
	 * @param command Command given the user
	 */
	public void editNeighbor(String[] command) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> stack = new ArrayList<ArrayList<String>>();
		int len = command.length;
		if (len < 2) {
			print("Invalid Command. Cannot find '-add' or '-remove'.");
			return;
		}

		for (int i = 1; i < len && good; i++) { // starting from 1 because 0th index was editneighbor
			String word = command[i];
			if (word.charAt(0) == '-' && good) {
				switch (word.substring(1)) {
				case "add": {
					if (i + 2 < len) {
						String countryName = command[++i], neighborCountryName = command[++i];
						addNeighbor(countryName, neighborCountryName, stack);
					} else {
						print("Invalid Command. Cannot find 'countryname' or 'neighborcountryname'.");
						good = false;
						return;
					}
					break;
				}
				case "remove": {
					if (i + 1 < len) {
						String countryName = command[++i], neighborCountryName = command[++i];
						removeNeighbor(countryName, neighborCountryName, stack);
					} else {
						print("Invalid Command. Cannot find 'countryname' or 'neighborcountryname'.");
						good = false;
						return;
					}
					break;
				}
				default: {
					print("Invalid Command. Cannot find '-add' or '-remove'.");
					good = false;
					return;
				}

				}
			} else {
				print("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}

		if (good) {
			executeStack("editneighbor", stack);
		} else {
			print("Error!!!");
		}
	}

	/**
	 * This method removes a neighboring country.
	 * 
	 * @param countryName         Country Name
	 * @param neighborCountryName Neighboring country to be removed
	 * @param stack               Stack
	 */
	private static void removeNeighbor(String countryName, String neighborCountryName,
			ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("remove", countryName, neighborCountryName)));
	}

	/**
	 * This method adds a neighboring country.
	 * 
	 * @param countryName         Country Name
	 * @param neighborCountryName Neighboring country to be added
	 * @param stack               Stack
	 */
	private static void addNeighbor(String countryName, String neighborCountryName,
			ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("add", countryName, neighborCountryName)));
	}

	/**
	 * This method is used to edit country
	 * 
	 * @param command Command given be the user
	 */
	public void editCountry(String[] command) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> stack = new ArrayList<ArrayList<String>>();
		int len = command.length;
		if (len < 2) {
			print("Invalid Command. Cannot find '-add' or '-remove'.");
			return;
		}

		for (int i = 1; i < len && good; i++) { // starting from 1 because 0th index was editcountry
			String word = command[i];
			if (word.charAt(0) == '-' && good) {
				switch (word.substring(1)) {
				case "add": {
					if (i + 2 < len) {
						String countryName = command[++i], continentName = command[++i];
						addCountry(countryName, continentName, stack);
					} else {
						print("Invalid Command. Cannot find 'countryname' or 'continentname'.");
						good = false;
						return;
					}
					break;
				}
				case "remove": {
					if (i + 1 < len) {
						String countryName = command[++i];
						removeCountry(countryName, stack);
					} else {
						print("Invalid Command. Cannot find 'countryname'.");
						good = false;
						return;
					}
					break;
				}
				default: {
					print("Invalid Command. Cannot find '-add' or '-remove'.");
					good = false;
					return;
				}

				}
			} else {
				print("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}
		if (good) {
			executeStack("editcountry", stack);
		}

	}

	/**
	 * This method removes a country.
	 * 
	 * @param countryName Country to be removed
	 * @param stack       Stack
	 */
	private static void removeCountry(String countryName, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("remove", countryName)));
	}

	/**
	 * This method adds a country.
	 * 
	 * @param countryName   Country to be added
	 * @param continentName Continent in which the country is to be added
	 * @param stack         Stack
	 */
	private static void addCountry(String countryName, String continentName, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || continentName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("add", countryName, continentName)));
	}

	/**
	 * This method is used to edit continent
	 * 
	 * @param command Command given by the user
	 */
	public void editContinent(String[] command) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> stack = new ArrayList<ArrayList<String>>();
		int len = command.length;
		if (len < 2) {
			print("Invalid Command. Cannot find '-add' or '-remove'.");
			good = false;
			return;
		}

		for (int i = 1; i < len && good; i++) { // starting from 1 because 0th index was editcontinent
			String word = command[i];
			if (word.charAt(0) == '-') {
				switch (word.substring(1)) {
				case "add": {
					if (i + 2 < len) {
						String continentName = command[++i], continentValue = command[++i];
						addContinent(continentName, continentValue, stack);
					} else {
						print("Invalid Command. Cannot find 'continentname' or 'continentvalue'.");
						good = false;
						return;
					}
					break;
				}
				case "remove": {
					if (i + 1 < len) {
						String continentName = command[++i];
						removeContinent(continentName, stack);
					} else {
						print("Invalid Command. Cannot find 'continentname'.");
						good = false;
						return;
					}
					break;
				}
				default: {
					print("Invalid Command. Cannot find '-add' or '-remove'.");
					good = false;
					return;
				}

				}
			} else {
				print("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}

		if (good) {
			executeStack("editcontinent", stack);
		}

	}

	/**
	 * This method removes a continent.
	 * 
	 * @param continentName Continent to be removed
	 * @param stack         Stack
	 */
	private static void removeContinent(String continentName, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (continentName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("remove", continentName)));
	}

	/**
	 * This method adds a continent.
	 * 
	 * @param continentName  Continent to be added
	 * @param continentValue Continent Value
	 * @param stack          Stack
	 */
	private static void addContinent(String continentName, String continentValue, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (continentName.charAt(0) == '-' || continentValue.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("add", continentName, continentValue)));
	}

	/**
	 * This method executes command stacks of sub-commands
	 * 
	 * @param cmd Command
	 * @param stack ArrayList of ArrayList of string to store all commands
	 */
	private void executeStack(String cmd, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		switch (cmd) {
		case "editcontinent": {
			for (int i = 0; i < stack.size(); i++) {
				ArrayList<String> s = stack.get(i);

				if (s.get(0).equals("add")) {

					// to check whether string is number or not; this case is only for 'add'
					try {
						Integer.parseInt(s.get(2));
					} catch (NumberFormatException e) {
						print("Invalid Command. 'continentvalue' should be a number.");
						good = false;
						return;
					} catch (NullPointerException e) {
						print("Invalid Command. 'continentvalue' should be a number.");
						good = false;
						return;
					}
					if (map.getContinentFromName(s.get(1)) != null) {
						print("Continent Already Exists.");
						return;
					}

					Continent continent = new Continent();
					continent.setName(s.get(1));
					continent.setContinentValue(Integer.parseInt(s.get(2)));
					continent.setContinentIndexInListOfContinent(map.getListOfContinent().size());

					map.getListOfContinent().add(continent);
					print("Added Continent: "+continent.getName());

				} else if (s.get(0).equals("remove")) {
					Continent continent = map.getContinentFromName(s.get(1));

					if (continent == null) {
						print("Continent Not Found.");
						return;
					}
					ArrayList<ArrayList<String>> s1 = new ArrayList<ArrayList<String>>();
					// removing all countries
					for (String countryName : continent.getCountries()) {
						s1.add(new ArrayList<>(Arrays.asList("remove", countryName)));
					}
					executeStack("editcountry", s1);

					// removing continent from listOfContinent
					map.getListOfContinent().remove(continent);

					print("Removed Continent: "+continent.getName());
				}
			}
			break;
		}
		case "editcountry": {
			for (int i = 0; i < stack.size(); i++) {
				ArrayList<String> s = stack.get(i);

				if (s.get(0).equals("add")) {
					Continent continent;

					if (map.getCountryFromName(s.get(1)) != null) {
						print("Country Already Exists.");
						return;
					} else if ((continent = map.getContinentFromName(s.get(2))) == null) {
						print("Continent Not Found.");
						return;
					}

					Country count = new Country();
					count.setName(s.get(1));
					count.setContinentName(continent.getName());
					// adding country name in continent
					continent.getCountries().add(count.getName());
					// adding country in listOfCountries
					map.getListOfCountries().add(count);

					print("Added Country: "+count.getName()+" To: "+count.getContinentName());
				} else if (s.get(0).equals("remove")) {
					Country country = map.getCountryFromName(s.get(1));

					if (country == null) {
						print("Country Not Found.");
						return;
					}

					ArrayList<ArrayList<String>> s1 = new ArrayList<ArrayList<String>>();
					// removing all neighbors and bridges(implicitly)
					for (String neighborName : country.getNeighbors()) {

						s1.add(new ArrayList<>(Arrays.asList("remove", country.getName(), neighborName)));
					}
					executeStack("editneighbor", s1);

					// removing country from listOfCountries
					map.getListOfCountries().remove(country);
					// removing country name from listOfContinents
					map.getContinentFromName(country.getContinentName()).getCountries().remove(country.getName());

					print("Removed Country: "+country.getName()+" From: "+country.getContinentName());
				}
			}
			break;
		}
		case "editneighbor": {

			for (int i = 0; i < stack.size(); i++) {

				ArrayList<String> s = stack.get(i);

				// checking country existence

				Country count, neig;

				if ((count = map.getCountryFromName(s.get(1))) == null) {
					print("Country Not Found.");
					return;
				} else if ((neig = map.getCountryFromName(s.get(2))) == null) {
					print("Neighbor Country Not Found.");
					return;
				}

				boolean link = count.getNeighbors().contains(neig.getName());

				String countContinent = count.getContinentName();
				String neigContinent = neig.getContinentName();

				if (s.get(0).equals("add")) {
					if (link) {
						// link already exists

						print("Given Countries Are Already Neighbors.");

						good = false;
						return;

					}

					// if not neighbors, creating link

					count.getNeighbors().add(neig.getName());
					neig.getNeighbors().add(count.getName());

					// if different continents, create a bridge also
					if (countContinent != neigContinent) {

						createBridge(countContinent, neigContinent, count.getName(), neig.getName());

					}

					print("Added Neighbors: "+count.getName()+", "+neig.getName());
				} else if (s.get(0).equals("remove")) {

					if (!link) {
						// link not found
						print("Given Countries Are Not Neighbors.");
						good = false;
						return;

					}

					// removing link
					count.getNeighbors().remove(neig.getName());
					neig.getNeighbors().remove(count.getName());

					// if different continents, remove the bridge also
					if (!count.getContinentName().equals(neig.getContinentName())) {

						removeBridge(countContinent, neigContinent, count.getName(), neig.getName());

					}

					print("Removed Neighbors: "+count.getName()+", "+neig.getName());
				}

			}

			break;
		}
		default: {
			print("Error!!!");
		}
		}

	}

	private static void print(String string) {
		// TODO Auto-generated method stub
		if(print) {
			System.out.println(string);
		}
	}

	/**
	 * This method removes the bridge between two continents
	 * 
	 * @param continent1Name Name of first Continent
	 * @param continent2Name Name of second Continent
	 * @param country1Name   Name of first Country
	 * @param country2Name   Name of second Country
	 * @return true if removed else false
	 */
	public boolean removeBridge(String continent1Name, String continent2Name, String country1Name,
			String country2Name) {
		// TODO Auto-generated method stub
		// check all bridge in first continent

		Continent continent1, continent2;
		continent1 = map.getContinentFromName(continent1Name);
		continent2 = map.getContinentFromName(continent2Name);

		for (int i = 0; i < continent1.getBridges().size(); i++) {
			Bridge bridge = continent1.getBridges().get(i);
			if (bridge.getCountry1().equals(country1Name) && bridge.getCountry2().equals(country2Name)) {
				continent1.getBridges().remove(bridge);

			} else
				return !good;
		}

		// check all bridge in second continent
		for (int i = 0; i < continent2.getBridges().size(); i++) {
			Bridge bridge = continent2.getBridges().get(i);
			if (bridge.getCountry1().equals(country2Name) && bridge.getCountry2().equals(country1Name)) {
				continent2.getBridges().remove(bridge);

			} else
				return !good;
		}
		return good;
	}

	/**
	 * This method creates a bridge
	 * 
	 * @param continent1Name Name of first continent
	 * @param continent2Name Name of second continent
	 * @param country1Name   Name of first country
	 * @param country2Name   Name of second country
	 */
	private void createBridge(String continent1Name, String continent2Name, String country1Name, String country2Name) {
		// TODO Auto-generated method stub
		Bridge bridgeA2B = new Bridge(continent2Name, country1Name, country2Name);
		Bridge bridgeB2A = new Bridge(continent1Name, country2Name, country1Name);
		map.getContinentFromName(continent1Name).getBridges().add(bridgeA2B);
		map.getContinentFromName(continent2Name).getBridges().add(bridgeB2A);
	}

}
