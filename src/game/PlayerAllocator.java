package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
//import mapWorks.MapReader;
import pattern.Strategy.AggressiveStrategy;
import pattern.Strategy.BenevolentStrategy;
import pattern.Strategy.CheaterStrategy;
import pattern.Strategy.HumanStrategy;
import pattern.Strategy.RandomStrategy;

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
	 * This method returns player from the list of players
	 * 
	 * @param name Name of the player to retrieve
	 * @return Player Object
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
	 * @param map     Map Object
	 * @param command Given Command
	 */
	public void allocate(Map map, String command) {

		this.listOfPlayers = map.getListOfPlayers();

		Scanner in = new Scanner(System.in);
		String cmd = "";
		int testing = 0;

		do {
			try {
				System.out.println(
						"Type \ngameplayer -add<PlayerName> <Strategy> or -remove <PlayerName> \npopulatecountries - assign countries to players");
				System.out.println("Type showmap");
				if (command != null) {
					cmd = command;
					testing = 1;
				} else
					cmd = in.nextLine();
				while (cmd.equals("populatecountries") && listOfPlayers.size() == 0) {
					System.out.println("Player list is empty, add players first.");
					System.out.println(
							"Type \ngameplayer -add<PlayerName> <Strategy> or -remove <PlayerName> \npopulatecountries - assign countries to players");
					System.out.println("Type showmap");
					cmd = in.nextLine();
				}
				String str[] = cmd.split(" ");
				if (cmd.equals("showmap")) {
					map.displayAll();
				} else if (validate(cmd) == 1) {
					int checkDuplicate = 0;
					for (int i = 1; i < str.length; i++) {
						try {
							if (str[i].equals("-add")) {
								checkDuplicate = 0;
								for (int h = 0; h < listOfPlayers.size(); h++) {
									if (str[i + 1].equals(listOfPlayers.get(h).getName())) {
										throw new AllocatorException(
												"This player is already added, kindly add a new player.");
									}
								}
								if (listOfPlayers.size() == map.getListOfCountries().size()) {
									throw new AllocatorException("Sorry! Cannot add more players than no of countries");
								}

								Player p = new Player();
								p.setName(str[i + 1]);
								if (str[i + 2].equalsIgnoreCase("human"))
									p.setStrategy(new HumanStrategy());
								else if (str[i + 2].equalsIgnoreCase("aggressive"))
									p.setStrategy(new AggressiveStrategy());
								else if (str[i + 2].equalsIgnoreCase("benevolent"))
									p.setStrategy(new BenevolentStrategy());
								else if (str[i + 2].equalsIgnoreCase("random"))
									p.setStrategy(new RandomStrategy());
								else if (str[i + 2].equalsIgnoreCase("cheater"))
									p.setStrategy(new CheaterStrategy());
								map.addPlayer(p);

								System.out.println("Player " + p.getName() + " has been added successfully.");
								i = i + 2;

							}
							if (str[i].equals("-remove")) {
								int j;
								int flag = 0;
								for (j = 0; j < listOfPlayers.size(); j++) {
									if (listOfPlayers.get(j).getName().contentEquals(str[i + 1])) {
										System.out.println("Player " + listOfPlayers.get(j).getName()
												+ " has been removed successfully.");
										map.removePlayer(listOfPlayers.get(j));
										flag = 1;
										break;
									}
								}
								if (flag == 0) {
									throw new AllocatorException("Player Not found");
								}
								i++;

							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}

				} else
					throw new AllocatorException("Invalid command,Type again");
				if (cmd.equals("populatecountries") && listOfPlayers.size() == 1) {
					throw new AllocatorException("Single player cannot play the game, please add more players");
				}
				if (testing == 1) {
					break;
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
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
		if (str[0].equals("populatecountries"))
			return 1;
		if (str[0].contentEquals("gameplayer") && str.length == 1)
			return 0;
		if (str[0].equals("gameplayer") && (str[1].equals("-add") || str[1].equals("-remove"))) {
			for (int i = 1; i < str.length; i++) {
				count = 0;
				if (str[i].equals("-add") || str[i].equals("-remove")) {
					if (str[i].equals("-add")) {
						if (i + 1 == str.length)
							return 0;
						if (str[i + 1].contains("-"))
							return 0;
						else {
							i++;
							if (i + 1 == str.length)
								return 0;
							if (str[i + 1].contains("-"))
								return 0;
							else {
								if (i + 1 == str.length)
									return 0;
								if (str[i + 1].equalsIgnoreCase("human"))
									i++;
								else if (str[i + 1].equalsIgnoreCase("aggressive"))
									i++;
								else if (str[i + 1].equalsIgnoreCase("benevolent"))
									i++;
								else if (str[i + 1].equalsIgnoreCase("random"))
									i++;
								else if (str[i + 1].equalsIgnoreCase("cheater"))
									i++;
								else
									return 0;

							}
						}
					} else if (str[i].equals("-remove")) {
						if (i + 1 == str.length)
							return 0;
						if (str[i + 1].contains("-"))
							return 0;
						else {
							i++;
							count++;
						}
						if (count != 1)
							return 0;
					}
				} else
					return 0;
			}
			return 1;
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
				map.setCountryOwner(c, listOfPlayers.get(k).getName());
			}
		}
		for (int p = j * listOfPlayers.size(); p < map.getListOfCountries().size(); p++) {
			countryList.add(map.getListOfCountries().get(p).getName());
		}
		int leftCountries = countryCount - (j * listOfPlayers.size());
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
			map.setCountryOwner(c, listOfPlayers.get(m).getName());
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
			}
		}
	}

}
