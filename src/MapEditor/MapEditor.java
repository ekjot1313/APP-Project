package MapEditor;
/**
 * Temporary class for user driven commands to edit map.
 * This will be called when user will enter 'editmap' command

 * @author ekjot
 *
 */

import java.io.*;

public class MapEditor {

	public static void main(String[] args) throws IOException {

		BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));

		String command[];

		int len;

		command = brConsole.readLine().split(" ");
		len = command.length;

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

	private static Object readCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void showMap() {
		// TODO Auto-generated method stub

	}

	private static void editNeighbour(String[] command) {
		// TODO Auto-generated method stub

	}

	private static void editCountry(String[] command) {
		// TODO Auto-generated method stub

	}

	private static void editContinent(String[] command) {
		// TODO Auto-generated method stub

	}

}
