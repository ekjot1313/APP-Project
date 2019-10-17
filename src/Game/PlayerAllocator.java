package Game;

import java.util.*;

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
				mr.displayAll(map);
			} else if (validate(cmd) == 1) {
				String str[] = cmd.split(" ");
				// System.out.println("Valid command");
				for (int i = 1; i < str.length; i++) {
					if (str[i].equals("-add")) {
						Player p = new Player();
						p.setName(str[i + 1]);
						listOfPlayers.add(p);
						i++;
						System.out.println("Player " + p.getName() + " has been added successfully.");
					}
					if (str[i].equals("-remove")) {
						int j;
						int flag = 0;
						for (j = 0; j < listOfPlayers.size(); j++) {
							if (listOfPlayers.get(j).getName().contentEquals(str[i + 1])) {
								System.out.println(
										"Player " + listOfPlayers.get(j).getName() + " has been removed successfully.");
								listOfPlayers.remove(j);
								// j++;
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
		in.close();
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
		int count = 0;
		for (int i = 0; i < j; i++) {
			for (int k = 0; k < playerCount; k++) {
				listOfPlayers.get(k).getAssigned_countries().add(map.getListOfCountries().get(count));
				map.getListOfCountries().get(count).setOwner(listOfPlayers.get(k).getName());
				count++;
			}
		}
		int leftCountries = countryCount - count;
		for (int m = 0; m < leftCountries; m++) {
			listOfPlayers.get(m).getAssigned_countries().add(map.getListOfCountries().get(count));
			map.getListOfCountries().get(count).setOwner(listOfPlayers.get(m).getName());
			count++;

		}
	}

}
