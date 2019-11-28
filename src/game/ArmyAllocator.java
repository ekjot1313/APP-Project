package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import dao.Country;
import dao.Map;
import dao.Player;

/**
 * This class allows the players to allocate armies to their countries
 * 
 * @author Mitalee Naik
 * @author Divya_000
 * @since 1.0.0
 *
 */
public class ArmyAllocator {
	/**
	 * Scanner obj
	 */
	private Scanner sc1;

	/**
	 * This method calculates the total number of armies based on the number of
	 * players
	 * 
	 * @param listOfPLayers List of all Players
	 * @param map           Map Object
	 * @return Number of assigned armies
	 */
	public int calculateTotalArmies(ArrayList<Player> listOfPLayers, Map map) {
		int maxArmiesForEachPlayer = 40;
		int assignedArmies = maxArmiesForEachPlayer - 5 * (listOfPLayers.size() - 2);
		if (assignedArmies <= 0) {
			for (Player p : listOfPLayers) {
				map.setNoOfArmies(p, 5);
				p.setUnassignedarmies(5);
			}
			assignedArmies = 5;
		} else {
			for (Player p : listOfPLayers) {
				map.setNoOfArmies(p, assignedArmies);
				p.setUnassignedarmies(assignedArmies);
			}
		}

		return assignedArmies;
	}

	/**
	 * This method allows the players to place armies on their countries in a round
	 * robin manner
	 * 
	 * @param assignedArmies Number of armies assigned
	 * @param listOfPLayers  List of all players
	 * @param map            Map Object
	 * @param test			for testing
	 */
	public void placeArmy(int assignedArmies, ArrayList<Player> listOfPLayers, Map map, int test) {

		sc1 = new Scanner(System.in);
		boolean isPlaceAll = false;
		if (test == 1) {
			placeAll(listOfPLayers, map);
		} else {

			for (int i = 0; i < assignedArmies; i++) {
				try {
					for (Player p : listOfPLayers) {
						if (!p.getStrategy().getClass().getName().equals("pattern.Strategy.HumanStrategy")) {
							isPlaceAll = true;
							break;
						}
						Boolean armyNotAllocated = true;
						while (armyNotAllocated) {
							System.out.println("Player " + p.getName()
									+ " to place armies :\n Type placearmy <countryname> or placeall to randomly allocate armies.\n Type showmap");

							String input = sc1.nextLine();
							String[] commands = input.split(" ");
							if (input.equals("showmap")) {
								map.displayAll();
							} else if (commands.length == 2 && commands[0].equals("placearmy")) {
								// check if country is valid and assigned to the current player
								if (map.getCountryFromName(commands[1]) == null) {
									throw new AllocatorException("Invalid country");
								} else {
									Country tempCountry = map.getCountryFromName(commands[1]);
									if (tempCountry.getOwner() != p.getName())
										throw new AllocatorException("Country is not assigned to current player");
									else {
										// main logic
										if (tempCountry.getNoOfArmies() == 0) {
											tempCountry.setNoOfArmies(tempCountry.getNoOfArmies() + 1);
											p.setUnassignedarmies(p.getUnassignedarmies() - 1);
											armyNotAllocated = false;
										} else {
											// check if there is any other country assigned to the current player with 0
											// armies
											boolean isValid = true;
											for (Country c : p.getAssigned_countries()) {
												if (c.getNoOfArmies() == 0 && c.getName() != tempCountry.getName()) {
													isValid = false;
													break;
												}

											}
											if (!isValid)
												throw new AllocatorException("Cannot place army to this country");
											else {
												tempCountry.setNoOfArmies(tempCountry.getNoOfArmies() + 1);
												p.setUnassignedarmies(p.getUnassignedarmies() - 1);
												armyNotAllocated = false;
											}
										}

									}
								}
								if (armyNotAllocated == false)
									System.out.println("Army placed successfully");

							} else if (commands.length == 1 && commands[0].equals("placearmy")) {
								throw new AllocatorException("Invalid command.");
							} else if (commands[0].equals("placeall")) {
								isPlaceAll = true;
								break;
							} else {
								throw new AllocatorException("Invalid command");
							}

						}
						if (isPlaceAll)
							break;
					}
					if (isPlaceAll)
						break;

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			// logic for placeall
			if (isPlaceAll)
				placeAll(listOfPLayers, map);
		}
	}

	/**
	 * This method places the remaining unassigned armies randomly
	 * 
	 * @param listOfPLayers List of all players
	 * @param map           Map Object
	 */
	public void placeAll(ArrayList<Player> listOfPLayers, Map map) {

		for (Player p : listOfPLayers) {

			for (int i = 0; i < p.getAssigned_countries().size() && p.getUnassignedarmies() > 0; i++) {
				if (p.getAssigned_countries().get(i).getNoOfArmies() == 0) {
					p.getAssigned_countries().get(i)
							.setNoOfArmies(p.getAssigned_countries().get(i).getNoOfArmies() + 1);
					p.setUnassignedarmies(p.getUnassignedarmies() - 1);
				}
			}

			Random r = new Random();
			while (p.getUnassignedarmies() > 0) {
				// random function to get a country randomly
				int i = r.nextInt(p.getAssigned_countries().size());
				p.getAssigned_countries().get(i).setNoOfArmies(p.getAssigned_countries().get(i).getNoOfArmies() + 1);
				p.setUnassignedarmies(p.getUnassignedarmies() - 1);
			}

		}
		System.out.println("All armies have been placed successfully");

	}

	/**
	 * This method displays information about the number of armies on each country
	 * for each player
	 * 
	 * @param listOfPLayers List of all players
	 * @param map           Map Object
	 */
	public void showPlayerDetails(ArrayList<Player> listOfPLayers, Map map) {
		for (Player p : listOfPLayers) {
			for (Country c : p.getAssigned_countries())
				System.out.println("Player " + p.getName() + " " + c.getName() + " " + " " + c.getNoOfArmies());
		}
	}
}
