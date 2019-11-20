package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class HumanStrategy implements Strategy {
	public void reinforcement(Map map, ArrayList<Player> listPlayer,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseViewCardExchangeView");
		P.setState("Reinforcement");
		Scanner sc = new Scanner(System.in);
		// calculate reinforcement armies
		int reinforcementArmies = P.calculateReinforceArmies(map, listPlayer);
		int forceExchangeCards = 0;

		if (P.getCards().size() >= 5) {
			forceExchangeCards = 1;
		} else
			forceExchangeCards = 0;
		// loop over playerlist and assign reinforcement armies
		System.out.println("Reinforcement armies to be assigned :" + reinforcementArmies);
		System.out.println("Type reinforce <countryname> <num>  to assign armies ");
		System.out.println("Type exchangecards <num> <num> <num> -none to exchange cards\n Type showmap");
		map.setNoOfArmies(P, P.getNoOfArmies() + reinforcementArmies);
		while (reinforcementArmies != 0 || forceExchangeCards == 1) {

			String input = sc.nextLine();
			String[] inputArray = input.split(" ");

			if (P.getCards().size() >= 5) {
				forceExchangeCards = 1;
			} else
				forceExchangeCards = 0;

			if (input.equals("showmap")) {

				map.displayAll();
			} else if (inputArray.length == 3 && inputArray[0].equals("reinforce")) {
				int armiesTobeplaced =0;
				try {
				armiesTobeplaced = Integer.parseInt(inputArray[2]);
				}catch(Exception e) {
					System.out.println("Invalid command");
					continue;
				}
				int countryFound = 0;

				ArrayList<Country> countryTempList = (ArrayList<Country>) P.getAssigned_countries();
				for (Country c : countryTempList) {
					if (c.getName().equals(inputArray[1]))
						countryFound = 1;
				}
				if (countryFound == 0)
					System.out.println("Country is not assigned to player ");
				else {
					if (armiesTobeplaced <= reinforcementArmies && armiesTobeplaced > 0) { // check reinforce command
						// and country is valid and
						// assigned to player
						map.getCountryFromName(inputArray[1]).setNoOfArmies(
								map.getCountryFromName(inputArray[1]).getNoOfArmies() + armiesTobeplaced);
						reinforcementArmies -= armiesTobeplaced;
						System.out.println("Reinforcement armies placed successfully");
						System.out.println("Remaining armies to be placed : " + reinforcementArmies);
						P.setView("PhaseView");
						P.setActions("Reinforced " + armiesTobeplaced + " armies to "
								+ map.getCountryFromName(inputArray[1]).getName());
						if (forceExchangeCards == 1 && reinforcementArmies == 0)
							System.out.println(
									"You have more than 5 exchange cards ,please exchange cards to proceed to attack phase");

					} else
						System.out.println(
								"Number of armies to be assigned should be in the range : 1 -" + reinforcementArmies);
				}
			} else if (inputArray.length == 4 && inputArray[0].equals("exchangecards")) {
				if (P.getCards().size() < 3) {
					System.out.println("You don't have enough cards to exchange");
					continue;
				}

				// check if three numbers are valid
				int num1;
				int num2;
				int num3;
				boolean IsExchangeCards = false;
				try {
					num1 = Integer.parseInt(inputArray[1]) - 1;
					num2 = Integer.parseInt(inputArray[2]) - 1;
					num3 = Integer.parseInt(inputArray[3]) - 1;
					// check if three numbers are valid cards indexes in player's hand
					ArrayList<String> playerCards = P.getCards();
					String card1 = playerCards.get(num1);
					String card2 = playerCards.get(num2);
					String card3 = playerCards.get(num3);
					if (!card1.isEmpty() && !card2.isEmpty() && !card3.isEmpty()) {
						// numbers are valid ,check if all same or all different
						String[] card1arr = card1.split(" ");
						String[] card2arr = card2.split(" ");
						String[] card3arr = card3.split(" ");

						String concatCards = card1arr[1] + card2arr[1] + card3arr[1];
						if (concatCards.equalsIgnoreCase("infantryinfantryinfantry")
								|| concatCards.equalsIgnoreCase("cavalrycavalrycavalry")
								|| concatCards.equalsIgnoreCase("artilleryartilleryartillery")) {
							// all same
							IsExchangeCards = true;
						} else {
							Set<String> diffChecker = new HashSet<String>();
							diffChecker.add(card1arr[1]);
							diffChecker.add(card2arr[1]);
							diffChecker.add(card3arr[1]);
							if (diffChecker.size() == 3) {
								// all different
								IsExchangeCards = true;
							} else {
								System.out.println("Exchange not possible");
								continue;
							}
						}
					}

					if (IsExchangeCards) {
						playerCards.remove(card1);
						playerCards.remove(card2);
						playerCards.remove(card3);
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
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid Command. 'num' should be a number.");
					continue;
				} catch (NullPointerException e) {
					System.out.println("Invalid Command. 'num' should be a number.");
					continue;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid card number.");
					continue;
				}

			} else if (inputArray.length == 2 && inputArray[0].equals("exchangecards")
					&& inputArray[1].equals("-none")) {
				// Player chooses not to exchange cards
				// check if cards is more than 5
				if (forceExchangeCards == 1)
					System.out.println("Exchange cards cannot be skipped as no. of cards is more than 5");
				else if (forceExchangeCards == 0)
					System.out.println("Exchange cards skipped");

			}

			else
				System.out.println("Invalid command .Type again");
		}
		P.setEndOfActions(1);
		P.setActions("Reinforcement finished");
	}
	public void fortification(Map map, ArrayList<Player> listPlayer, String command,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Fortification");
		Scanner sc2 = new Scanner(System.in);
		String in;
		int flag = 0;
		do {

			System.out.println(
					"Type fortify <from country name> <to country name> <number of armies> or fortify -none (choose to not do a move)\n Type showmap");
			if (command != null)
				in = command;
			else
				in = sc2.nextLine();
			String input[] = in.split(" ");
			if (in.equals("showmap")) {
				map.displayAll();
			} else if (input.length == 4 && input[0].equals("fortify")) {
				HashMap<Integer, List<Integer>> mapOfAssignedCountries = new HashMap<Integer, List<Integer>>();
				for (int i = 0; i < P.getAssigned_countries().size(); i++) {
					List<Integer> templist = new ArrayList<Integer>();
					for (int j = 0; j < P.getAssigned_countries().get(i).getNeighbors().size(); j++) {
						if (map.getCountryFromName(P.getAssigned_countries().get(i).getNeighbors().get(j)).getOwner()
								.equals(P.getName())) {
							for (int k = 0; k < P.getAssigned_countries().size(); k++) {
								if (P.getAssigned_countries().get(k).equals(map.getCountryFromName(
										P.getAssigned_countries().get(i).getNeighbors().get(j)))) {
									templist.add(k);
								}
							}
						}
					}
					mapOfAssignedCountries.put(i, templist);
				}
				int source = -1, destination = -1, validPath = 0;
				for (int k = 0; k < P.getAssigned_countries().size(); k++) {
					if (P.getAssigned_countries().get(k).getName().equals(input[1])) {
						source = k;
					}
					if (P.getAssigned_countries().get(k).getName().equals(input[2])) {
						destination = k;
					}
				}
				if (source == -1 || destination == -1) {
					if (source == -1 && destination == -1) {
						System.out.println("Sorry!From Country:" + input[1] + " and To Country :" + input[2]
								+ " doesn't belong to you");
						if (command != null)
							return;
					} else if (source == -1) {
						System.out.println("Sorry!From Country :" + input[1] + " doesn't belong to you");
						if (command != null)
							return;
					} else {
						System.out.println("Sorry!To Country :" + input[2] + " doesn't belong to you");
						if (command != null)
							return;
					}
				} else {
					Boolean[] visited = new Boolean[mapOfAssignedCountries.keySet().size()];
					for (int i = 0; i < visited.length; i++) {
						visited[i] = false;
					}
					LinkedList<Integer> queue = new LinkedList<Integer>();
					queue.add(source);
					visited[source] = true;
					while (queue.size() > 0) {
						Integer c1 = queue.poll();
						Iterator<Integer> i = mapOfAssignedCountries.get(c1).listIterator();
						while (i.hasNext()) {
							int n = (int) i.next();

							if (n == destination) {
								validPath = 1;
								break;
							}
							if (visited[n] == false) {
								visited[n] = true;
								queue.add(n);
							}
						}
						if (validPath == 1)
							break;
					}
					if (validPath == 1) {
						if (P.getAssigned_countries().get(source).getNoOfArmies() == 1) {
							System.out.println(
									"Sorry! You cannot fortify for this combination as the from country has only 1 army");
							if (command != null)
								return;
						} else if (Integer.parseInt(input[3]) > 0 && (Integer.parseInt(input[3]) < P
								.getAssigned_countries().get(source).getNoOfArmies())) {

							P.getAssigned_countries().get(source)
									.setNoOfArmies((P.getAssigned_countries().get(source).getNoOfArmies())
											- Integer.parseInt(input[3]));
							P.getAssigned_countries().get(destination)
									.setNoOfArmies(P.getAssigned_countries().get(destination).getNoOfArmies()
											+ Integer.parseInt(input[3]));
							System.out.println("Fortification successful");
							P.setEndOfActions(1);
							P.setView("PhaseView");
							P.setActions("Fortified " + input[2] + " with " + input[3] + " armies from " + input[1]);
							return;

						} else {
							System.out.println("Invalid no of armies specified, for these two countries it can be 1-"
									+ (P.getAssigned_countries().get(source).getNoOfArmies() - 1));
							if (command != null)
								return;
						}
					} else {
						System.out.println(
								"There's no path between the mentioned countries.(You can move any number of armies from one of the owned countries to the other, provided that there is a path between these two countries that is composed of countries owned by you)");
						if (command != null)
							return;
					}
				}
			} else if (in.equals("fortify -none")) {
				System.out.println("Skipped fortification");
				P.setEndOfActions(1);
				P.setView("PhaseView");
				P.setActions("Skipped fortification");
				return;
			} else {
				System.out.println("Invalid command,type again");
				if (command != null)
					return;
			}
		} while (flag == 0);
	}
	public int attack(Map map, ArrayList<Player> listPlayer,Player P) {
		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Attack");
		Scanner sc3 = new Scanner(System.in);
		int attackDeadlock = 0;
		attackDeadlock = 0;
		// checking for deadlock
		attackDeadlock = P.attackDeadlock(map);
		if (attackDeadlock == 1) {
			P.setEndOfActions(1);
			System.out.println("You cannot attack now because of the attack deadlock.");
			P.setActions("You cannot attack now because of the attack deadlock." + "\n Attack Finished.");
			return 0;
		}
		System.out.println("Type attack <countrynamefrom> <countynameto> <numdice> for a single attack");
		System.out.println("attack <countrynamefrom> <countynameto> -allout for an attack until no attack is possible\nattack –noattack to end attack phase");
		System.out.println("Type showmap");
		String input;
		do {
			attackDeadlock = 0;
			// checking for deadlock
			attackDeadlock = P.attackDeadlock(map);
			if (attackDeadlock == 1) {
				P.setEndOfActions(1);
				System.out.println("You cannot attack now because of the attack deadlock.");
				P.setActions("You cannot attack now because of the attack deadlock." + "\n Attack Finished.");
				return 0;
			}
			input = sc3.nextLine();
			while (P.validate(input, map) == 0) {
				System.out.println("Invalid command, Kindly type again");
				input = sc3.nextLine();
			}
			if (input.equals("showmap")) {
				map.displayAll();
			} else {

				String s[] = input.split(" ");
				if (!input.equals("attack -noattack")) {
					Country fromCountry = map.getCountryFromName(s[1]);
					System.out.println("Valid command");
					int attackerDice = 0;

					int defenderDice = 0;
					Country toCountry = map.getCountryFromName(s[2]);
					int validCommand = 0;
					String defend = toCountry.getOwner();
					int index = -1;
					for (int i = 0; i < listPlayer.size(); i++) {

						if (listPlayer.get(i).getName().equals(toCountry.getOwner())) {
							index = i;
							break;
						}
					}
					P.setActions("Attacking country: " + toCountry.getName() + " from country :" + fromCountry.getName());
					Player defender = listPlayer.get(index);
					int isAllout = 0;
					if (s[3].equals("-allout")) {
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
							System.out.println("Dice Roll Output:");
							diceRoll.print(result);
							result = diceRoll.sort(result);
							int min = Math.min(attackerDice, defenderDice);
							for (int i = 0; i < min; i++) {
								if (result[0][i] > result[1][i])// attacker wins
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
						isAllout = 1;
					} else {
						attackerDice = Integer.parseInt(s[3]);
						System.out.println("Player :" + defend + " has to defend country :" + s[2]
								+ " \nType defend numdice to choose no of dices to defend your country.");
						P.setActions("Player :" + defend + " has to defend country :" + s[2]);
						while (validCommand == 0) {
							input = sc3.nextLine();
							String str[] = input.split(" ");
							if (str.length == 2 && str[0].equals("defend")) {
								int dice = Integer.parseInt(str[1]);
								int noOfArmies = toCountry.getNoOfArmies();
								if (dice > 0 && dice < 3 && dice <= noOfArmies) {
									defenderDice = dice;
									validCommand = 1;
								} else
									System.out.println("Incorrect number of dices");
							} else {
								System.out.println("Invalid command,type again.");
							}
						}

						if (validCommand == 1) {
							Dice diceRoll = new Dice(attackerDice, defenderDice);
							int result[][] = diceRoll.rollAll();
							System.out.println("Dice Roll Output:");
							diceRoll.print(result);
							result = diceRoll.sort(result);
							int min = Math.min(attackerDice, defenderDice);
							for (int i = 0; i < min; i++) {
								if (result[0][i] > result[1][i])// attacker wins
								{
									defender.setNoOfArmies(defender.getNoOfArmies() - 1);
									toCountry.setNoOfArmies(toCountry.getNoOfArmies() - 1);
									System.out.println("Defender lost 1 army");
									P.setActions("Defender lost 1 army");
								} else { // defender wins
									P.setNoOfArmies(P.getNoOfArmies() - 1);
									fromCountry.setNoOfArmies(fromCountry.getNoOfArmies() - 1);
									System.out.println("Attacker lost 1 army");
									P.setActions("Attacker lost 1 army");
								}
							}
						}
					}
					if (validCommand == 1 || isAllout == 1) {
						if (toCountry.getNoOfArmies() == 0) { // attacker has conquered the defending country.
							map.setCountryOwner(toCountry, P.getName());
							P.getAssigned_countries().add(toCountry);
							defender.getAssigned_countries().remove(toCountry);
							/*toCountry.setNoOfArmies(1);
							fromCountry.setNoOfArmies(fromCountry.getNoOfArmies() - 1);*/
							System.out.println("You have conquered country: " + toCountry.getName());
							P.setActions(P.getName() + " has conquered country: " + toCountry.getName());
							if (defender.getAssigned_countries().size() == 0) {// defender is out of the game
								for (int i = 0; i < defender.getCards().size(); i++) {
									P.getCards().add(defender.getCards().get(i));
								}
								listPlayer.remove(defender);
								// checking for game finish condition
								if (P.endGame(listPlayer) == 1)
									return 1;

							} else {
								String card = P.randomCard();
								//P.cards.add(card);
								P.getCards().add(card);
								System.out.println("You have received: " + card + " card");
								P.setActions(P.getName() + " has received: " + card + " card");
								P.deck.remove(card);
							}
							// System.out.println(fromCountry.getNoOfArmies());
							
								System.out.println(
										"Move armies from " + fromCountry.getName() + " to " + toCountry.getName());
								System.out.println("Number of dices used by the attacker in the last attack:"+attackerDice);
								System.out.println(
										"Available armies you can move : " + attackerDice+"-"+ (fromCountry.getNoOfArmies() - 1));
								System.out.println("Type attackmove <number> to move");
								int valid = 0;
								do {
									String command = sc3.nextLine();
									valid = P.attackMove(command, fromCountry, toCountry,attackerDice);
								} while (valid == 0);
							
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
					}
				}
			}

		} while (!input.equals("attack -noattack") && attackDeadlock == 0);
		if (attackDeadlock == 1) {

		}
		return 0;
	}
}
