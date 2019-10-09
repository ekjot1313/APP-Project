package mapWorks;

import Game.Bridge;
import Game.Continent;
import Game.Country;
import Game.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Game.MapReader;

/**
 * Temporary class for user driven commands to edit map. This will be called
 * when user will enter 'editmap' command
 * 
 * @author ekjot
 *
 */

// pending: ,removeContinent(),  removeCountry() , showMap(), saveMap()

public class MapEditor {
	public static boolean good;
	public static Map map;

	//public static void main(String args[]) throws IOException {
	public Map mapeditorInit(Map map) throws IOException {
		BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));
		
		if(map == null)
		map = new Map();
		else
			this.map = map;

		String command[];
		good = true;

		command = brConsole.readLine().split(" ");

		while (!command[0].equals("savemap")) {

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
				showMap();
		

				break;
			}

			default: {
				System.out.println("Invalid Command. Type Again.");
			}
			}

			command = brConsole.readLine().split(" ");
			good = true;

		}

		System.out.println("savemap found");
		MapSaver ms = new MapSaver();
		ms.saveMap(map, command[1]);
		return map;

	}

	/**
	 * This method will display the map
	 */
	private static void showMap() {
		// TODO Auto-generated method stub

		(new MapReader()).display(map);

	}

	/**
	 * This method will edit neighbour
	 * 
	 * @param command
	 */
	private static void editNeighbor(String[] command) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> stack = new ArrayList<ArrayList<String>>();
		int len = command.length;
		if (len < 2) {
			System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
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
						System.out.println("Invalid Command. Cannot find 'countryname' or 'neighborcountryname'.");
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
						System.out.println("Invalid Command. Cannot find 'countryname' or 'neighborcountryname'.");
						good = false;
						return;
					}
					break;
				}
				default: {
					System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
					good = false;
					return;
				}

				}
			} else {
				System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}

		if (good) {
			executeStack("editneighbor", stack);
		} else {
			System.out.println("Error!!!");
		}
	}

	/**
	 * This method will remove a neighbor country.
	 * 
	 * @param countryName
	 * @param neighborCountryName
	 * @param stack
	 */
	private static void removeNeighbor(String countryName, String neighborCountryName,
			ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("remove", countryName, neighborCountryName)));
		System.out.println("remove: " + countryName + " , " + neighborCountryName);

	}

	/**
	 * This method will add a neighbor country.
	 * 
	 * @param countryName
	 * @param neighborCountryName
	 * @param stack
	 */
	private static void addNeighbor(String countryName, String neighborCountryName,
			ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("add", countryName, neighborCountryName)));
		System.out.println("add: " + countryName + " , " + neighborCountryName);
	}

	/**
	 * This method will edit country
	 * 
	 * @param command
	 */
	private static void editCountry(String[] command) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> stack = new ArrayList<ArrayList<String>>();
		int len = command.length;
		if (len < 2) {
			System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
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
						System.out.println("Invalid Command. Cannot find 'countryname' or 'continentname'.");
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
						System.out.println("Invalid Command. Cannot find 'countryname'.");
						good = false;
						return;
					}
					break;
				}
				default: {
					System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
					good = false;
					return;
				}

				}
			} else {
				System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}
		if (good) {
			executeStack("editcountry", stack);
		}

	}

	/**
	 * This method will remove a country.
	 * 
	 * @param countryName
	 * @param stack
	 */
	private static void removeCountry(String countryName, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("remove", countryName)));
		System.out.println("remove: " + countryName);

	}

	/**
	 * This method will add a country.
	 * 
	 * @param countryName
	 * @param continentName
	 * @param stack
	 */

	private static void addCountry(String countryName, String continentName, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || continentName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("add", countryName, continentName)));
		System.out.println("add: " + countryName + " , " + continentName);

	}

	/**
	 * This method will edit continent
	 * 
	 * @param command
	 */
	private static void editContinent(String[] command) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> stack = new ArrayList<ArrayList<String>>();
		int len = command.length;
		if (len < 2) {
			System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
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
						System.out.println("Invalid Command. Cannot find 'continentname' or 'continentvalue'.");
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
						System.out.println("Invalid Command. Cannot find 'continentname'.");
						good = false;
						return;
					}
					break;
				}
				default: {
					System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
					good = false;
					return;
				}

				}
			} else {
				System.out.println("Invalid Command. Cannot find '-add' or '-remove'.");
				good = false;
				return;
			}

		}

		if (good) {
			executeStack("editcontinent", stack);
		}

	}

	/**
	 * This method will remove a continent.
	 * 
	 * @param continentName
	 * @param stack
	 */
	private static void removeContinent(String continentName, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (continentName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("remove", continentName)));
		System.out.println("remove: " + continentName);

	}

	/**
	 * This method will add a continent.
	 * 
	 * @param continentName
	 * @param continentValue
	 * @param stack
	 */
	private static void addContinent(String continentName, String continentValue, ArrayList<ArrayList<String>> stack) {
		// TODO Auto-generated method stub
		if (continentName.charAt(0) == '-' || continentValue.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		stack.add(new ArrayList<>(Arrays.asList("add", continentName, continentValue)));
		System.out.println("add: " + continentName + " , " + continentValue);
	}

	/**
	 * This method will excute command stacks of sub-commands
	 * 
	 * @param cmd
	 * @param stk
	 */
	private static void executeStack(String cmd, ArrayList<ArrayList<String>> stack) {
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
						System.out.println("Invalid Command. 'continentvalue' should be a number.");
						good = false;
						return;
					} catch (NullPointerException e) {
						System.out.println("Invalid Command. 'continentvalue' should be a number.");
						good = false;
						return;
					}

					if (map.getContinentFromName(s.get(1)) != null) {
						System.out.println("Continent Already Exists.");
						return;
					}

					Continent cont = new Continent();
					cont.setName(s.get(1));
					cont.setContinentValue(Integer.parseInt(s.get(2)));
					cont.setContinentIndexInListOfContinent(map.getListOfContinent().size());

					map.getListOfContinent().add(cont);

				} else if (s.get(0).equals("remove")) {
					Continent continent = map.getContinentFromName(s.get(1));

					if (continent == null) {
						System.out.println("Continent Not Found.");
						return;
					}

					/////////////////////////////////////////// pending

					ArrayList<ArrayList<String>> s1 = new ArrayList<ArrayList<String>>();
					// removing all countries
					for (String countryName : continent.getCountries()) {
						s1.add(new ArrayList<>(Arrays.asList("remove", countryName)));
					}
					executeStack("editcountry", s1);

					// removing continent from listOfContinent
					map.getListOfContinent().remove(continent);

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
						System.out.println("Country Already Exists.");
						return;
					} else if ((continent = map.getContinentFromName(s.get(2))) == null) {
						System.out.println("Continent Not Found.");
						return;
					}

					Country count = new Country();
					count.setName(s.get(1));
					count.setContinentName(continent.getName());

					// adding country name in continent
					continent.getCountries().add(count.getName());
					// adding country in listOfCountries
					map.getListOfCountries().add(count);

				} else if (s.get(0).equals("remove")) {
					Country country = map.getCountryFromName(s.get(1));

					if (country == null) {
						System.out.println("Country Not Found.");
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
					System.out.println("Country Not Found.");
					return;
				} else if ((neig = map.getCountryFromName(s.get(2))) == null) {
					System.out.println("Neighbor Country Not Found.");
					return;
				}

				boolean link = count.getNeighbors().contains(neig.getName());

				String countContinent = count.getContinentName();
				String neigContinent = neig.getContinentName();

				if (s.get(0).equals("add")) {
					if (link) {
						// link already exists

						System.out.println("Given Countries Are Already Neighbors.");

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

				} else if (s.get(0).equals("remove")) {

					if (!link) {
						// link not found
						System.out.println("Given Countries Are Not Neighbors.");
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

				}

			}

			break;
		}
		default: {
			System.out.println("Error!!!");
		}
		}

	}

	private static void removeBridge(String continent1Name, String continent2Name, String country1Name,
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

			}
		}

		// check all bridge in second continent
		for (int i = 0; i < continent2.getBridges().size(); i++) {
			Bridge bridge = continent2.getBridges().get(i);
			if (bridge.getCountry1().equals(country2Name) && bridge.getCountry2().equals(country1Name)) {
				continent2.getBridges().remove(bridge);

			}
		}

	}

	/**
	 * This method creates a bridge
	 * 
	 * @param continent1Name
	 * @param continent2Name
	 * @param count
	 * @param country2Name
	 */
	private static void createBridge(String continent1Name, String continent2Name, String country1Name,
			String country2Name) {
		// TODO Auto-generated method stub
		Bridge bridgeA2B = new Bridge(continent2Name, country1Name, country2Name);
		Bridge bridgeB2A = new Bridge(continent1Name, country2Name, country1Name);
		map.getContinentFromName(continent1Name).getBridges().add(bridgeA2B);
		map.getContinentFromName(continent2Name).getBridges().add(bridgeB2A);
	}

	/**
	 * This method find country index from listofcountry with its name
	 * 
	 * @param countName
	 * @param listOfCountries
	 * @return
	 */
	private static int findCountInd(String countName, List<Country> listOfCountries) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listOfCountries.size(); i++) {
			if (listOfCountries.get(i).getName().equals(countName)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method find continent index from listofcontinent with its name
	 * 
	 * @param contName
	 * @param listOfContinent
	 * @return
	 */
	private static int findContInd(String contName, List<Continent> listOfContinent) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listOfContinent.size(); i++) {
			if (listOfContinent.get(i).getName().equals(contName)) {
				return i;
			}
		}
		return -1;
	}

}
