package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class GamePlay {
	
	
	
	public void reinforcement(ArrayList<Player> listPlayer ,Map map,int playerIndex) {
		
		Scanner sc = new Scanner(System.in);
		//calculate reinforcement armies
		int noOfArmies = listPlayer.get(playerIndex).getAssigned_countries().size() /3;
		int reinforcementArmies =  noOfArmies <= 3? 3 :noOfArmies;
		System.out.println("reinforcementArmies "+ reinforcementArmies);
		
		//loop over playerlist and assign reinforcement armies
		while(reinforcementArmies != 0 ) {
			 System.out.println("Reinforcement armies to be assigned :" + reinforcementArmies);
			 System.out.println("Type reinforce <countryname> <num>  to assign armies");
			 String input = sc.nextLine();
			 String[] inputArray = input.split(" ");
			 if(inputArray.length == 3 && inputArray[0].equals("reinforce")) {
				 int armiesTobeplaced = Integer.parseInt(inputArray[2]);
				 int countryFound =0;
				
				 ArrayList<Country> countryTempList = (ArrayList<Country>) listPlayer.get(playerIndex).getAssigned_countries();
				 for(Country c :countryTempList) {
					 if(c.getName().equals(inputArray[1]))
						 countryFound =1;
				 }
				 if(countryFound == 0)
					 System.out.println("Country is not assigned to player ");
				 else {
					 if(armiesTobeplaced <= reinforcementArmies && armiesTobeplaced >0) { // check reinforce command and country is valid and assigned to player
						 listPlayer.get(playerIndex).setNoOfArmies(listPlayer.get(playerIndex).getNoOfArmies() + armiesTobeplaced);
						 map.getCountryFromName(inputArray[1]).setNoOfArmies(map.getCountryFromName(inputArray[1]).getNoOfArmies() + armiesTobeplaced);
						 reinforcementArmies -= armiesTobeplaced;
					 }else
						 System.out.println("Number of armies to be assigned should be in the range : 1 -"+reinforcementArmies);
				 }
			}else
				System.out.println("Invalid command .Type again");
			 
		}
		
		for(Player p :listPlayer) {
			for(Country c:p.getAssigned_countries())
				System.out.println("Player "+p.getName() +" "+c.getName() +" "+" " +c.getNoOfArmies()); 
		}
		
		
		
		
		
	}
	

}
