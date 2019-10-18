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
	 * flag to store true if flow is good
	 */
	public boolean good;
	/**
	 * flag to allow print or not
	 */
	public boolean print;
	/**
	 * to store map object
	 */
	public Map map;

	/**
	 * Default Constructor
	 */
	public MapEditor() {
		this.good = true;
		this.print = true;
		this.map = new Map();
	}

	/**
	 * This method initializes map editor
	 * 
	 * @param map Map Object
	 * @return Map object
	 * @throws IOException BufferedReader used for user input
	 */
	public Map mapEditorInit(Map map) throws IOException {
		BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));

		// if passed map is null then create new map otherwise copy map
		if (map == null)
			this.map = new Map();
		else
			this.map = map;

		String command[];
		command = brConsole.readLine().split(" ");

		// loop to read user commands; exits only on 'savemap' command
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

					// validate and save map; if map is invalid, prompt user to edit map
					MapReader mr = new MapReader();
					mr.map = this.map;
					if (mr.validateMap(mr.map) == 0 && mr.validateContinent(mr.map) == 0) {
						(new MapSaver()).saveMap(mr.map, command[1]);

						// map successfully edited and saved
						return mr.map;
					} else {
						print("The map file cannot be saved as the map is not connected");

					}

					good = false; // to break loop
					continue; // to skip next lines
				} else {
					print("Invalid Command. Type Again.");
				}
				break;
			}
			default: {
				print("Invalid Command. Type Again.");
			}
			}

			// if invalid command found, prompt user again to enter valid command
			command = brConsole.readLine().split(" ");
			good = true;

		}

		// return null map if editing is unsuccessfull.
		return null;

	}

	/**
	 * This method is used to read editcontinent command, store sub-commands to
	 * queue, and execute all sub-commands
	 * 
	 * @param command Command given by the user
	 */
	public void editContinent(String[] command) {

		// to store sub-commands
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();

		// if command is incomplete; prompt user to type new command
		int len = command.length;
		if (len < 2) {
			print("Invalid Command. Cannot find '-add' or '-remove'.");
			good = false;
			return;
		}

		// to read sub-commands
		for (int i = 1; i < len && good; i++) { // starting from 1 because 0th index was 'editcontinent'
			String tag = command[i];
			if (tag.charAt(0) == '-') {

				// finding tag
				switch (tag.substring(1)) {
				case "add": {
					if (i + 2 < len) {
						String continentName = command[++i], continentValue = command[++i];

						// adding sub-command to queue
						addContinentToQueue(continentName, continentValue, queue);
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

						// adding sub-command to queue
						removeContinentToQueue(continentName, queue);
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
			}
			// if cannot find tag
			else {
				print("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}

		// after storing all sub-commands in queue, execute them
		if (good) {
			executeQueue("editcontinent", queue);
		}

	}

	/**
	 * This method is used to read editcountry command, store sub-commands to queue,
	 * and execute all sub-commands
	 * 
	 * @param command Command given be the user
	 */
	public void editCountry(String[] command) {

		// to store sub-commands
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();

		// if command is incomplete; prompt user to type new command
		int len = command.length;
		if (len < 2) {
			print("Invalid Command. Cannot find '-add' or '-remove'.");
			return;
		}

		// to read sub-commands
		for (int i = 1; i < len && good; i++) { // starting from 1 because 0th index was editcountry
			String tag = command[i];
			if (tag.charAt(0) == '-') {

				// finding tag
				switch (tag.substring(1)) {
				case "add": {
					if (i + 2 < len) {
						String countryName = command[++i], continentName = command[++i];

						// adding sub-command to queue
						addCountryToQueue(countryName, continentName, queue);
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

						// adding sub-command to queue
						removeCountryToQueue(countryName, queue);
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
			}
			// if cannot find tag
			else {
				print("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}

		// after storing all sub-commands in queue, execute them
		if (good) {
			executeQueue("editcountry", queue);
		}

	}

	/**
	 * This method is used to read editneighbor command, store sub-commands to
	 * queue, and execute all sub-commands
	 * 
	 * @param command Command given the user
	 */
	public void editNeighbor(String[] command) {

		// to store sub-commands
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();

		// if command is incomplete; prompt user to type new command
		int len = command.length;
		if (len < 2) {
			print("Invalid Command. Cannot find '-add' or '-remove'.");
			return;
		}

		// to read sub-commands
		for (int i = 1; i < len && good; i++) { // starting from 1 because 0th index was editneighbor
			String tag = command[i];
			if (tag.charAt(0) == '-') {

				// finding tag
				switch (tag.substring(1)) {
				case "add": {
					if (i + 2 < len) {
						String countryName = command[++i], neighborCountryName = command[++i];

						// adding sub-command to queue
						addNeighborToQueue(countryName, neighborCountryName, queue);
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

						// adding sub-command to queue
						removeNeighborToQueue(countryName, neighborCountryName, queue);
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
			}

			// if cannot find tag
			else {
				print("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}

		// after storing all sub-commands in queue, execute them
		if (good) {
			executeQueue("editneighbor", queue);
		}
	}

	/**
	 * This method adds sub-command, of adding continent, to queue.
	 * 
	 * @param continentName  Continent to be added
	 * @param continentValue Continent Value
	 * @param queue          queue
	 */
	private void addContinentToQueue(String continentName, String continentValue, ArrayList<ArrayList<String>> queue) {

		// if country name is actually a tag
		if (continentName.charAt(0) == '-' || continentValue.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}

		// add valid sub-command to queue
		queue.add(new ArrayList<>(Arrays.asList("add", continentName, continentValue)));
	}

	/**
	 * This method adds sub-command, of removing continent, to queue.
	 * 
	 * @param continentName Continent to be removed
	 * @param queue         queue
	 */
	private void removeContinentToQueue(String continentName, ArrayList<ArrayList<String>> queue) {

		// if country name is actually a tag
		if (continentName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}

		// add valid sub-command to queue
		queue.add(new ArrayList<>(Arrays.asList("remove", continentName)));
	}

	/**
	 * This method adds sub-command, of adding country, to queue.
	 * 
	 * @param countryName   Country to be added
	 * @param continentName Continent in which the country is to be added
	 * @param queue         queue
	 */
	private void addCountryToQueue(String countryName, String continentName, ArrayList<ArrayList<String>> queue) {

		// if country name is actually a tag
		if (countryName.charAt(0) == '-' || continentName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}

		// add valid sub-command to queue
		queue.add(new ArrayList<>(Arrays.asList("add", countryName, continentName)));
	}

	/**
	 * This method adds sub-command, of removing country, to queue.
	 * 
	 * @param countryName Country to be removed
	 * @param queue       queue
	 */
	private void removeCountryToQueue(String countryName, ArrayList<ArrayList<String>> queue) {

		// if country name is actually a tag
		if (countryName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}

		// add valid sub-command to queue
		queue.add(new ArrayList<>(Arrays.asList("remove", countryName)));
	}

	/**
	 * This method adds sub-command, of adding neighbors, to queue.
	 * 
	 * @param countryName         Country Name
	 * @param neighborCountryName Neighboring country to be added
	 * @param queue               queue
	 */
	private void addNeighborToQueue(String countryName, String neighborCountryName,
			ArrayList<ArrayList<String>> queue) {

		// if country name is actually a tag
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}

		// add valid sub-command to queue
		queue.add(new ArrayList<>(Arrays.asList("add", countryName, neighborCountryName)));
	}

	/**
	 * This method adds sub-command, of removing neighbors, to queue.
	 * 
	 * @param countryName         Country Name
	 * @param neighborCountryName Neighboring country to be removed
	 * @param queue               queue
	 */
	private void removeNeighborToQueue(String countryName, String neighborCountryName,
			ArrayList<ArrayList<String>> queue) {

		// if country name is actually a tag
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			print("Invalid Command. Type Again.");
			good = false;
			return;
		}

		// add valid sub-command to queue
		queue.add(new ArrayList<>(Arrays.asList("remove", countryName, neighborCountryName)));
	}

	/**
	 * This method executes queue of sub-commands
	 * 
	 * @param cmd   Command
	 * @param queue ArrayList of ArrayList of string to store all commands
	 */
	private void executeQueue(String cmd, ArrayList<ArrayList<String>> queue) {

		// check type of command
		switch (cmd) {
		case "editcontinent": {
			// for all sub-commands
			for (int i = 0; i < queue.size(); i++) {
				ArrayList<String> s = queue.get(i);

				// if tag is add
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

					// creating new continent object and adding properties
					Continent continent = new Continent();
					continent.setName(s.get(1));
					continent.setContinentValue(Integer.parseInt(s.get(2)));
					continent.setContinentIndexInListOfContinent(map.getListOfContinent().size());

					// adding continent object to map
					map.getListOfContinent().add(continent);
					print("Added Continent: " + continent.getName());

				}

				// if tag is remove
				else if (s.get(0).equals("remove")) {

					// getting continent object from map
					Continent continent = map.getContinentFromName(s.get(1));

					if (continent == null) {
						print("Continent Not Found.");
						return;
					}

					// creating sub-commands to remove all countries in continent, and storing in
					// sub-queue
					ArrayList<ArrayList<String>> subqueue = new ArrayList<ArrayList<String>>();
					for (String countryName : continent.getCountries()) {
						subqueue.add(new ArrayList<>(Arrays.asList("remove", countryName)));
					}

					
					// executing all sub-commands
					MapEditor me=new MapEditor();
					me.map=this.map;
					me.print=false; // not to print sub edits
					me.executeQueue("editcountry", subqueue);
					
					
					// removing empty continent from map
					map.getListOfContinent().remove(continent);

					print("Removed Continent: " + continent.getName());
				}
			}
			break;
		}
		case "editcountry": {

			// for all sub-commands
			for (int i = 0; i < queue.size(); i++) {
				ArrayList<String> s = queue.get(i);

				// if tag is add
				if (s.get(0).equals("add")) {

					// creating continent object to link parent continent of country
					Continent parentContinent;

					if (map.getCountryFromName(s.get(1)) != null) {
						print("Country Already Exists.");
						return;
					} else if ((parentContinent = map.getContinentFromName(s.get(2))) == null) {
						print("Continent Not Found.");
						return;
					}

					// creating new country object and adding properties
					Country count = new Country();
					count.setName(s.get(1));
					count.setContinentName(parentContinent.getName());

					// adding country name in continent
					parentContinent.getCountries().add(count.getName());

					// adding country in map
					map.getListOfCountries().add(count);

					print("Added Country: " + count.getName() + " To: " + count.getContinentName());
				}

				// if tag is remove
				else if (s.get(0).equals("remove")) {

					// getting continent object from map
					Country country = map.getCountryFromName(s.get(1));

					if (country == null) {
						print("Country Not Found.");
						return;
					}

					// creating sub-commands to remove all neighbors of country, and
					// storing in sub-queue
					ArrayList<ArrayList<String>> subqueue = new ArrayList<ArrayList<String>>();
					for (String neighborName : country.getNeighbors()) {
						subqueue.add(new ArrayList<>(Arrays.asList("remove", country.getName(), neighborName)));
					}

					
					// executing all sub-commands
					MapEditor me=new MapEditor();
					me.map=this.map;
					me.print=false; // not to print sub edits
					me.executeQueue("editneighbor", subqueue);
					

					// removing empty country from map
					map.getListOfCountries().remove(country);

					// removing country name from parent continent
					map.getContinentFromName(country.getContinentName()).getCountries().remove(country.getName());

					print("Removed Country: " + country.getName() + " From: " + country.getContinentName());
				}
			}
			break;
		}
		case "editneighbor": {

			// for all sub-commands
			for (int i = 0; i < queue.size(); i++) {
				ArrayList<String> s = queue.get(i);

				// checking countries existence
				Country count, neig;
				if ((count = map.getCountryFromName(s.get(1))) == null) {
					print("Country Not Found.");
					return;
				} else if ((neig = map.getCountryFromName(s.get(2))) == null) {
					print("Neighbor Country Not Found.");
					return;
				}

				// find if there is link between them
				boolean link = count.getNeighbors().contains(neig.getName());

				String countContinent = count.getContinentName();
				String neigContinent = neig.getContinentName();

				// if tag is add
				if (s.get(0).equals("add")) {

					// link already exists
					if (link) {
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

					print("Added Neighbors: " + count.getName() + ", " + neig.getName());
				}

				// if tag is remove
				else if (s.get(0).equals("remove")) {

					// if link not found
					if (!link) {
						print("Given Countries Are Not Neighbors.");
						good = false;
					}

					// removing link
					count.getNeighbors().remove(neig.getName());
					neig.getNeighbors().remove(count.getName());

					// if different continents, remove the bridge also
					if (!count.getContinentName().equals(neig.getContinentName())) {
						removeBridge(countContinent, neigContinent, count.getName(), neig.getName());
					}

					print("Removed Neighbors: " + count.getName() + ", " + neig.getName());
				}

			}

			break;
		}
		default: {
			print("Error!!!");
		}
		}

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
	 * This method is used to print some string if print flag is set to true
	 * 
	 * @param string String to print
	 */
	private void print(String string) {
		// TODO Auto-generated method stub
		if (print) {
			System.out.println(string);
		}
	}

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

}
