package dao;

import java.util.ArrayList;
import java.util.Scanner;

public class BenevolentStrategy implements Strategy  {
	/**
	 * This country object stores the weakest country.
	 */
	public Country weak;
	/**
	 * This method calculates the number of reinforcement armies
	 * 
	 * @param map         Map Object
	 * @param P			  Player
	 * @return Number of reinforcement armies
	 */
	@Override
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
	/**
	 * This function finds the weakest country of a player which is the country with lowest no. of armies out of all the countries which can be attacked by the other players.
	 * @param map			Map Object
	 * @param listPlayer	List of Players
	 * @param P				Current Player
	 * @return		1 for success,0 for failure
	 */
	public int weakestCountry(Map map, ArrayList<Player> listPlayer,Player P) {
		int min =999,flag=0;
		for(int i=0;i<P.getAssigned_countries().size();i++) {
			if(P.getAssigned_countries().get(i).getNoOfArmies()< min) {
				//if can attack-
				if(attackPossible(map,listPlayer,P,P.getAssigned_countries().get(i))==1) {
				min=P.getAssigned_countries().get(i).getNoOfArmies();
				weak=P.getAssigned_countries().get(i);
				flag=1;
				}
			}
		}
		if(flag==1) {
			return 1;
		}
		if(flag==0)
			return 0;
		return 0;
	}
	/**
	 * This function finds the weakest country of a player which is the country with lowest no. of armies.
	 * @param map			Map Object
	 * @param listPlayer	List of Players
	 * @param P				Current Player
	 */
	public void simpleWeakestCountry(Map map, ArrayList<Player> listPlayer,Player P) {
		int min =999;
		for(int i=0;i<P.getAssigned_countries().size();i++) {
			if(P.getAssigned_countries().get(i).getNoOfArmies()<min) {
				min=P.getAssigned_countries().get(i).getNoOfArmies();
				weak=P.getAssigned_countries().get(i);
				}
			}
	}
	/**
	 * This function informs whether an attack is possible on a country or not.
	 * @param map	Map Object
	 * @param listPlayer	List of Players
	 * @param P			Current Player
	 * @param c			Current country
	 * @return			1 for success,0 for failure
	 */
	public int attackPossible(Map map, ArrayList<Player> listPlayer,Player P,Country c) {
		for(String s:c.getNeighbors()) {
			Country neighbor=map.getCountryFromName(s);
			if(!c.getOwner().equals(neighbor.getOwner())&& neighbor.getNoOfArmies()>1)
					return 1;
		}
		return 0;
	}
	/**
	 * This is the function for reinforcement phase
	 * @param map	Map Object
	 * @param listPlayer	List of Players
	 * @param P			Current Player
	 */
	public void reinforcement(Map map, ArrayList<Player> listPlayer,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Reinforcement");
		Scanner sc = new Scanner(System.in);
		// calculate reinforcement armies
		int reinforcementArmies=calculateReinforceArmies(map,P);
		map.setNoOfArmies(P, (P.getNoOfArmies() + reinforcementArmies));
		int flag=weakestCountry(map, listPlayer, P);
		if(flag==0) {
			simpleWeakestCountry(map, listPlayer, P);
		}
		P.setActions("Reinforced " + reinforcementArmies + " armies to "+ weak.getName());
		weak.setNoOfArmies(weak.getNoOfArmies()+reinforcementArmies);
		P.setEndOfActions(1); 
		P.setActions("Reinforcement finished");
	}
	/**
	 * This is the function for attack phase
	 * @param map	Map Object
	 * @param listPlayer	List of Players
	 * @param P			Current Player
	 * @return 1 if the game is over otherwise 0.
	 */
	public int attack(Map map, ArrayList<Player> listPlayer,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Attack");
		P.setEndOfActions(1); 
		System.out.println("Attack skipped");
		P.setActions("Attack finished");
		return 0;
	}
	/**
	 * This is the function for fortification phase
	 * @param map	Map Object
	 * @param listPlayer	List of Players
	 * @param command	command used for testing
	 * @param P			Current Player
	 */
	public void fortification(Map map, ArrayList<Player> listPlayer, String command,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Fortification");
		int min =0;
		int flag=weakestCountry(map, listPlayer, P);
		if(flag==0) {
			simpleWeakestCountry(map, listPlayer, P);
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
	@Override
	public int validate(String command, Map testMap, Player P) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int attackMove(String command, Country fromCountry, Country toCountry, int attackerDice, Player P) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int endGame(ArrayList<Player> listOfPlayers) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int attackDeadlock(Map testMap, Player P) {
		// TODO Auto-generated method stub
		return 0;
	}



}
