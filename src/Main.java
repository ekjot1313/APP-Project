import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mapWorks.MapReader;
import view.CardExchangeView;
import view.PWDView;
import view.PhaseView;
import dao.HumanStrategy;
import dao.Map;
import dao.Player;
import game.ArmyAllocator;
import game.PlayerAllocator;
import mapWorks.ConquestReaderWriter;
import mapWorks.DominationReaderWriter;
import mapWorks.MapReaderWriterAdapter;
import mapWorks.MapEditor;

/**
 * This is the main class
 * 
 * @author Mitalee Naik
 * @since 1.0.0
 *
 */
public class Main {
	/**
	 * To store phase domination view
	 */
	static PWDView pwdView = null;
	
	static Map map;
	
	/**
	 * @param args
	 * @throws Exception
	 *The execution of game begins from this function 
	 */
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
					System.exit(0);
		
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
		currentPath += "\\Maps\\" + filename + ".map";
		File newFile = new File(currentPath);
		if (newFile.exists()) {
			mr.parseMapFile(newFile);
			try {
				editMapCommands();
				System.out.println("Type savemap");
				map = mpe.mapEditorInit(map);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Map file does not exist .New File created .");
			editMapCommands();
			try {
				map = mpe.mapEditorInit(null);

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
			
			pwdView = new PWDView(true);
			map = new Map(); // to clear buffer map
			map.attach(pwdView);
			
			//check if file if Domination or Conquest
			BufferedReader bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			String firstLine = bufferReaderForFile.readLine();
			
			DominationReaderWriter drw =  new DominationReaderWriter();
			ConquestReaderWriter crw =  new ConquestReaderWriter();
			
			if(!firstLine.startsWith(";")) {
				drw = new MapReaderWriterAdapter(crw);
			}
			
			int mapParseStatus = drw.parseMapFile(map,newFile);
			
			//int mapParseStatus = mr.parseMapFile(newFile);
			

			// to check whether map is parsed successfully
			
			if (mapParseStatus == 1) {
				System.out.println("Map is loaded successfully.");

				gameplayer();
			}

		} else {
			System.out.println("File Not found .");
		}
	}

	/**
	 * This method is called to add or remove a player, assign countries to players
	 * and allow them to place armies The game phases (reinforcement, attack and
	 * fortification) start from this function
	 * 
	 * @param mr MapReader Object
	 * @throws Exception
	 */
	private static void gameplayer() throws Exception {

		// Player p =new Player();
		// Create Deck of cards
		ArrayList<String> deck = createDeck(map);
		Player.deck = deck;
		PlayerAllocator pa = new PlayerAllocator();
		ArmyAllocator aa = new ArmyAllocator();
		int gameOver = 0;
		pa.allocate(map,null);
		pa.populateCountries(map);
		aa.calculateTotalArmies((ArrayList<Player>) pa.listOfPlayers, map, 0);
		for (int j = 0; j < pa.listOfPlayers.size(); j++) {
			pa.listOfPlayers.get(j).setStrategy(new HumanStrategy());
		}
		while (true) {
			PhaseView pv= new PhaseView();
			CardExchangeView cev = new CardExchangeView();
			
			for (int i = 0; i < pa.listOfPlayers.size(); i++) {
				System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " reinforcement phase begins");
				pa.listOfPlayers.get(i).attach(pv);
				
				pa.listOfPlayers.get(i).attach(cev);
				pa.listOfPlayers.get(i).executeReinforcement(map, (ArrayList<Player>) pa.listOfPlayers);
				Thread.sleep(1500);
				pa.listOfPlayers.get(i).detach(cev);
				cev.close();
				
				gameOver = pa.listOfPlayers.get(i).executeAttack(map, (ArrayList<Player>) pa.listOfPlayers);
				Thread.sleep(1500);
				if (gameOver == 1)
					break;
				pa.listOfPlayers.get(i).executeFortification(map, (ArrayList<Player>) pa.listOfPlayers,null);
				Thread.sleep(1500);
				pa.listOfPlayers.get(i).detach(pv);
				
			}
			if (gameOver == 1)
				break;
		}
		System.out.println("Game Over");
		System.out.println("Winner is Player: " + pa.listOfPlayers.get(0).getName());
		
		//detach and close PWDView
		map.detach(pwdView);
		pwdView.close();
		
		
		
	}

	/**
	 * To create a deck of cards based on countries
	 * 
	 * @param map
	 * @return cardlist
	 */
	private static ArrayList<String> createDeck(Map map) {
		// TODO Auto-generated method stub
		List<String> typeOfCards = new ArrayList<String>();
		typeOfCards.add("infantry");
		typeOfCards.add("cavalry");
		typeOfCards.add("artillery");
		int numberofCountries = map.getListOfCountries().size();
		int equalDis = numberofCountries / 3;
		int iCounter = equalDis;
		int cCounter = equalDis;
		int aCounter = equalDis;
		ArrayList<String> cardList = new ArrayList<String>();

		Random r = new Random();
		for (int i = 0; i < equalDis * 3; i++) {
			String card;
			card = map.getListOfCountries().get(i).getName();
			int j = r.nextInt(typeOfCards.size());
			card += " " + typeOfCards.get(j);
			cardList.add(card);
			if (typeOfCards.get(j).equals("infantry")) {
				iCounter++;
				if (iCounter == equalDis)
					typeOfCards.remove("infantry");
			}
			if (typeOfCards.get(j).equals("cavalry")) {
				cCounter++;
				if (cCounter == equalDis)
					typeOfCards.remove("cavalry");
			}
			if (typeOfCards.get(j).equals("artillery")) {
				aCounter++;
				if (aCounter == equalDis)
					typeOfCards.remove("artillery");
			}

		}
		int remaining = numberofCountries - equalDis * 3;
		if (remaining == 1)
			cardList.add(map.getListOfCountries().get(equalDis * 3).getName() + " " + "infantry");
		if (remaining > 1)
			cardList.add(map.getListOfCountries().get(equalDis * 3 + 1).getName() + " " + "cavalry");

		return cardList;
	}

	/**
	 * This method contains the commands provided after editmap option is selected
	 */
	private static void editMapCommands() {

		System.out.println("Type editcontinent -add <continentname> <continentvalue> -remove <continentname>");
		System.out.println("Type editcountry -add <countryname> <continentname> -remove <countryname>");
		System.out.println(
				"Type editneighbor -add <countryname> <neighborcountryname> -remove <countryname> <neighborcountryname>");
		System.out.println("Type showmap");
		System.out.println("Type validatemap");
	}
}
