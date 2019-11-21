package dao;

import java.util.ArrayList;
import java.util.Scanner;

public class BenevolentStrategy implements Strategy  {
	/**
	 * This method calculates the number of reinforcement armies
	 * 
	 
	 * @param map         Map Object
	 * @param P			  Player
	 * @return Number of reinforcement armies
	 */
	public int calculateReinforceArmies(Map map,Player P) {
		// calculating on the basis of no of countries the player own
		int noOfArmies = P.getAssigned_countries().size() / 3;
		int reinforcementArmies = noOfArmies <= 3 ? 3 : noOfArmies;

		// calculating on the basis of continents owned

		for (Continent continent : map.getListOfContinent()) {
			if (continent.getOwner().equals(P.getName())) {
				reinforcementArmies += continent.getContinentValue();
		
			}
		}

		return reinforcementArmies;
	}
	public void reinforcement(Map map, ArrayList<Player> listPlayer,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Reinforcement");
		Scanner sc = new Scanner(System.in);
		// calculate reinforcement armies
		int reinforcementArmies=calculateReinforceArmies(map,P);
		Country weak=new Country();
		int min =0;
		for(int i=0;i<P.getAssigned_countries().size();i++) {
			if(i==0) {
				min=P.getAssigned_countries().get(i).getNoOfArmies();
				weak=P.getAssigned_countries().get(i);
			}
			if(P.getAssigned_countries().get(i).getNoOfArmies()<min) {
				min=P.getAssigned_countries().get(i).getNoOfArmies();
				weak=P.getAssigned_countries().get(i);
			}
		}
		P.setActions("Reinforced " + reinforcementArmies + " armies to "+ weak.getName());
		weak.setNoOfArmies(weak.getNoOfArmies()+reinforcementArmies);
		P.setEndOfActions(1); 
		P.setActions("Reinforcement finished");
	}
	public int attack(Map map, ArrayList<Player> listPlayer,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Attack");
		P.setEndOfActions(1); 
		System.out.println("Attack skipped");
		P.setActions("Attack finished");
		return 0;
	}
	public void fortification(Map map, ArrayList<Player> listPlayer, String command,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Fortification");
		Country weak=new Country();
		int min =0;
		for(int i=0;i<P.getAssigned_countries().size();i++) {
			if(i==0) {
				min=P.getAssigned_countries().get(i).getNoOfArmies();
				weak=P.getAssigned_countries().get(i);
			}
			if(P.getAssigned_countries().get(i).getNoOfArmies()<min) {
				min=P.getAssigned_countries().get(i).getNoOfArmies();
				weak=P.getAssigned_countries().get(i);
			}
		}
		Country c=new Country();
		int max=1;
		for(int i=0;i<weak.getNeighbors().size();i++) {
			Country neighbor=map.getCountryFromName(weak.getNeighbors().get(i));
			if(weak.getOwner().equals(neighbor.getOwner())) {
				if(neighbor.getNoOfArmies()>max) {
					max=neighbor.getNoOfArmies();
					c=neighbor;
				}
			}
		}
		if(max>1) {
			int army=c.getNoOfArmies()-1;
			weak.setNoOfArmies(weak.getNoOfArmies()+army);
			c.setNoOfArmies(1);
			System.out.println("Fortification successful");
			P.setActions("Fortified " + weak.getName() + " with " + army + " armies from " + c.getName());
		}
		else
			System.out.println("Cannot fortify as all the neighboring countries of weakest country have only 1 army left");
		P.setEndOfActions(1); 
		P.setActions("Fortification finished");
	}

}
