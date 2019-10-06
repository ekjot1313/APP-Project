package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class ArmyAllocator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void calculateTotalArmies(ArrayList<Player> listOfPLayers ) {
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
		
		
		
	}
	
	public void placeArmy(int assignedArmies,ArrayList<Player> listOfPLayers,Map map) {
		Scanner sc = new Scanner(System.in);
		boolean isPlaceAll = false;
		for(int i=0;i<assignedArmies;i++) {
			for(Player p:listOfPLayers) {
				System.out.println("Player "+p.getName() +"to place armies :\n Type placearmy <countryname> or placeall to randomly allocate armies ");
				String input = sc.nextLine();
				
				String[] commands = input.split(" ");
				if(commands.length == 2 && commands[0].equals("placearmy") ) {
					//check if country is valid and assigned to the current player	
					if(map.getCountryFromName(commands[1]) == null) {
						System.out.println("Invalid country");
					}else {
						Country tempCountry = map.getCountryFromName(commands[1]);
						if(tempCountry.owner.getName() != p.getName() )
							System.out.println("Country is not assigned to current player");
						else {
							//main logic
							if(tempCountry.getNoOfArmies() == 0) {			
							tempCountry.noOfArmies ++;
							p.unassignedarmies --;
							}else {
								//check if there is any other country assigned to the current player with 0 armies 
								boolean isValid = true;
								for(Country c : p.getAssigned_countries()) {
									if(c.noOfArmies == 0 && c.getName() != tempCountry.getName()) {
										isValid = false;
										break;
									}
										
								}
								if(!isValid)
									System.out.println("Cannot place army to this country");
								else {
									tempCountry.noOfArmies ++;
									p.unassignedarmies --;
								}
							}
							
						}
					}
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
		
		// logic for placeall 
		
	}
	

}
