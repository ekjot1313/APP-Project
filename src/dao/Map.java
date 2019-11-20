package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import mapWorks.MapEditor;

/**
 * This class defines Map
 * 
 * @author Ekjot
 *
 */
public class Map extends pattern.Observable {
	/**
	 * To store a message
	 */
	private String message1;
	/**
	 * To store a message
	 */
	private String message2;
	/**
	 * To store the the map name
	 */
	private String mapName;
	/**
	 * To store the list of continents
	 */
	private List<Continent> listOfContinent;
	/**
	 * To store the list of countries
	 */
	private List<Country> listOfCountries;
	/**
	 * To store list of players
	 */
	private List<Player> listOfPlayers;

	/*
	 * flag to print or not
	 */
	public boolean printFlag = true;
	
	/**
	 * Type of map
	 */
	public String type;
	/**
	 * @return the listOfPlayers
	 */
	public List<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	/**
	 * @param listOfPlayers the listOfPlayers to set
	 */
	public void setListOfPlayers(List<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	/**
	 * Constructor initializes list of countries and continents
	 */
	public Map() {
		this.listOfContinent = new ArrayList<Continent>();
		this.listOfCountries = new ArrayList<Country>();
		this.listOfPlayers = new ArrayList<Player>();
	}

	/**
	 * Constructor initializes list of countries and continents and then copy values
	 * of passed map to local map object
	 * 
	 * @param m Map Object
	 */
	public Map(Map m) {
		listOfContinent = new ArrayList<Continent>();
		listOfCountries = new ArrayList<Country>();
		for (Continent c : m.getListOfContinent()) {
			Continent newc = new Continent(c);
			listOfContinent.add(newc);
		}
		for (Country c : m.getListOfCountries()) {
			Country newc = new Country(c);
			listOfCountries.add(newc);
		}
	}

	/**
	 * This method returns the first message that is displayed in map file
	 * 
	 * @return First Message
	 */
	public String getMessage1() {
		return message1;
	}

	/**
	 * This method sets the first message that is displayed in map file
	 * 
	 * @param message1 First Message
	 */
	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	/**
	 * This method returns the second message that is displayed in map file
	 * 
	 * @return Second Message
	 */
	public String getMessage2() {
		return message2;
	}

	/**
	 * This method sets the second message that is displayed in map file
	 * 
	 * @param message2 Second Message
	 */
	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	/**
	 * This method returns the list of continents
	 * 
	 * @return Continent List
	 */
	public List<Continent> getListOfContinent() {
		return listOfContinent;
	}

	/**
	 * This method sets the list of continents
	 * 
	 * @param listOfContinent Continent List
	 */
	public void setListOfContinent(List<Continent> listOfContinent) {
		this.listOfContinent = listOfContinent;
	}

	/**
	 * This method returns the list of countries
	 * 
	 * @return Country List
	 */
	public List<Country> getListOfCountries() {
		return listOfCountries;
	}

	/**
	 * This method sets the list of countries
	 * 
	 * @param listOfCountries Country List
	 */
	public void setListOfCountries(List<Country> listOfCountries) {
		this.listOfCountries = listOfCountries;
	}

	/**
	 * This method returns the name of the map
	 * 
	 * @return Map Name
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * This method sets the name of the map
	 * 
	 * @param mapName Map Name
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * This method returns the object of a country using the given country name
	 * 
	 * @param givenCountryName Country Name
	 * @return Country Object
	 */
	public Country getCountryFromName(String givenCountryName) {

		for (Country country : this.listOfCountries) {
			if (country.getName().equals(givenCountryName)) {
				return country;
			}
		}

		return null;
	}

	/**
	 * This method returns the object of a continent using the given continent name
	 * 
	 * @param givenContinentName Continent Name
	 * @return Continent Object
	 */
	public Continent getContinentFromName(String givenContinentName) {
		for (Continent continent : this.listOfContinent) {
			if (continent.getName().equals(givenContinentName)) {
				return continent;
			}
		}
		return null;
	}

	/**
	 * This method returns the object of a player using the given player name
	 * 
	 * @param givenPlayerName Player Name
	 * @return Player Object
	 */
	public Player getPlayerFromName(String givenPlayerName) {
		for (Player player : this.listOfPlayers) {
			if (player.getName().equals(givenPlayerName)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * This method displays the map
	 * 
	 */
	public void display() {
		// TODO Auto-generated method stub

		// display
		if (this.getListOfContinent().size() > 0) {
			for (Continent c : this.getListOfContinent()) {
				println("");
				println("Continent :" + c.getName());

				if (c.getBridges().size() > 0) {
					println("Bridges");
					for (Bridge bridge : c.getBridges()) {
						println("To Continent: " + bridge.getNeigContinent() + "|| From Country: "
								+ bridge.getCountry1() + " To country: " + bridge.getCountry2());
					}

				}
				for (String c1 : c.getCountries()) {
					print("Country :" + c1 + ": Neighbours -> ");

					for (Country country : this.getListOfCountries()) {
						if (c1.equals(country.getName())) {

							for (String c2 : country.getNeighbors()) {
								print(c2 + " || ");
							}

							println("");
						}
					}

				}

			}
		} else {
			println("Map Empty.");
		}

	}

	/**
	 * This method checks if every subgraph is valid
	 * 
	 * @param map Map Object
	 * @return 0 if valid else 1
	 */
	public int validateContinent(Map map) {

		MapEditor mpeNew = new MapEditor();
		mpeNew.print = false;// no need to print background edits
		Map newMap = new Map(map);
		mpeNew.map = newMap;

		if (newMap.getListOfContinent().size() > 1) {// no need to check validation on continent if there is no or only
			// one continent
			for (int i = 0; i < newMap.getListOfContinent().size(); i++) {
				String remainingContinent = "editcontinent ";

				for (int j = 0; j < newMap.getListOfContinent().size(); j++) {
					if (i != j)
						remainingContinent += "-remove " + newMap.getListOfContinent().get(j).getName() + " ";

				}
				remainingContinent.trim();

				mpeNew.editContinent(remainingContinent.split(" "));

				if (mpeNew.map.validateMap() == 1) {
					return 1;
				}
				newMap = new Map(map);
				mpeNew.map = newMap;
			}
		}

		return 0;
	}

	/**
	 * This method displays the map
	 * 
	 * 
	 */
	public void displayAll() {
		// TODO Auto-generated method stub

		// display
		if (this.getListOfContinent().size() > 0) {
			for (Continent c : this.getListOfContinent()) {
				println("");
				println("------------------------------------------------------------------------------------");
				println("Continent :" + c.getName());

				if (c.getBridges().size() > 0) {
					println(" Bridges");
					for (Bridge bridge : c.getBridges()) {
						println("  To Continent: " + bridge.getNeigContinent() + "|| From Country: "
								+ bridge.getCountry1() + " To country: " + bridge.getCountry2());
					}

				}
				println("");
				for (String c1 : c.getCountries()) {
					println(" Country :" + c1);
					print("\tNo of Armies :" + this.getCountryFromName(c1).getNoOfArmies());
					println(" Owner :" + this.getCountryFromName(c1).getOwner());
					print("\tNeighbors :");
					for (Country country : this.getListOfCountries()) {
						if (c1.equals(country.getName())) {

							for (String c2 : country.getNeighbors()) {
								print(c2 + " || ");
							}

							println("");
						}
					}

				}

			}
		} else {
			println("Map Empty.");
		}

	}

	/**
	 * This method checks whether the map is valid or not
	 * 
	 * @return 0 if valid else 1
	 */
	public int validateMap() {
		// traversing
		/**
		 * Map to store the Index of countries
		 */
		HashMap<Integer, List<Integer>> mapOfWorld;
		if (this.getListOfContinent().size() == 0 || this.getListOfCountries().size() == 0) {
			println("Map is empty");
			return 1;
		}
		int notConnected = 0;
		mapOfWorld = new HashMap<Integer, List<Integer>>();
		if (checkDuplicates() == 0) {

			// graph creation

			for (int i = 0; i < this.getListOfCountries().size(); i++) {
				List<Integer> templist = new ArrayList<Integer>();
				for (int j = 0; j < this.getListOfCountries().get(i).getNeighbors().size(); j++)
					templist.add(this.getListOfCountries()
							.indexOf(this.getCountryFromName(this.getListOfCountries().get(i).getNeighbors().get(j))));
				mapOfWorld.put(i, templist);

			}

			Boolean[] visited = new Boolean[mapOfWorld.keySet().size()];
			for (int i = 0; i < visited.length; i++) {
				visited[i] = false;
			}

			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(0);
			visited[0] = true;
			while (queue.size() > 0) {
				Integer c1 = queue.poll();
				Iterator<Integer> i = mapOfWorld.get(c1).listIterator();
				while (i.hasNext()) {
					int n = (int) i.next();
					if (visited[n] == false) {
						visited[n] = true;
						queue.add(n);
					}

				}

			}

			for (int i = 0; i < visited.length; i++) {
				if (!visited[i]) {
					notConnected = 1;
					break;
				}
			}

		} else
			notConnected = 1;
		return notConnected;
	}

	/**
	 * This method checks if duplicate continents or countries exist
	 * 
	 * @return 0 if no duplicates else 1
	 */
	public int checkDuplicates() {
		int duplicate = 0;
		for (int i = 0; i < (this.getListOfContinent().size() - 1); i++)
			for (int j = i + 1; j < this.getListOfContinent().size(); j++)
				if ((this.getListOfContinent().get(i).getName())
						.equalsIgnoreCase(this.getListOfContinent().get(j).getName())) {
					duplicate = 1;
					println("Duplicate Continent :" + this.getListOfContinent().get(i).getName());
					break;
				}
		if (duplicate == 0)
			for (int i = 0; i < (this.getListOfCountries().size() - 1); i++)
				for (int j = i + 1; j < this.getListOfCountries().size(); j++)
					if ((this.getListOfCountries().get(i).getName())
							.equalsIgnoreCase(this.getListOfCountries().get(j).getName())) {
						duplicate = 1;
						println("Duplicate Country :" + this.getListOfCountries().get(i).getName());
						break;
					}
		return duplicate;
	}

	/**
	 * This method set owner to given country and notify PWDView about the change.
	 * 
	 * @param country
	 * @param owner
	 */
	public void setCountryOwner(Country country, String owner) {
		// TODO Auto-generated method stub

		country.setOwner(owner);
		notify(this);

	}

	/**
	 * This method add new player to listOfPlayers
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		// TODO Auto-generated method stub
		this.listOfPlayers.add(player);
		notify(this);
	}

	/**
	 * This method add one continent into listOfContinent
	 * 
	 * @param continent
	 */
	public void addContinent(Continent continent) {
		// TODO Auto-generated method stub
		getListOfContinent().add(continent);
		notify(this);
	}

	/**
	 * Adds the country to list of countries
	 * @param country object of country class 
	 */
	public void addCountry(Country country) {
		// TODO Auto-generated method stub
		getListOfCountries().add(country);
		notify(this);
	}

	/**
	 * Set the owner of the continent
	 * @param cont name of continent
	 * @param name name of player
	 */
	public void setContinentOwner(Continent cont, String name) {
		// TODO Auto-generated method stub
		cont.setOwner(name);
		notify(this);
	}

	/**
	 * Removes the player from list of players
	 * @param player
	 */
	public void removePlayer(Player player) {
		// TODO Auto-generated method stub
		this.listOfPlayers.remove(player);
		notify(this);
	}

	/**
	 * Sets the number of armies for a player
	 * @param p player 
	 * @param i number of armies to be assigned
	 */
	public void setNoOfArmies(Player p, int i) {
		// TODO Auto-generated method stub
		p.setNoOfArmies(i);
		notify(this);
	}

	/**
	 * This method checks if map is completely valid or not.
	 * 
	 * @param map
	 * @return true: if map is valid. false: if map is invalid
	 */
	public boolean isValid(Map map) {
		// TODO Auto-generated method stub
		int notConnected = validateMap();
		int notConnectedSubGraph = validateContinent(map);
		if (notConnected == 0 && notConnectedSubGraph == 0)
			return true;
		return false;
	}

	/**
	 * Prints the message
	 * @param msg
	 */
	public void print(String msg) {
		if (printFlag)
			System.out.print(msg);
	}

	/**
	 * Prints the message on next line
	 * @param msg
	 */
	public void println(String msg) {
		if (printFlag)
			System.out.println(msg);
	}

}
