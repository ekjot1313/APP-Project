package mapWorks;

import Game.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
	public static ArrayList<String[]>stack=new ArrayList<String[]>();

	public static void main(Map m) throws IOException {
		map = m;

		BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));

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
						addNeighbor(countryName, neighborCountryName);
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
						removeNeighbor(countryName, neighborCountryName);
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

	}

	/**
	 * This method will remove a neighbor country.
	 * 
	 * @param countryName
	 * @param neighborCountryName
	 */
	private static void removeNeighbor(String countryName, String neighborCountryName) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}

		System.out.println("remove: " + countryName + " , " + neighborCountryName);
		
		
		
		
	}

	/**
	 * This method will add a neighbor country.
	 * 
	 * @param countryName
	 * @param neighborCountryName
	 */
	private static void addNeighbor(String countryName, String neighborCountryName) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || neighborCountryName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}

		System.out.println("add: " + countryName + " , " + neighborCountryName);
	}

	/**
	 * This method will edit country
	 * 
	 * @param command
	 */
	private static void editCountry(String[] command) {
		// TODO Auto-generated method stub
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
						addCountry(countryName, continentName);
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
						removeCountry(countryName);
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

	}

	/**
	 * This method will remove a country.
	 * 
	 * @param countryName
	 */
	private static void removeCountry(String countryName) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}

		System.out.println("remove: " + countryName);

	}

	/**
	 * This method will add a country.
	 * 
	 * @param countryName
	 * @param continentName
	 */

	private static void addCountry(String countryName, String continentName) {
		// TODO Auto-generated method stub
		if (countryName.charAt(0) == '-' || continentName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}

		System.out.println("add: " + countryName + " , " + continentName);

	}

	/**
	 * This method will edit continent
	 * 
	 * @param command
	 */
	private static void editContinent(String[] command) {
		// TODO Auto-generated method stub
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
						addContinent(continentName, continentValue);
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
						removeContinent(continentName);
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

	}

	/**
	 * This method will remove a continent.
	 * 
	 * @param continentName
	 */
	private static void removeContinent(String continentName) {
		// TODO Auto-generated method stub
		if (continentName.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		System.out.println("remove: " + continentName);

	}

	/**
	 * This method will add a continent.
	 * 
	 * @param continentName
	 * @param continentValue
	 */
	private static void addContinent(String continentName, String continentValue) {
		// TODO Auto-generated method stub
		if (continentName.charAt(0) == '-' || continentValue.charAt(0) == '-') {
			System.out.println("Invalid Command. Type Again.");
			good = false;
			return;
		}
		System.out.println("add: " + continentName + " , " + continentValue);
	}

}
