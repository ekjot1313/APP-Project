package pattern.builder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;

/**
 * This class is used to save the game
 *
 */
public class SaveGameBuilder extends GameBuilder {

	/**
	 * This method is used to build complex map object
	 * 
	 * @param filename Name of the file
	 * @param map      Map Object
	 */
	@Override
	void buildMap(String fileName, Map map) {
		String currentPath = System.getProperty("user.dir") + "\\SavedGame\\";
		String mapPath = currentPath + fileName + ".txt";
		BufferedWriter bwFile;
		try {
			bwFile = new BufferedWriter(new FileWriter(mapPath));

			String content = "**";
			content += ("\r\n[Continents]\r\n");
			for (Continent continent : map.getListOfContinent()) {
				content += (continent.getName() + " " + continent.getContinentValue() + " " + continent.getOwner()
						+ "\n");
			}
			content += ("\r\n[Territories]\r\n");
			for (Country country : map.getListOfCountries()) {
				content += (country.getName() + " " + country.getContinentName() + " " + country.getNoOfArmies() + " "
						+ country.getOwner() + " ");
				for (String neighbor : country.getNeighbors()) {
					content += (neighbor + " ");
				}
				content += "\n";
			}

			content += ("\r\n[PlayerList]\r\n");

			for (Player p : map.getListOfPlayers()) {
				content += p.getName() + " " + p.getNoOfArmies() + " " + p.getUnassignedarmies() + " "
						+ p.getStrategy().getClass().getName() + " ";
				for (Country c : p.getAssigned_countries()) {
					content += c.getName() + " ";
				}

				content += "CARDS,";
				for (String cards : p.getCards()) {
					content += cards + ",";
				}
				content += "\n";
			}

			content += ("\r\n[CardDetails]\r\n");
			for (String card : Player.deck) {
				content += card + ",";
			}
			content += Player.cardExchangeCounter;

			bwFile.write(content);
			bwFile.close();
			System.out.println("Game file saved as: " + fileName + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gameProduct.setMap(map);

	}

	/**
	 * This method is used to build player object
	 * 
	 * @param filename Name of the file
	 * @param player   Player Object
	 */
	@Override
	void buildPlayer(String fileName, String player) {
		String currentPath = System.getProperty("user.dir") + "\\SavedGame\\";
		String mapPath = currentPath + fileName + ".txt";
		BufferedWriter bwFile;

		try {
			bwFile = new BufferedWriter(new FileWriter(mapPath, true));

			String content = "";
			content += ("\r\n[CurrentPlayer]\r\n");
			content += player;
			bwFile.write(content);
			bwFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gameProduct.setCurrentPlayer(player);
	}

	/**
	 * This method is used to build Phase
	 * 
	 * @param filename Name of the file
	 * @param phase    Phase
	 */
	@Override
	void buildPhase(String fileName, String phase) {
		// TODO Auto-generated method stub

		String currentPath = System.getProperty("user.dir") + "\\SavedGame\\";
		String mapPath = currentPath + fileName + ".txt";
		BufferedWriter bwFile;

		try {
			bwFile = new BufferedWriter(new FileWriter(mapPath, true));

			String content = "";
			content += ("\r\n[CurrentPhase]\r\n");
			content += phase;
			bwFile.write(content);
			bwFile.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gameProduct.setCurrentPhase(phase);

	}

}
