package pattern.builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.Bridge;
import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import pattern.Strategy.HumanStrategy;

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
		try {

			bufferReaderForFile = new BufferedReader(new FileReader(filename));
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
				if(playerDetails[2].equals("pattern.Strategy.HumanStrategy"))
					p.setStrategy(new HumanStrategy());
				else if(playerDetails[2].equals("pattern.Strategy.AggressiveStrategy"))
					p.setStrategy(new HumanStrategy());
				else if(playerDetails[2].equals("pattern.Strategy.BenevolentStrategy"))
					p.setStrategy(new HumanStrategy());
				else if(playerDetails[2].equals("pattern.Strategy.CheaterStrategy"))
					p.setStrategy(new HumanStrategy());
				else if(playerDetails[2].equals("pattern.Strategy.RandomStrategy"))
					p.setStrategy(new HumanStrategy());
				ArrayList<Country> countries = new ArrayList<Country>();
				int j= 3;
				for(j=3;j<playerDetails.length;j++) {
					if(map.getCountryFromName(playerDetails[j]) != null) {
						Country c= map.getCountryFromName(playerDetails[j]);
					countries.add(c);
					}
					else
						break;
				}
				p.setAssigned_countries(countries);
				ArrayList<String> cardList = new ArrayList<String>();
				for(int k=j;k<playerDetails.length;k++) {
					cardList.add(playerDetails[k]);
				}
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
		try {

			bufferReaderForFile = new BufferedReader(new FileReader(filename));
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
		try {

			bufferReaderForFile = new BufferedReader(new FileReader(filename));
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
