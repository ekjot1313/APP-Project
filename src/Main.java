import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Game.ArmyAllocator;
import Game.MapReader;
import Game.Player;
import Game.PlayerAllocator;
import mapWorks.MapEditor;

public class Main {
	//private static MapReader mr;
	public static void main(String[] args) {
		
		System.out.println("Welcome to RISK GAME!");
		System.out.println("Type \nloadmap <filename> -load an existing map \neditmap <filename> -edit an existing map or create a new map");
		
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			String[] commands = sc.nextLine().split(" ");
			if(commands.length == 1 && !commands[0].equals("exit") ) {
				System.out.println("Invalid command .Type exit to end game");
			}
			else {
			switch(commands[0]) {
				case "loadmap":	
					loadmap(commands[1]);
					break;
				case "editmap":
					editmap(commands[1]);
					break;	

				case "exit":
					return;
					
				default:
					System.out.println("Invalid command . Type exit to end game");
					
				
			}
			}
			
		}
		
		/*
		 case "showmap":
					break;
				case "validatemap":
					break;
				case "savemap":
					break;
		 */
	}

	private static void editmap(String filename) {
		// TODO Auto-generated method stub
		
		MapReader mr = new MapReader();
		System.out.println(System.getProperty("user.dir"));
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\src\\Maps\\" + filename;
		File newFile = new File(currentPath);
		if(newFile.exists())
			mr.parseMapFile(newFile);
		else
		{
			System.out.println("Map file does not exist .New File created .\nType editcontinent -add <continentname> <continentvalue> -remove <continentname>");
			System.out.println("Type editcountry -add <countryname> <continentname> -remove <countryname>");
			System.out.println("Type editneighbor -add <countryname> <neighborcountryname> -remove <countryname> <neighborcountryname>");
			MapEditor mpe = new MapEditor();
			//call MapEditor -- MapEditor.map will be the map loaded.
		}
		
	}

	private static void loadmap(String filename) {
		// TODO Auto-generated method stub
		MapReader mr = new MapReader();
		System.out.println(System.getProperty("user.dir"));
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\src\\Maps\\" + filename;
		//mr.parseMapFile(currentPath);
		File newFile = new File(currentPath);
		if(newFile.exists()) {
			mr.parseMapFile(newFile);
			System.out.println("Map is loaded successfully");
			gameplayer(mr);
			
		}
		else
		{
			System.out.println("File Not found .");
		}
	}
	
	private static void gameplayer(MapReader mr) {
		
		PlayerAllocator pa = new PlayerAllocator();
		pa.map = mr.map;
		pa.allocate();
		placearmies(pa);
		
		
	}
	private static void placearmies (PlayerAllocator pa) {
		ArmyAllocator aa=new ArmyAllocator();
		aa.calculateTotalArmies((ArrayList<Player>) pa.listOfPlayers, pa.map);
	}
	
	

}
