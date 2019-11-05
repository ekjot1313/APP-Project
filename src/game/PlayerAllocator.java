package game;

import java.util.*;
import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import mapWorks.MapReader;

/**
 * This class is used to add or remove players and also to assign countries to
 * players initially
 * 
 * @author Piyush
 * @author Hartaj
 */
public class PlayerAllocator {
	/**
	 * list of players
	 */
	public List<Player> listOfPlayers;

	/**
	 * 
	 */
	public Player getPlayerFromName(String name) {
		for (int i = 0; i < listOfPlayers.size(); i++) {
			if (listOfPlayers.get(i).getName().equals(name)) {
				return listOfPlayers.get(i);
			}
		}
		return null;
	}

	/**
	 * Constructor to initialize list of players
	 */
	public PlayerAllocator() {
		this.listOfPlayers = new ArrayList<Player>();
	}

	/**
	 * This method adds or removes the player
	 * 
	 * @param map Map Object
	 */
	public void allocate(Map map) {

		this.listOfPlayers = map.getListOfPlayers();

		Scanner in = new Scanner(System.in);
		String cmd;

		do {
			System.out.println(
					"Type \ngameplayer -add<PlayerName> or -remove <PlayerName> \npopulatecountries - assign countries to players");
			System.out.println("Type showmap");
			cmd = in.nextLine();
			while (cmd.equals("populatecountries") && listOfPlayers.size() == 0) {
				System.out.println("Player list is empty, add players first.");
				System.out.println(
						"Type \ngameplayer -add<PlayerName> or -remove <PlayerName> \npopulatecountries - assign countries to players");
				System.out.println("Type showmap");
				cmd = in.nextLine();
			}
			if (cmd.equals("showmap")) {
				MapReader mr = new MapReader();
				map.displayAll();
			} else if (validate(cmd) == 1) {
				String str[] = cmd.split(" ");
				int checkDuplicate = 0;
				for (int i = 1; i < str.length; i++) {
					if (str[i].equals("-add")) {
						for (int h = 0; h < listOfPlayers.size(); h++) {
							if (str[i + 1].equals(listOfPlayers.get(h).getName())) {
								System.out.println("This player is already added, kindly add a new player.");
								checkDuplicate = 1;
								break;
							}
						}
						if (listOfPlayers.size() == map.getListOfCountries().size()) {
							System.out.println("Sorry! Cannot add more players than no of countries");
							break;
						}
						if (checkDuplicate == 1)
							break;
						else {
							Player p = new Player();
							p.setName(str[i + 1]);
							// listOfPlayers.add(p);
							map.addPlayer(p);
							i++;
							System.out.println("Player " + p.getName() + " has been added successfully.");
						}
					}
					if (str[i].equals("-remove")) {
						int j;
						int flag = 0;
						for (j = 0; j < listOfPlayers.size(); j++) {
							if (listOfPlayers.get(j).getName().contentEquals(str[i + 1])) {
								System.out.println(
										"Player " + listOfPlayers.get(j).getName() + " has been removed successfully.");
								// listOfPlayers.remove(j);
								map.removePlayer(listOfPlayers.get(j));
								flag = 1;
								break;
							}
						}
						if (flag == 0) {
							System.out.println("Player Not found");
							break;
						}

					}
				}

			} else
				System.out.println("Invalid command,Type again");
			if (cmd.equals("populatecountries") && listOfPlayers.size() == 1) {
				System.out.println("Single player cannot play the game, please add more players");
				continue;
			}
		} while (!cmd.equals("populatecountries") || listOfPlayers.size() <= 1);
	}

	/**
	 * This method displays the list of players
	 */
	public void printPlayerList() {
		System.out.println("Player List");
		for (int i = 0; i < listOfPlayers.size(); i++) {
			System.out.println(listOfPlayers.get(i).getName());
		}
		System.out.println();
	}

	/**
	 * This method displays the countries owned by each player
	 */
	public void printPlayerCountries() {
		for (int i = 0; i < listOfPlayers.size(); i++) {
			System.out.println("Player :" + listOfPlayers.get(i).getName());
			System.out.println("Countries owned:");
			for (int j = 0; j < listOfPlayers.get(i).getAssigned_countries().size(); j++) {
				System.out.println(listOfPlayers.get(i).getAssigned_countries().get(j).getName());
			}
		}
		System.out.println();
	}

	/**
	 * This method checks whether the user command is valid or not
	 * 
	 * @param command Command given by the user
	 * @return 1 if valid else 0
	 */
	public int validate(String command) {
		String str[] = command.split(" ");
		int count;
		if ((str.length % 2) != 0) {
			if (str[0].equals("populatecountries"))
				return 1;
			if (str[0].contentEquals("gameplayer") && str.length == 1)
				return 0;
			if (str[0].equals("gameplayer") && (str[1].equals("-add") || str[1].equals("-remove"))) {
				for (int i = 1; i < str.length; i++) {
					count = 0;
					if (str[i].equals("-add") || str[i].equals("-remove")) {
						if (str[i + 1].contains("-"))
							return 0;
						else {
							i++;
							count++;
						}
						if (count != 1)
							return 0;
					}
				}
				return 1;
			}
		}
		return 0;

	}

	/**
	 * This method assigns countries to the players
	 * 
	 * @param map Map Object
	 */
	public void populateCountries(Map map) {
		int countryCount = map.getListOfCountries().size();
		int playerCount = listOfPlayers.size();
		int j = (countryCount / playerCount);
		ArrayList<String> countryList = new ArrayList<String>();
		for (int p = 0; p < j * listOfPlayers.size(); p++) {
			countryList.add(map.getListOfCountries().get(p).getName());
		}
		int count = 0;
		for (int i = 0; i < j; i++) {
			for (int k = 0; k < playerCount; k++) {
				Random r = new Random();
				int index;
				if (countryList.size() != 1)
					index = r.nextInt(countryList.size() - 1);
				else
					index = 0;
				Country c = map.getCountryFromName(countryList.get(index));
				countryList.remove(index);
				listOfPlayers.get(k).getAssigned_countries().add(c);
				// c.setOwner(listOfPlayers.get(k).getName());
				map.setCountryOwner(c, listOfPlayers.get(k).getName());
				count++;
			}
		}
		for (int p = j * listOfPlayers.size(); p < map.getListOfCountries().size(); p++) {
			countryList.add(map.getListOfCountries().get(p).getName());
		}
		int leftCountries = countryCount - count;
		for (int m = 0; m < leftCountries; m++) {
			Random r = new Random();
			int index;
			if (countryList.size() != 1)
				index = r.nextInt(countryList.size() - 1);
			else
				index = 0;

			Country c = map.getCountryFromName(countryList.get(index));
			countryList.remove(index);
			listOfPlayers.get(m).getAssigned_countries().add(c);
			c.setOwner(listOfPlayers.get(m).getName());
			count++;
		}

		for (Continent c : map.getListOfContinent()) {
			int flag = 0;
			String owner = map.getCountryFromName(c.getCountries().get(0)).getOwner();
			for (String s : c.getCountries()) {
				Country country = map.getCountryFromName(s);
				if (!owner.equals(country.getOwner())) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				map.setContinentOwner(c, owner);
				c.setAssignArmy(1);
			}
		}
	}

}
