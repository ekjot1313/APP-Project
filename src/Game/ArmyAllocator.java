package Game;

import java.util.ArrayList;
import java.util.Scanner;

import dao.Country;
import dao.Map;
import dao.Player;
import mapWorks.MapReader;

import java.util.Random;

/** 
 * This class allows the players to allocate armies to their countries
 * @author Mitalee Naik
 * @author Divya_000
 * @since 1.0.0
 *
 */
public class ArmyAllocator {
	
	/**
	 * This method calculates the total number of armies based on the number of players 
	 * @param listOfPLayers List of all Players
	 * @param map Map Object	
	 * @return Number of assigned armies
	 */
	public int calculateTotalArmies(ArrayList<Player> listOfPLayers ,Map map,int test) {
		// TODO 2 player scenario TBD
		// 2 -40 armies ,3 -35 armies , 4- 30 armies ,5 -25 armies ...
		int maxArmiesForEachPlayer = 40;
		// Maximum number of players can be 9 only
		// Logic for more than 9 players - TBD*
		int assignedArmies = maxArmiesForEachPlayer - 5 * (listOfPLayers.size()-2);
		for(Player p :listOfPLayers) {		
			p.setNoOfArmies(assignedArmies);
			p.setUnassignedarmies(assignedArmies);
		}
		if(test ==1)
		placeArmy(assignedArmies, listOfPLayers, map, 1);
		else
			placeArmy(assignedArmies, listOfPLayers, map, 0);
		return assignedArmies;
	}
	
	/**
	 * This method allows the players to place armies on their countries in a round robin manner
	 * @param assignedArmies Number of armies assigned
	 * @param listOfPLayers List of all players
	 * @param map Map Object
	 */
	public void placeArmy(int assignedArmies, ArrayList<Player> listOfPLayers,Map map,int test) {
		
		Scanner sc = new Scanner(System.in);
		boolean isPlaceAll = false;
		if(test == 1) {
			placeAll( listOfPLayers, map);
		}
		else {
		for(int i=0;i<assignedArmies;i++) {
			for(Player p:listOfPLayers) {
				Boolean armyNotAllocated = true;
				while (armyNotAllocated) {
				System.out.println("Player "+p.getName() +" to place armies :\n Type placearmy <countryname> or placeall to randomly allocate armies.\n Type showmap ");
				String input = sc.nextLine();
				String[] commands = input.split(" ");
				if(input.equals("showmap")) {
					MapReader mr=new MapReader();
					mr.displayAll(map);
				}
				else if(commands.length == 2 && commands[0].equals("placearmy") ) {
					//check if country is valid and assigned to the current player	
					if(map.getCountryFromName(commands[1]) == null) {
						System.out.println("Invalid country");
					}else {
						Country tempCountry = map.getCountryFromName(commands[1]);
						if(tempCountry.getOwner() != p.getName() )
							System.out.println("Country is not assigned to current player");
						else {
							//main logic
							if(tempCountry.getNoOfArmies() == 0) {			
							tempCountry.setNoOfArmies(tempCountry.getNoOfArmies() + 1);
							p.setUnassignedarmies(p.getUnassignedarmies() - 1);
							armyNotAllocated =false;
							}else {
								//check if there is any other country assigned to the current player with 0 armies 
								boolean isValid = true;
								for(Country c : p.getAssigned_countries()) {
									if(c.getNoOfArmies() == 0 && c.getName() != tempCountry.getName()) {
										isValid = false;
										break;
									}
										
								}
								if(!isValid)
									System.out.println("Cannot place army to this country");
								else {
									tempCountry.setNoOfArmies(tempCountry.getNoOfArmies() + 1);
									p.setUnassignedarmies(p.getUnassignedarmies() - 1);
									armyNotAllocated = false;
								}
							}
							
						}
					}
					if(armyNotAllocated == false)
					System.out.println("Army placed successfully");
					
				}
				else if(commands.length == 1 && commands[0].equals("placearmy")) {
					System.out.println("Invalid command.");
				}else if(commands[0].equals("placeall")) {
					isPlaceAll = true;
					break;
				}else {
					System.out.println("Invalid command");
				}
				
				
				
			}
			if(isPlaceAll)
				break;
			}
			if(isPlaceAll)
				break;
		}
		
		// logic for placeall
		if(isPlaceAll)
		placeAll( listOfPLayers, map);
		}	
		sc.close();
	}
	
	/**
	 * This method places the remaining unassigned armies randomly 
	 * @param listOfPLayers List of all players
	 * @param map Map Object
	 */
	public void placeAll(ArrayList<Player> listOfPLayers,Map map) {
	
		for(Player p: listOfPLayers) {
			
			for(int i=0;i<p.getAssigned_countries().size()&& p.getUnassignedarmies()>0;i++) {
				if(p.getAssigned_countries().get(i).getNoOfArmies() == 0 ) {
					p.getAssigned_countries().get(i).setNoOfArmies(p.getAssigned_countries().get(i).getNoOfArmies()+1);
					p.setUnassignedarmies(p.getUnassignedarmies() - 1);
					}
				}
			
			Random r =  new Random();
			while(p.getUnassignedarmies()>0) {
				//random function to get a country randomly
				int i = r.nextInt(p.getAssigned_countries().size() );
				p.getAssigned_countries().get(i).setNoOfArmies(p.getAssigned_countries().get(i).getNoOfArmies()+1);
				p.setUnassignedarmies(p.getUnassignedarmies() - 1);
			}	
			
		}
		System.out.println("All armies have been placed successfully");
		
	}
	
	/**
	 * This method displays information about the number of armies on each country for each player
	 * @param listOfPLayers List of all players
	 * @param map Map Object
	 */
	public void showPlayerDetails(ArrayList<Player> listOfPLayers,Map map) {
		for(Player p :listOfPLayers) {
			for(Country c:p.getAssigned_countries())
				System.out.println("Player "+p.getName() +" "+c.getName() +" "+" " +c.getNoOfArmies()); 
		}
	}
}
