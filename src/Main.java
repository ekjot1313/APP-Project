import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mapWorks.MapReader;
import view.PhaseView;
import dao.Map;
import dao.Player;
import game.ArmyAllocator;
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
	public static void main(String[] args) throws Exception {
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
		currentPath += "\\Maps\\" + filename+".map";
		File newFile = new File(currentPath);
		if (newFile.exists()) {
			mr.parseMapFile(newFile);
			try {
				editMapCommands();
				System.out.println("Type savemap");
				mr.map = mpe.mapEditorInit(mr.map);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Map file does not exist .New File created .");
			editMapCommands();
			try {
				mr.map = mpe.mapEditorInit(null);

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
	 * @throws Exception 
	 */
	private static void loadmap(String filename) throws Exception {
		// TODO Auto-generated method stub
		MapReader mr = new MapReader();
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\";
		System.out.println(currentPath);
		currentPath += filename + ".map";
		File newFile = new File(currentPath);
		if (newFile.exists()) {
			int mapParseStatus = mr.parseMapFile(newFile);
			// to check whether map is parsed successfully
			if (mapParseStatus == 1) {
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
	 * The game phases (reinforcement, attack and fortification) start from this function
	 * 
	 * @param mr MapReader Object
	 * @throws Exception 
	 */
	private static void gameplayer(MapReader mr) throws Exception {
		
		//Player p =new Player();
		//Create Deck of cards
		ArrayList<String> deck = createDeck(mr.map);
		System.out.println(deck.toString());
		Player.deck = deck;
		PlayerAllocator pa = new PlayerAllocator();
		ArmyAllocator aa = new ArmyAllocator();
		int gameOver=0;
		pa.allocate(mr.map);
		pa.populateCountries(mr.map);
		
		aa.calculateTotalArmies((ArrayList<Player>) pa.listOfPlayers, mr.map, 0);
		while (true) {
			for (int i = 0; i < pa.listOfPlayers.size(); i++) {
				System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " reinforcement phase begins");
				PhaseView pv= new PhaseView();
				pa.listOfPlayers.get(i).attach(pv);
				pa.listOfPlayers.get(i).reinforcement(mr.map,(ArrayList<Player>) pa.listOfPlayers);
				gameOver=pa.listOfPlayers.get(i).attack(mr.map,(ArrayList<Player>) pa.listOfPlayers);
				if(gameOver == 1)
					break;
				pa.listOfPlayers.get(i).fortification(mr.map,(ArrayList<Player>) pa.listOfPlayers);
			}
			if(gameOver == 1)
				break;
		}
		System.out.println("Game is Over");
		System.out.println("Winner is Player: "+pa.listOfPlayers.get(0).getName());
	}
	/**
	 * To create a deck of cards based on countries
	 * @param map
	 */
	private static ArrayList<String> createDeck(Map map) {
		// TODO Auto-generated method stub
		List<String> typeOfCards = new ArrayList<String>();
		typeOfCards.add("infantry");
		typeOfCards.add("cavalry");
		typeOfCards.add("artillery");
		int numberofCountries = map.getListOfCountries().size();
		int equalDis = numberofCountries/3;
		int iCounter= equalDis;
		int cCounter = equalDis;
		int aCounter = equalDis;
		ArrayList<String> cardList =  new ArrayList<String>();

		Random r = new Random();
		for(int i=0;i< equalDis*3;i++){
			String card;
			card = map.getListOfCountries().get(i).getName();
			int j= r.nextInt(typeOfCards.size());
			card += " "+typeOfCards.get(j);
			cardList.add(card);
			if(typeOfCards.get(j).equals("infantry") ) {
				iCounter++;
				if(iCounter == equalDis)
					typeOfCards.remove("infantry");
			}
			if(typeOfCards.get(j).equals("cavalry") ) {
				cCounter++;
				if(cCounter == equalDis)
					typeOfCards.remove("cavalry");
			}
			if(typeOfCards.get(j).equals("artillery") ) {
				aCounter++;
				if(aCounter == equalDis)
					typeOfCards.remove("artillery");
			}
			
		}
		int remaining = numberofCountries - equalDis*3;
		if(remaining ==1)
			cardList.add(map.getListOfCountries().get(equalDis*3).getName()+" "+"infantry");
		if(remaining >1)
			cardList.add(map.getListOfCountries().get(equalDis*3+1).getName()+" "+"cavalry");
		
		return cardList;
	}

	/**
	 * This method contains the commands provided after editmap option is selected
	 */
	private static void editMapCommands() {
		
		System.out.println("Type editcontinent -add <continentname> <continentvalue> -remove <continentname>");
		System.out.println("Type editcountry -add <countryname> <continentname> -remove <countryname>");
		System.out.println("Type editneighbor -add <countryname> <neighborcountryname> -remove <countryname> <neighborcountryname>");
		System.out.println("Type showmap");
		System.out.println("Type validatemap");
	}
}


