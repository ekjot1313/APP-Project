import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import mapWorks.MapReader;
import dao.Player;
import game.ArmyAllocator;
import game.GamePlay;
import game.PlayerAllocator;
import mapWorks.MapEditor;

/**
 * This is the main class
 * 
 * @author Mitalee Naik
 * @since 1.0.0
 *
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("Welcome to RISK GAME!");
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println(
					"Type \nloadmap <filename> -load an existing map \neditmap <filename> -edit an existing map or create a new map");
			String[] commands = sc.nextLine().split(" ");
			if (commands.length == 1 && !commands[0].equals("exit")) {
				System.out.println("Invalid command .Type exit to end game");
			} else {
				switch (commands[0]) {
				case "loadmap":
					loadmap(commands[1]);
					break;
				case "editmap":
					editmap(commands[1]);
					break;
				case "exit":
					sc.close();
					return;
				default:
					System.out.println("Invalid command . Type exit to end game");
				}
			}
		}
	}

	/**
	 * This method is called when user gives 'editmap' command
	 * 
	 * @param filename Map file to be edited
	 */
	private static void editmap(String filename) {
		MapReader mr = new MapReader();
		MapEditor mpe = new MapEditor();
		System.out.println(System.getProperty("user.dir"));
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\" + filename;
		File newFile = new File(currentPath);
		if (newFile.exists()) {
			mr.parseMapFile(newFile);
			try {
				System.out.println("Type editcontinent -add <continentname> <continentvalue> -remove <continentname>");
				System.out.println("Type editcountry -add <countryname> <continentname> -remove <countryname>");
				System.out.println(
						"Type editneighbor -add <countryname> <neighborcountryname> -remove <countryname> <neighborcountryname>");
				System.out.println("Type showmap");
				System.out.println("Type validatemap");
				mr.map = mpe.mapeditorInit(mr.map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println(
					"Map file does not exist .New File created .\nType editcontinent -add <continentname> <continentvalue> -remove <continentname>");
			System.out.println("Type editcountry -add <countryname> <continentname> -remove <countryname>");
			System.out.println(
					"Type editneighbor -add <countryname> <neighborcountryname> -remove <countryname> <neighborcountryname>");
			System.out.println("Type showmap");
			System.out.println("Type validatemap");
			try {
				mr.map = mpe.mapeditorInit(null);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is called when user gives 'loadmap' command
	 * 
	 * @param filename Map file to be loaded
	 */
	private static void loadmap(String filename) {
		// TODO Auto-generated method stub
		MapReader mr = new MapReader();
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\";
		System.out.println(currentPath);
		currentPath += filename + ".map";
		File newFile = new File(currentPath);
		if (newFile.exists()) {
			int mapParseStatus = mr.parseMapFile(newFile);
			if (mapParseStatus == 0) {
				System.out.println("Map is loaded successfully.");
				gameplayer(mr);
			}

		} else {
			System.out.println("File Not found .");
		}
	}

	/**
	 * This method is called to add or remove a player, assign countries to players
	 * and allow them to place armies
	 * 
	 * @param mr MapReader Object
	 */
	private static void gameplayer(MapReader mr) {
		PlayerAllocator pa = new PlayerAllocator();
		pa.allocate(mr.map);
		pa.populateCountries(mr.map);
		placearmies(pa, mr);
	}

	/**
	 * This method allows user to place armies on their countries
	 * 
	 * @param pa PlayerAllocator Object
	 * @param mr MapReader Object
	 */
	private static void placearmies(PlayerAllocator pa, MapReader mr) {
		ArmyAllocator aa = new ArmyAllocator();
		aa.calculateTotalArmies((ArrayList<Player>) pa.listOfPlayers, mr.map, 0);
		GamePlay gp = new GamePlay();
		while (true) {
			for (int i = 0; i < pa.listOfPlayers.size(); i++) {
				System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " reinforcement phase begins");
				gp.reinforcement((ArrayList<Player>) pa.listOfPlayers, mr.map, i);
				gp.fortification((ArrayList<Player>) pa.listOfPlayers, mr.map, i);
			}
		}
	}
}
