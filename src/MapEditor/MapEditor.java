package MapEditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Temporary class for user driven commands to edit map.
 * This will be called when user will enter 'editmap' command

 * @author ekjot
 *
 */

// pending: editContinent(), editCountry(), editNeighbour(), showMap(), saveMap()

public class MapEditor {

	public static void main(String[] args) throws IOException {

		BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));

		String command[];

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
				editNeighbour(command);
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

		}

		System.out.println("savemap found");

	}

	/**
	 * This method will display the map
	 */
	private static void showMap() {
		// TODO Auto-generated method stub

	}
/**
 * This method will edit neighbour
 * @param command
 */
	private static void editNeighbour(String[] command) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method will edit country
	 * @param command
	 */
	private static void editCountry(String[] command) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method will edit continent
	 * @param command
	 */
	private static void editContinent(String[] command) {
		// TODO Auto-generated method stub

	}

}
