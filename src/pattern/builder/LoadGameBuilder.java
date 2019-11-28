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

/**
 * This class is used to load the game
 *
 */
public class LoadGameBuilder extends GameBuilder {
	/**
	 * BufferedReader to process map file
	 */
	private BufferedReader bufferReaderForFile;
	/**
	 * CurrentLine to store the current line of parsing
	 */
	private String currentLine;

	/**
	 * This method is used to build complex map object
	 * 
	 * @param filename Name of the file
	 * @param map      Map Object
	 */
	@Override
	void buildMap(String filename, Map map) {
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\SavedGame\\" + filename + ".txt";
		File newFile = new File(currentPath);
		try {
			bufferReaderForFile = new BufferedReader(new FileReader(newFile));

			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				if (currentLine.contains("[Continents]")) {
					loadContinents(map);
				}

				if (currentLine.contains("[Territories]")) {
					loadTerritories(map);
				}

				if (currentLine.contains("[PlayerList]")) {
					loadPlayers(map);
				}

				if (currentLine.contains("[CardDetails]")) {
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

	/**
	 * This method is used to load card details
	 * 
	 * @param map Map Object
	 * @throws IOException ioexception
	 */
	private void loadCardDetails(Map map) throws IOException {
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] cardDetails = currentLine.split(",");
			ArrayList<String> deck = new ArrayList<String>();
			for (int i = 0; i < cardDetails.length - 1; i++) {
				deck.add(cardDetails[i]);
			}
			Player.deck = deck;
			Player.cardExchangeCounter = Integer.parseInt(cardDetails[cardDetails.length - 1]);
		}

	}

	/**
	 * This method is used to load players
	 * 
	 * @param map Map Object
	 * @throws IOException ioexception
	 */
	private void loadPlayers(Map map) throws IOException {
		ArrayList<Player> playerlist = new ArrayList<Player>();
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}

			String[] playerDetails = currentLine.split(" ");

			Player p = new Player();
			p.setName(playerDetails[0]);

			p.setUnassignedarmies(Integer.parseInt(playerDetails[2]));

			if (playerDetails[3].equals("pattern.Strategy.HumanStrategy"))
				p.setStrategy(new HumanStrategy());
			else if (playerDetails[3].equals("pattern.Strategy.AggressiveStrategy"))
				p.setStrategy(new AggressiveStrategy());
			else if (playerDetails[3].equals("pattern.Strategy.BenevolentStrategy"))
				p.setStrategy(new BenevolentStrategy());
			else if (playerDetails[3].equals("pattern.Strategy.CheaterStrategy"))
				p.setStrategy(new CheaterStrategy());
			else if (playerDetails[3].equals("pattern.Strategy.RandomStrategy"))
				p.setStrategy(new RandomStrategy());
			ArrayList<Country> countries = new ArrayList<Country>();
			int j = 4;
			for (j = 4; j < playerDetails.length; j++) {
				if (map.getCountryFromName(playerDetails[j]) != null) {
					Country c = map.getCountryFromName(playerDetails[j]);
					countries.add(c);
				} else
					break;
			}
			p.setAssigned_countries(countries);
			ArrayList<String> cardList = new ArrayList<String>();
			int index = 0;
			String[] cardsString = currentLine.split(",");
			for (int i = 1; i < cardsString.length; i++) {
				cardList.add(cardsString[i]);
			}

			p.setCards(cardList);

			map.setNoOfArmies(p, Integer.parseInt(playerDetails[1]) - Integer.parseInt(playerDetails[2]));
			map.addPlayer(p);
		}
	}

	/**
	 * This method is used to load bridges
	 * 
	 * @param map Map Object
	 */
	private void loadBridges(Map map) {
		for (Country c : map.getListOfCountries()) {
			for (String neighborCountry : c.getNeighbors()) {
				if (!map.getCountryFromName(neighborCountry).getContinentName().equals(c.getContinentName())) {
					Bridge bridge = new Bridge(map.getCountryFromName(neighborCountry).getContinentName(), c.getName(),
							neighborCountry);
					map.getContinentFromName(c.getContinentName()).getBridges().add(bridge);
				}
			}
		}

	}

	/**
	 * This method is used to load territories
	 * 
	 * @param map Map Object
	 * @throws IOException ioexception
	 */
	private void loadTerritories(Map map) throws IOException {
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
			List<String> neighbours = new ArrayList<String>();
			for (int i = 4; i < countryDetails.length; i++) {
				neighbours.add(countryDetails[i]);
			}
			c.setNeighbors(neighbours);
			map.addCountry(c);

		}

	}

	/**
	 * This method is used to load continents
	 * 
	 * @param map Map Object
	 * @throws NumberFormatException numberformatexception
	 * @throws IOException ioexception
	 */
	private void loadContinents(Map map) throws NumberFormatException, IOException {
		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			if (currentLine.length() == 0) {
				continue;
			}
			String[] continentDetails = currentLine.split(" ");
			Continent continent = new Continent();
			continent.setName(continentDetails[0]);
			continent.setContinentValue(Integer.parseInt(continentDetails[1]));
			if (continentDetails[2].equals("FREE") && continentDetails[3].equals("CONTINENTS"))
				continent.setOwner("FREE CONTINENTS");
			else
				continent.setOwner(continentDetails[2]);
			map.addContinent(continent);

		}

	}

	/**
	 * This method is used to build player object
	 * 
	 * @param filename Name of the file
	 * @param player   Player Object
	 */
	@Override
	void buildPlayer(String filename, String player) {
		String currPlayer = "";
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

	/**
	 * This method is used to build Phase
	 * 
	 * @param filename Name of the file
	 * @param phase    Phase
	 */
	@Override
	void buildPhase(String filename, String phase) {
		String currPhase = "";
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
