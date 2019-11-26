package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/**
 * This class is for Aggresive Strategy, implements the Strategy interface.
 * @author Piyush
 *
 */
public class AggressiveStrategy implements Strategy {
	/**
	 * Constructor to initialize country object.
	 */
	public AggressiveStrategy(){
		strong=new Country();
	}
	/**
	 * This country object stores the strongest country.
	 */
	public Country strong;
	/**
	 * This method calculates the number of reinforcement armies
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
	/**
	 * This function finds the strongest country of a player which is the country with highest no. of armies out of all the countries which can be attacked by the other players.
	 * @param map			Map Object
	 * @param listPlayer	List of Players
	 * @param P				Current Player
	 * @return		1 for success,0 for failure
	 */
	public int strongestCountry(Map map, ArrayList<Player> listPlayer,Player P) {
		int max =0,flag=0;
		for(int i=0;i<P.getAssigned_countries().size();i++) {
			if(P.getAssigned_countries().get(i).getNoOfArmies()>max) {
				//if can attack-
				if(attackPossible(map,listPlayer,P,P.getAssigned_countries().get(i))==1) {
				max=P.getAssigned_countries().get(i).getNoOfArmies();
				strong=P.getAssigned_countries().get(i);
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
	 * This function finds the strongest country of a player which is the country with highest no. of armies.
	 * @param map			Map Object
	 * @param listPlayer	List of Players
	 * @param P				Current Player
	 */
	public void simpleStrongestCountry(Map map, ArrayList<Player> listPlayer,Player P) {
		int max =0,flag=0;
		for(int i=0;i<P.getAssigned_countries().size();i++) {
			if(P.getAssigned_countries().get(i).getNoOfArmies()>max) {
				max=P.getAssigned_countries().get(i).getNoOfArmies();
				strong=P.getAssigned_countries().get(i);
				flag=1;
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
			if(!c.getOwner().equals(neighbor.getOwner()) && neighbor.getNoOfArmies()>1) {
					return 1;
			}
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
		P.setView("PhaseViewCardExchangeView");
		P.setState("Reinforcement");
		Scanner sc = new Scanner(System.in);
		// calculate reinforcement armies
		int reinforcementArmies=calculateReinforceArmies(map,P);
		//int cardSize=P.getCards().size();
		int flag=0;
	while(P.getCards().size()>=3) {
			flag=0;
			int cardSize=P.getCards().size();
			for(int i=0;i<cardSize-2;i++) {
				String c1[]=P.getCards().get(i).split(" ");
				for(int j=i+1;j<cardSize;j++) {
					String c2[]=P.getCards().get(j).split(" ");
					if(c1[1].equalsIgnoreCase(c2[1])) {
						for(int k=j+1;k<cardSize;k++) {
							String c3[]=P.getCards().get(k).split(" ");
							if(c1[1].equalsIgnoreCase(c3[1])) {
						
								String card1=P.getCards().get(i);
								String card2=P.getCards().get(j);
								String card3=P.getCards().get(k);
								P.getCards().remove(card1);
								P.getCards().remove(card2);
								P.getCards().remove(card3);
								P.setView("CardExchangeView");
								P.setActions("Player has exchanged " + card1 + ", " + card2 + ", " + card3);
								P.setCardExchangeCounter(P.getCardExchangeCounter() + 5);
								reinforcementArmies += P.getCardExchangeCounter();
								map.setNoOfArmies(P, P.getNoOfArmies() + P.getCardExchangeCounter());
								System.out.println("Reinforcement armies added " + P.getCardExchangeCounter());
								System.out.println("Remaining armies to be placed : " + reinforcementArmies);
								Player.deck.add(card1);
								Player.deck.add(card2);
								Player.deck.add(card3);
								flag=1;
								break;
							}
						}
					}
					if(flag==1)
						break;
				}
				if(flag==1)
					break;
			}
			if(flag==0) {
				for(int i=0;i<cardSize-2;i++) {
					String c1[]=P.getCards().get(i).split(" ");
					for(int j=i+1;j<cardSize-1;j++) {
						String c2[]=P.getCards().get(j).split(" ");
						if(!c1[1].equalsIgnoreCase(c2[1])) {
							for(int k=j+1;k<cardSize;k++) {
								String c3[]=P.getCards().get(k).split(" ");
								if(!c1[1].equalsIgnoreCase(c3[1]) && !c2[1].equalsIgnoreCase(c3[1])) {
							String card1=P.getCards().get(i);
							String card2=P.getCards().get(j);
							String card3=P.getCards().get(k);
							P.getCards().remove(card1);
							P.getCards().remove(card2);
							P.getCards().remove(card3);
							P.setView("CardExchangeView");
							P.setActions("Player has exchanged " + card1 + ", " + card2 + ", " + card3);
							P.setCardExchangeCounter(P.getCardExchangeCounter() + 5);
							reinforcementArmies += P.getCardExchangeCounter();
							map.setNoOfArmies(P, P.getNoOfArmies() + P.getCardExchangeCounter());
							System.out.println("Reinforcement armies added " + P.getCardExchangeCounter());
							System.out.println("Remaining armies to be placed : " + reinforcementArmies);
							P.deck.add(card1);
							P.deck.add(card2);
							P.deck.add(card3);
							flag=1;
							break;
								}
							}
						}
						if(flag==1)
						break;
					}
					if(flag==1)
					break;
				}
				if(flag==0)
					break;
			}
		}
		map.setNoOfArmies(P, (P.getNoOfArmies() + reinforcementArmies));
		flag=strongestCountry(map,listPlayer,P);
		if(flag==0) {
			simpleStrongestCountry(map,listPlayer,P);
		}
		P.setView("PhaseView");
		P.setActions("Reinforced " + reinforcementArmies + " armies to "+ strong.getName());
		strong.setNoOfArmies(strong.getNoOfArmies()+reinforcementArmies);
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
		if(strong.getNoOfArmies()==1) {
			System.out.println("Sorry!You cannot attack with 1 army in your strongest country");
			P.setEndOfActions(1); 
			P.setActions("Attack finished");
			return 0;
		}else {
			while(true) {
		for(int i=0;i<strong.getNeighbors().size();i++) {
			Country neighbor=map.getCountryFromName(strong.getNeighbors().get(i));
			if(!strong.getOwner().equals(neighbor.getOwner())) {
				//attack
				Country toCountry=neighbor;
				Country fromCountry=strong;
				int attackerDice = 0;
				int defenderDice=0,index = -1;
				for (int k = 0; k < listPlayer.size(); k++) {

					if (listPlayer.get(k).getName().equals(toCountry.getOwner())) {
						index = k;
						break;
					}
				}
				P.setActions("Attacking country: " + toCountry.getName() + " from country :" + fromCountry.getName());
				Player defender = listPlayer.get(index);
				while (toCountry.getNoOfArmies() != 0 && fromCountry.getNoOfArmies() != 1) {
					if (fromCountry.getNoOfArmies() > 3)
						attackerDice = 3;
					else if(fromCountry.getNoOfArmies() == 3)
						attackerDice = 2;
					else
						attackerDice = 1;
					if (toCountry.getNoOfArmies() >= 2)
						defenderDice = 2;
					else
						defenderDice = 1;
					Dice diceRoll = new Dice(attackerDice, defenderDice);
					int result[][] = diceRoll.rollAll();
					//System.out.println("Dice Roll Output:");
					//diceRoll.print(result);
					result = diceRoll.sort(result);
					int min = Math.min(attackerDice, defenderDice);
					for (int j = 0; j < min; j++) {
						if (result[0][j] > result[1][j])// attacker wins
						{
							map.setNoOfArmies(defender, defender.getNoOfArmies() - 1);
							toCountry.setNoOfArmies(toCountry.getNoOfArmies() - 1);
						} else { // defender wins
							map.setNoOfArmies(P, P.getNoOfArmies() - 1);
							fromCountry.setNoOfArmies((fromCountry.getNoOfArmies() - 1));
						}
						if (fromCountry.getNoOfArmies() == 1)
							break;
					}
				}
				if (fromCountry.getNoOfArmies() == 1) {
					System.out.println("Player :" + defender.getName() + " has defended successfully and attacking country :"
							+ fromCountry.getName() + " has only 1 army left");
					P.setActions("Player :" + defender.getName() + " has defended successfully and attacking country :"
							+ fromCountry.getName() + " has only 1 army left");
				}
					if (toCountry.getNoOfArmies() == 0) { // attacker has conquered the defending country.
						map.setCountryOwner(toCountry, P.getName());
						P.getAssigned_countries().add(toCountry);
						P.setActions(P.getName() + " has conquered country: " + toCountry.getName());
						P.setActions("Moving armies"+(fromCountry.getNoOfArmies() -1)+ " to country: " + toCountry.getName());
						defender.getAssigned_countries().remove(toCountry);
						toCountry.setNoOfArmies(fromCountry.getNoOfArmies() -1);
						fromCountry.setNoOfArmies(1);
						System.out.println("You have conquered country: " + toCountry.getName());
						
						if (defender.getAssigned_countries().size() == 0) {// defender is out of the game
							listPlayer.remove(defender);
							// checking for game finish condition
							if (endGame(listPlayer) == 1)
								return 1;

						}
						else {
							String card = P.randomCard();
							if(!card.equals("None")) {
							P.getCards().add(card);
							System.out.println("You have received: " + card + " card");
							P.setActions(P.getName() + " has received: " + card + " card");
							Player.deck.remove(card);
							}else {
								System.out.println("No more cards available");
							}
						}
						Continent cont = map.getContinentFromName(toCountry.getContinentName());
						int flag = 0;
						for (String country : cont.getCountries()) {
							Country c = map.getCountryFromName(country);
							if (!P.getName().equals(c.getOwner())) {
								flag = 1;
								map.setContinentOwner(cont, "FREE CONTINENTS");
								break;
							}
						}
						if (flag == 0) { // continent has been conquered
							map.setContinentOwner(cont, P.getName());
							System.out.println("You have conquered continent: " + cont.getName());
							P.setActions(P.getName()+ " has conquered continent: " + cont.getName());
						}
					}
					int flag=strongestCountry(map,listPlayer,P);
					if(flag==0) {
						P.setEndOfActions(1); 
						P.setActions("Attack finished");
						return 0;
					}
			}
			if(strong.getNoOfArmies()==1) {
				P.setEndOfActions(1); 
				P.setActions("Attack finished");
				return 0;
			}
		}
		}
		}
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
		int max =0;
		Country c=new Country();
		max=1;
		int flag=strongestCountry(map,listPlayer,P);
		
		if(flag==1) {
		for(int i=0;i<strong.getNeighbors().size();i++) {
			Country neighbor=map.getCountryFromName(strong.getNeighbors().get(i));
			if(strong.getOwner().equals(neighbor.getOwner())) {
				if(neighbor.getNoOfArmies()>max) {
					max=neighbor.getNoOfArmies();
					c=neighbor;
				}
			}
		}
		if(max>1) {
			int army=c.getNoOfArmies()-1;
			strong.setNoOfArmies(strong.getNoOfArmies()+army);
			c.setNoOfArmies(1);
			System.out.println("Fortification successful");
			P.setEndOfActions(1); 
			P.setActions("Fortified " + strong.getName() + " with " + army + " armies from " + c.getName());
			return;
		}
		else {
			simpleStrongestCountry(map,listPlayer,P);
			max=1;
			for(int i=0;i<strong.getNeighbors().size();i++) {
				Country neighbor=map.getCountryFromName(strong.getNeighbors().get(i));
				if(strong.getOwner().equals(neighbor.getOwner())) {
					if(neighbor.getNoOfArmies()>max) {
						max=neighbor.getNoOfArmies();
						c=neighbor;
					}
				}
			}
			if(max>1) {
				int army=c.getNoOfArmies()-1;
				strong.setNoOfArmies(strong.getNoOfArmies()+army);
				c.setNoOfArmies(1);
				System.out.println("Fortification successful");
				P.setActions("Fortified " + strong.getName() + " with " + army + " armies from " + c.getName());
				P.setEndOfActions(1); 
				P.setActions("Fortification finished");
				return;
			}
			else {
				System.out.println("Cannot fortify as all the neighboring countries of strongest country have only 1 army left");
				P.setEndOfActions(1); 
				P.setActions("Fortification finished");
				return;
			}
		}
	}else {simpleStrongestCountry(map,listPlayer,P);
	max=1;
	for(int i=0;i<strong.getNeighbors().size();i++) {
		Country neighbor=map.getCountryFromName(strong.getNeighbors().get(i));
		if(strong.getOwner().equals(neighbor.getOwner())) {
			if(neighbor.getNoOfArmies()>max) {
				max=neighbor.getNoOfArmies();
				c=neighbor;
			}
		}
	}
	if(max>1) {
		int army=c.getNoOfArmies()-1;
		strong.setNoOfArmies(strong.getNoOfArmies()+army);
		c.setNoOfArmies(1);
		System.out.println("Fortification successful");
		P.setActions("Fortified " + strong.getName() + " with " + army + " armies from " + c.getName());
		P.setEndOfActions(1); 
		P.setActions("Fortification finished");
		return;
	}
	else
		System.out.println("Cannot fortify as all the neighboring countries of strongest country have only 1 army left");
	P.setEndOfActions(1); 
	P.setActions("Fortification finished");
		return;
		
	}
		
	}
	/**
	 * Function to check the end of game
	 * @param listPlayer list of players
	 * @return 1 if end of game otherwise 0.
	 */
	public int endGame(ArrayList<Player> listPlayer) {
		if (listPlayer.size() == 1)
			return 1;
		return 0;
	}
}
