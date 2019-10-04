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

// pending: addContinent(),removeContinent(), addCountry(), removeCountry(), addNeighbour(), removeNeighbour(), showMap(), saveMap()

public class MapEditor {
	public static boolean good;
	public static Map map;

	public static void main(String args[]) throws IOException {

		BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));

		map = new Map();

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

	}

	/**
	 * This method will display the map
	 */
	private static void showMap() {
		// TODO Auto-generated method stub
		// call mapPrinter
		System.out.println("showMap");

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
					Continent cont = new Continent();
					cont.setName(s.get(1));

					// to check whether string is number or not
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

					cont.setContinentValue(Integer.parseInt(s.get(2)));
					map.listOfContinent.add(cont);
					// (new MapReader()).display(map);

				} else if (s.get(0).equals("remove")) {

				}
			}
			break;
		}
		case "editcountry": {
			for (int i = 0; i < stack.size(); i++) {
				ArrayList<String> s = stack.get(i);
				
				Country count = new Country();
				count.setName(s.get(1));

				int contInd = findContInd(s.get(2), map.listOfContinent);

				if (contInd == -1) {
					System.out.println("Continent Not Found.");
					return;
				}

				count.setContinentName(map.listOfContinent.get(contInd));

				if (s.get(0).equals("add")) {
					

					map.listOfContinent.get(contInd).getCountries().add(count);

					map.listOfCountries.add(count);

					// (new MapReader()).display(map);

				} else if (s.get(0).equals("remove")) {
					
					for(int i=0;i<map.listOfCountries.get(coun))
					executeStack("editneighbor",(new ArrayList<>(Arrays.asList("remove", count, neig));

				}
			}
			break;
		}
		case "editneighbor": {
			for (int i = 0; i < stack.size(); i++) {
				ArrayList<String> s = stack.get(i);
				int countInd = findCountInd(s.get(1), map.listOfCountries);
				if (countInd == -1) {
					System.out.println("Country Not Found.");
					return;
				}
				int neigInd = findCountInd(s.get(2), map.listOfCountries);
				if (neigInd == -1) {
					System.out.println("Neighbor Country Not Found.");
					return;
				}

				Country count = map.listOfCountries.get(countInd);
				Country neig = map.listOfCountries.get(neigInd);

				int contInd1 = map.listOfContinent.indexOf(count.getContinentName());
				int contInd2 = map.listOfContinent.indexOf(neig.getContinentName());

				if (s.get(0).equals("add")) {

					map.listOfCountries.get(countInd).getNeighbours().add(neig);
					map.listOfCountries.get(neigInd).getNeighbours().add(count);

					// if different continents, create a bridge also
					if (!count.getContinentName().equals(neig.getContinentName())) {

						createBridge(contInd1, contInd2, count, neig);

						displayBridge();

					}

				} else if (s.get(0).equals("remove")) {

					// finding index of second country in first country's neighbor list
					int countInNeigInd = findCountInd(neig.getName(), count.getNeighbours());
					int neigInCountInd = findCountInd(count.getName(), neig.getNeighbours());

					// removing from neighbor list
					map.listOfCountries.get(countInd).getNeighbours().remove(countInNeigInd);
					map.listOfCountries.get(neigInd).getNeighbours().remove(neigInCountInd);

					// if different continents, remove the bridge also
					if (!count.getContinentName().equals(neig.getContinentName())) {

						removeBridge(contInd1, contInd2, count, neig);
						displayBridge();

					}
					
					map.listOfContinent.get(contInd1).countries.remove(count);
					//map.listOfContinent.get(contInd1).countries.

				}

			}
			(new MapReader()).display(map);
			break;
		}
		default: {
			System.out.println("Error!!!");
		}
		}

	}

	private static void displayBridge() {
		// TODO Auto-generated method stub
		for (Continent cont : map.listOfContinent) {
			for (Bridge bridge : cont.bridges) {
				System.out.println("bridge: " + cont.getName() + "-" + bridge.neigCont.getName() + "( "
						+ bridge.count1.name + " -> " + bridge.count2.name + " )");
			}

		}

	}

	private static void removeBridge(int contInd1, int contInd2, Country count, Country neig) {
		// TODO Auto-generated method stub
//check all bridge in first continent
		
		for (int i=0;i<map.listOfContinent.get(contInd1).bridges.size();i++){
			Bridge bridge=map.listOfContinent.get(contInd1).bridges.get(i);
			if (bridge.count1.equals(count) && bridge.count2.equals(neig)) {
				map.listOfContinent.get(contInd1).bridges.remove(bridge);
				
				
			}
		}
		
		// check all bridge in second continent
		for (int i=0;i<map.listOfContinent.get(contInd2).bridges.size();i++){
			Bridge bridge=map.listOfContinent.get(contInd2).bridges.get(i);
			if (bridge.count1.equals(neig) && bridge.count2.equals(count)) {
				map.listOfContinent.get(contInd2).bridges.remove(bridge);
				
				
			}
		}
		
	

	}

	/**
	 * This method creates a bridge
	 * 
	 * @param contInd1
	 * @param contInd2
	 * @param count
	 * @param neig
	 */
	private static void createBridge(int contInd1, int contInd2, Country count, Country neig) {
		// TODO Auto-generated method stub
		Bridge bridgeA2B = new Bridge(map.listOfContinent.get(contInd2), count, neig);
		Bridge bridgeB2A = new Bridge(map.listOfContinent.get(contInd1), neig, count);
		map.listOfContinent.get(contInd1).bridges.add(bridgeA2B);
		map.listOfContinent.get(contInd2).bridges.add(bridgeB2A);
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
