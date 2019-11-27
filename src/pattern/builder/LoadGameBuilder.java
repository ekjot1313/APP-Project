package pattern.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.Bridge;
import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import pattern.Strategy.AggressiveStrategy;
import pattern.Strategy.BenevolentStrategy;
import pattern.Strategy.CheaterStrategy;
import pattern.Strategy.HumanStrategy;
import pattern.Strategy.RandomStrategy;

public class LoadGameBuilder extends GameBuilder{
	/**
	 * BufferedReader to process map file
	 */
	private BufferedReader bufferReaderForFile;
	/**
	 * CurrentLine to store the current line of parsing
	 */
	private String currentLine;
	
	@Override
	void buildMap(String filename, Map map) {
		// TODO Auto-generated method stub
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\SavedGame\\" + filename + ".txt";
		File newFile = new File(currentPath);
		try {

			bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			map = new Map();
			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[Continents]")) {
					loadContinents(map);
				}

				if (currentLine.contains("[Territories]")) {
					loadTerritories(map);
				}
				
				if(currentLine.contains("[PlayerList]")) {
					loadPlayers(map);
				}
				
				if(currentLine.contains("[CardDetails]")) {
					loadCardDetails(map);
				}
				

			}
			
			loadBridges(map);
			
			
		} catch (Exception e) {
			if (e.toString().contains("FileNotFoundException"))
				System.out.println("Invalid filename");
			e.printStackTrace();
		}
		gameProduct.setMap(map);
	}

	private void loadCardDetails(Map map) throws IOException {
		// TODO Auto-generated method stub
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] cardDetails = currentLine.split(",");
			ArrayList<String> deck = new ArrayList<String>();
			for(int i=0;i<cardDetails.length - 1;i++) {
				deck.add(cardDetails[i]);
			}
			Player.deck  =deck;
			Player.cardExchangeCounter = Integer.parseInt(cardDetails[cardDetails.length -1]);
		}
		
		
	}

	private void loadPlayers(Map map) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Player> playerlist = new ArrayList<Player>();
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
				
				String[] playerDetails = currentLine.split(" ");
				
				Player p = new Player();
				p.setName(playerDetails[0]);
				p.setNoOfArmies(Integer.parseInt(playerDetails[1]));
				p.setUnassignedarmies(Integer.parseInt(playerDetails[2]));
				
				if(playerDetails[3].equals("pattern.Strategy.HumanStrategy"))
					p.setStrategy(new HumanStrategy());
				else if(playerDetails[3].equals("pattern.Strategy.AggressiveStrategy"))
					p.setStrategy(new AggressiveStrategy());
				else if(playerDetails[3].equals("pattern.Strategy.BenevolentStrategy"))
					p.setStrategy(new BenevolentStrategy());
				else if(playerDetails[3].equals("pattern.Strategy.CheaterStrategy"))
					p.setStrategy(new CheaterStrategy());
				else if(playerDetails[3].equals("pattern.Strategy.RandomStrategy"))
					p.setStrategy(new RandomStrategy());
				ArrayList<Country> countries = new ArrayList<Country>();
				int j= 4;
				for(j=4;j<playerDetails.length;j++) {
					if(map.getCountryFromName(playerDetails[j]) != null) {
						Country c= map.getCountryFromName(playerDetails[j]);
					countries.add(c);
					}
					else
						break;
				}
				p.setAssigned_countries(countries);
				ArrayList<String> cardList = new ArrayList<String>();
				int index=0;
				String [] cardsString=currentLine.split(",");
				for(int i=1; i<cardsString.length;i++) {
					cardList.add(cardsString[i]);
					System.out.println("Cards: "+cardsString[i]);
				}
				/*for(int i=0;i<playerDetails.length;i++) {
					if(playerDetails[i].equals("CARDS")) {
						index = i;
					}
				}*/
				
				
				/*for(int m=index;m<playerDetails.length;m++) {
					cardsString += playerDetails[m];
				}
				System.out.println("CardsString: "+cardsString);*/
				//String [] cards = cardsString.split(",");
				//System.out.println("length: "+(playerDetails.length-2));
				/*for(int k=0;k<cards.length;k++) {
					cardList.add(cards[k]);
					System.out.println("Cards: "+cards[k]);
				}
					*/
				
				p.setCards(cardList);
				
				playerlist.add(p);
			}
		map.setListOfPlayers(playerlist);
			
		
		
	}

	private void loadBridges(Map map) {
		// TODO Auto-generated method stub
		for(Country c: map.getListOfCountries()) {
			for(String neighborCountry :c.getNeighbors() ) {
				if(!map.getCountryFromName(neighborCountry).getContinentName().equals(c.getContinentName())) {
					Bridge bridge = new Bridge(map.getCountryFromName(neighborCountry).getContinentName(),c.getName() ,neighborCountry);
					map.getContinentFromName(c.getContinentName()).getBridges().add(bridge);
				}
			}
		}
		
	}

	private void loadTerritories(Map map) throws IOException {
		// TODO Auto-generated method stub
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			
			if (currentLine.length() == 0) {
				continue;
			}
			String[] countryDetails = currentLine.split(" ");
			Country c = new Country();
			c.setName(countryDetails[0]);
			c.setContinentName(countryDetails[1]);
			c.setNoOfArmies(Integer.parseInt(countryDetails[2]));
			c.setOwner(countryDetails[3]);
			Continent continent = map.getContinentFromName(countryDetails[1]);
			continent.getCountries().add(c.getName());
			List<String> neighbours =  new ArrayList<String>();
			for (int i = 4; i < countryDetails.length; i++) {
				neighbours.add(countryDetails[i]);
			}
			c.setNeighbors(neighbours);
			map.addCountry(c);

		}	
		
	}

	private void loadContinents(Map map) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] continentDetails = currentLine.split(" ");
			Continent continent = new Continent();
			continent.setName(continentDetails[0]);
			continent.setContinentValue(Integer.parseInt(continentDetails[1]));
			continent.setOwner(continentDetails[2]);
			map.addContinent(continent);
			

		}

	}

	@Override
	void buildPlayer(String filename, String player) {
		// TODO Auto-generated method stub	
		String currPlayer ="";
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\SavedGame\\" + filename + ".txt";
		File newFile = new File(currentPath);
		try {

			bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[CurrentPlayer]")) {
					currPlayer = bufferReaderForFile.readLine();
				}
			}
		} catch (Exception e) {
			if (e.toString().contains("FileNotFoundException"))
				System.out.println("Invalid filename");
			e.printStackTrace();
		}
		
		gameProduct.setCurrentPlayer(currPlayer);
	}

	@Override
	void buildPhase(String filename, String phase) {
		// TODO Auto-generated method stub
		String currPhase ="";
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\SavedGame\\" + filename + ".txt";
		File newFile = new File(currentPath);
		try {

			bufferReaderForFile = new BufferedReader(new FileReader(newFile));
			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[CurrentPhase]")) {
					currPhase = bufferReaderForFile.readLine();
				}
			}
		} catch (Exception e) {
			if (e.toString().contains("FileNotFoundException"))
				System.out.println("Invalid filename");
			e.printStackTrace();
		}
		
		gameProduct.setCurrentPhase(currPhase);
		
	}

	
}
