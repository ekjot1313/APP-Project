import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mapWorks.MapReader;
import view.CardExchangeView;
import view.PWDView;
import view.PhaseView;
import dao.AggresiveStrategy;
import dao.BenevolentStrategy;
import dao.CheaterStrategy;
import dao.HumanStrategy;
import dao.Map;
import dao.Player;
import dao.RandomStrategy;
import dao.Strategy;
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
			System.out.println("Select game mode :Tournament or Single Game ");
			String gameMode = sc.nextLine();
			
			if(gameMode.equalsIgnoreCase("Single Game")) {
				System.out.println(
						"Type \nloadmap <filename> -load an existing map \neditmap <filename> -edit an existing map or create a new map");
				String[] commands = sc.nextLine().split(" ");
				if (commands.length == 1 && !commands[0].equals("exit")) {
					System.out.println("Invalid command .Type exit to end game");
				} else {
					switch (commands[0]) {
					case "loadmap":
						int isSuccess =loadmap(commands[1]);
						if(isSuccess == 1)
							gameplayer();
						else
							continue;
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
			}else if(gameMode.equalsIgnoreCase("Tournament")) {				
				tournamentMode();
				
			}else {
				System.out.println("Invalid Command");
			}
		}
	}

	private static void tournamentMode() throws Exception {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String[] listOfMapFiles ;
		String[] listOfPlayerStrategies;
		int numberOfGames = 0;
		int maxTurns = 0;
		//Initial setup
		while(true) {
		System.out.println("Type tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
		String tournamentCommand  = sc.nextLine();
		
		String[] tournamentCommandArray = tournamentCommand.split(" ");
		
			if(tournamentCommandArray[0].equals("tournament") && tournamentCommandArray.length == 9){
				if(tournamentCommandArray[1].equals("-M") && tournamentCommandArray[3].equals("-P") && tournamentCommandArray[5].equals("-G") && tournamentCommandArray[7].equals("-D")) {
					listOfMapFiles = tournamentCommandArray[2].split(",");
					listOfPlayerStrategies = tournamentCommandArray[4].split(",");
					try {
					numberOfGames = Integer.parseInt(tournamentCommandArray[6]);
					maxTurns = Integer.parseInt(tournamentCommandArray[8]);
					}catch(Exception e) {
						System.out.println("Invalid command .Type again");
					}
					break;
					
				}else {
					System.out.println("Invalid command .Type again");
				}
					
			}else {
				System.out.println("Invalid command .Type again");
			}
		}
		
		
		//TournamentMode Begins
		String[][] winnerArray = new String[listOfMapFiles.length][numberOfGames];
		int m= 0;
		for(String filename :listOfMapFiles) {
			//For each map	
				//Gameplayer logic			
				for(int j=0;j<numberOfGames;j++) {
					map = new Map();
					ArrayList<Player> listOfPlayers = new ArrayList<Player>();
					map.setListOfPlayers(listOfPlayers);
					int isSuccess = loadmap(filename);
					if(isSuccess == 1) {
						for(String playerType : listOfPlayerStrategies) {
							Player p =  new Player();
							p.deck = new ArrayList<String>();
							p.setName(playerType);
							p.setStrategy(getStrategyByName(playerType));
							map.addPlayer(p);
							listOfPlayers.add(p);
						}
					PlayerAllocator pa = new PlayerAllocator();
					ArmyAllocator aa = new ArmyAllocator();
					pa.listOfPlayers = listOfPlayers;
					pa.populateCountries(map);
					aa.calculateTotalArmies((ArrayList<Player>) pa.listOfPlayers, map, -1);
					aa.placeAll((ArrayList<Player>) pa.listOfPlayers, map);
					boolean isDraw = true;
					for(int k =0;k<maxTurns;k++) {
						PhaseView pv= new PhaseView();
						//CardExchangeView cev = new CardExchangeView();
						int gameOver = 0;
						for (int i = 0; i < pa.listOfPlayers.size(); i++) {
							
							//System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " reinforcement phase begins");
							pa.listOfPlayers.get(i).attach(pv);
							
							//pa.listOfPlayers.get(i).attach(cev);
							pa.listOfPlayers.get(i).executeReinforcement(map, (ArrayList<Player>) pa.listOfPlayers);
							Thread.sleep(2500);
							Player current= pa.listOfPlayers.get(i);
							//pa.listOfPlayers.get(i).detach(cev);
							//cev.close();
							//System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " Attack phase begins");
							gameOver = pa.listOfPlayers.get(i).executeAttack(map, (ArrayList<Player>) pa.listOfPlayers);
							Thread.sleep(2500);
							if (gameOver == 1)
								break;
							int index=pa.listOfPlayers.indexOf(current);
							i=index;
							//System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " Fortification phase begins");
							pa.listOfPlayers.get(i).executeFortification(map, (ArrayList<Player>) pa.listOfPlayers,null);
							Thread.sleep(1500);
							pa.listOfPlayers.get(i).detach(pv);
							
						}
						if (gameOver == 1) {
						System.out.println("Game Over");
						System.out.println("Winner is Player: " + pa.listOfPlayers.get(0).getName());
						winnerArray[m][j] = pa.listOfPlayers.get(0).getName();
						isDraw = false;
						break;
						}
						
					}
					
					if(isDraw) {
						System.out.println("Draw");
						winnerArray[m][j] = "Draw";
					}
				}
				
				
			}
				m++;
		}
		
		System.out.print("\t");
		for(int i=0;i<numberOfGames;i++) {
		System.out.print("Game "+(i+1) +"\t");
		}

		System.out.println();
		for(int i=0;i<winnerArray.length;i++) {
			System.out.print("Map "+(i+1) +"\t");
			for(int j=0;j<winnerArray[i].length;j++) {
				System.out.print(winnerArray[i][j]+"\t");
			}
			System.out.println();
		}
		
		
		
	}

	private static Strategy getStrategyByName(String playerType) {
		// TODO Auto-generated method stub
		if(playerType.equalsIgnoreCase("Aggressive"))
			return new AggresiveStrategy();
		if(playerType.equalsIgnoreCase("Random"))
			return new RandomStrategy();
		if(playerType.equalsIgnoreCase("Cheater"))
			return new CheaterStrategy();
		if(playerType.equalsIgnoreCase("Benevolent"))
			return new BenevolentStrategy();
		return null;
	}

	/**
	 * This method is called when user gives 'editmap' command
	 * 
	 * @param filename Map file to be edited
	 */
	private static void editmap(String filename)  throws Exception {
		MapEditor mpe = new MapEditor();
		System.out.println(System.getProperty("user.dir"));
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\Maps\\" + filename + ".map";
		File newFile = new File(currentPath);
		map = new Map(); // to clear buffer map
		DominationReaderWriter drw =  new DominationReaderWriter();
		if (newFile.exists()) {
			//check if file if Domination or Conquest
			BufferedReader bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			String firstLine = bufferReaderForFile.readLine();
			
			ConquestReaderWriter crw =  new ConquestReaderWriter();
			
			if(!firstLine.startsWith(";")) {
				drw = new MapReaderWriterAdapter(crw);
			}
			
			int mapParseStatus = drw.parseMapFile(map,newFile);
			try {
				editMapCommands();
				System.out.println("Type savemap");
				map = mpe.mapEditorInit(map , drw);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Map file does not exist .New File created .");
			editMapCommands();
			try {
				map = mpe.mapEditorInit(null , drw);

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
	private static int  loadmap(String filename) throws Exception {
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
			
			// to check whether map is parsed successfully
			if (mapParseStatus == 1) {
				System.out.println("Map is loaded successfully.");
				return 1;
				
			}
			else {
				System.out.println("Error while loading map");
				return 0;
			}
				

		} else {
			System.out.println("File Not found .");
			return 0;
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
				System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " Attack phase begins");
				Player current= pa.listOfPlayers.get(i);
				gameOver = pa.listOfPlayers.get(i).executeAttack(map, (ArrayList<Player>) pa.listOfPlayers);
				Thread.sleep(1500);
				if (gameOver == 1)
					break;
				int index=pa.listOfPlayers.indexOf(current);
				i=index;
				System.out.println("Player " + pa.listOfPlayers.get(i).getName() + " Fortification phase begins");
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
