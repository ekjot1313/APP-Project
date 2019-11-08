package dao;

import java.util.*;
import dao.Map;

/**
 * This class represents the Player details
 * 
 * @author Piyush
 *
 */
public class Player extends pattern.Observable {

	/**
	 * To store the current view
	 */
	private String view;

	/**
	 * To store end of actions
	 */
	private int endOfActions;

	/**
	 * To store current state- Reinforcement ,attack, fortify
	 */
	private String state;

	/**
	 * deck of cards
	 */
	public static ArrayList<String> deck;
	/**
	 * TO store current actions
	 */
	private String actions;

	/**
	 * The name of the player
	 */
	private String name;
	/**
	 * List of cards for a player
	 */
	/**
	 * This holds the count of no of times the player has exchanged cards to get
	 * armies.
	 */
	private int cardExchangeCounter;

	/**
	 * ArrayList of cards
	 */
	private ArrayList<String> cards;

	/**
	 * Number of armies assigned ot the player
	 */
	private int noOfArmies = 0;
	/**
	 * Number of unassigned armies
	 */
	private int unassignedarmies = 0;
	/**
	 * list of assigned countries
	 */
	private List<Country> assigned_countries;

	/**
	 * Object of scanner class
	 */
	private Scanner sc;

	/**
	 * Object of scanner class
	 */
	private Scanner sc2;

	/**
	 * Object of scanner class
	 */
	private Scanner sc3;

	/**
	 * Constructor initializes the list of assigned countries
	 */
	public Player() {
		assigned_countries = new ArrayList<Country>();
		cards = new ArrayList<String>();
	}

	/**
	 * To get random card
	 * @return card
	 */
	public String randomCard() {
		Random number = new Random();
		int no = number.nextInt(deck.size());
		return deck.get(no);
	}

	/**
	 * This method returns the number of unassigned armies
	 * @return Number of Unassigned Armies
	 */
	public int getUnassignedarmies() {
		return unassignedarmies;
	}

	/**
	 * This method sets the number of unassigned armies
	 * @param unassignedarmies Number of unassigned armies
	 */

	public void setUnassignedarmies(int unassignedarmies) {
		this.unassignedarmies = unassignedarmies;
	}

	/**
	 * This method returns the name of the player.
	 * @return Player Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the player.
	 * @param name Player Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method returns the number of armies for a player.
	 * @return Number of armies for a player
	 */
	public int getNoOfArmies() {
		return noOfArmies;
	}

	/**
	 * This method sets the number of armies for a player.
	 * @param noOfArmies Number of armies of a particular player
	 */
	public void setNoOfArmies(int noOfArmies) {
		this.noOfArmies = noOfArmies;
	}

	/**
	 * This method returns the list of countries belonging to a player.
	 * @return List of countries belonging to a player
	 */
	public List<Country> getAssigned_countries() {
		return assigned_countries;
	}
	
	/**
	 * To get the current view
	 * @return view
	 */
	public String getView() {
		return view;
	}

	/**
	 * To set the current view
	 * @param view
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * This method sets the list of countries belonging to a player.
	 * 
	 * @param assigned_countries Countries assigned to a player
	 */
	public void setAssigned_countries(List<Country> assigned_countries) {
		this.assigned_countries = assigned_countries;
	}

	/**
	 * To get end of actions
	 * @return endOfActions
	 */
	public int getEndOfActions() {
		return endOfActions;
	}

	/**
	 * TO set end of actions
	 * @param endOfActions
	 */
	public void setEndOfActions(int endOfActions) {
		this.endOfActions = endOfActions;

	}
	/**
	 * TO get current actions
	 * @return actions
	 */
	public String getActions() {
		return actions;
	}

	/**
	 * To store current action
	 * @param actions
	 */
	public void setActions(String actions) {
		this.actions = actions;
		notify(this);
		this.actions = "";
	}

	/**
	 * TO get current state
	 * @return state
	 */
	public String getState() {
		return state;
	}

	/**
	 * To set current state
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
		notify(this);
	}
	
	/**
	 * This method returns the cardExchangeCounter.
	 * 
	 * @return
	 */
	public int getCardExchangeCounter() {
		return cardExchangeCounter;
	}

	/**
	 * This method sets the cardExchangeCounter.
	 * 
	 * @param cardExchangeCounter
	 */
	public void setCardExchangeCounter(int cardExchangeCounter) {
		this.cardExchangeCounter = cardExchangeCounter;
	}
	
	/**
	 * This method returns the card list.
	 * @return
	 */
	public ArrayList<String> getCards() {
		return cards;
	}

	/**
	 * This method sets the card list.
	 * @param cards
	 */
	public void setCards(ArrayList<String> cards) {
		this.cards = cards;
	}

	/**
	 * This method calculates the number of reinforcement armies
	 * 
	 * @param listPlayer  List of Players
	 * @param map         Map Object
	 * @return Number of reinforcement armies
	 */
	public int calculateReinforceArmies(Map map, ArrayList<Player> listPlayer) {
		// calculating on the basis of no of countries the player own
		int noOfArmies = this.getAssigned_countries().size() / 3;
		int reinforcementArmies = noOfArmies <= 3 ? 3 : noOfArmies;

		// calculating on the basis of continents owned

		for (Continent continent : map.getListOfContinent()) {
			if (continent.getOwner().equals(this.getName())) {
				reinforcementArmies += continent.getContinentValue();
		
			}
		}

		return reinforcementArmies;
	}

	/**
	 * This method is used for reinforcement phase and exchange of cards.
	 * 
	 * @param listPlayer  List of players
	 * @param map         Map object which contains the map details like continents
	 *                    and countries.
	 *                    
	 */
	public void reinforcement(Map map, ArrayList<Player> listPlayer) {
		endOfActions = 0;
		setView("PhaseViewCardExchangeView");
		setState("Reinforcement");
		sc = new Scanner(System.in);
		// calculate reinforcement armies
		int reinforcementArmies = calculateReinforceArmies(map, listPlayer);
		int forceExchangeCards = 0;

		if (this.getCards().size() >= 5) {
			forceExchangeCards = 1;
		} else
			forceExchangeCards = 0;
		// loop over playerlist and assign reinforcement armies
		System.out.println("Reinforcement armies to be assigned :" + reinforcementArmies);
		System.out.println("Type reinforce <countryname> <num>  to assign armies ");
		System.out.println("Type exchangecards <num> <num> <num> -none to exchange cards\n Type showmap");
		map.setNoOfArmies(this, this.getNoOfArmies() + reinforcementArmies);
		while (reinforcementArmies != 0 || forceExchangeCards == 1) {

			String input = sc.nextLine();
			String[] inputArray = input.split(" ");

			if (this.getCards().size() >= 5) {
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

				ArrayList<Country> countryTempList = (ArrayList<Country>) this.getAssigned_countries();
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
						setView("PhaseView");
						setActions("Reinforced " + armiesTobeplaced + " armies to "
								+ map.getCountryFromName(inputArray[1]).getName());
						if (forceExchangeCards == 1 && reinforcementArmies == 0)
							System.out.println(
									"You have more than 5 exchange cards ,please exchange cards to proceed to attack phase");

					} else
						System.out.println(
								"Number of armies to be assigned should be in the range : 1 -" + reinforcementArmies);
				}
			} else if (inputArray.length == 4 && inputArray[0].equals("exchangecards")) {
				if (this.getCards().size() < 3) {
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
					ArrayList<String> playerCards = this.getCards();
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
						setView("CardExchangeView");
						setActions("Player has exchanged " + card1 + ", " + card2 + ", " + card3);

						this.setCardExchangeCounter(this.getCardExchangeCounter() + 5);
						reinforcementArmies += this.getCardExchangeCounter();
						map.setNoOfArmies(this, this.getNoOfArmies() + this.getCardExchangeCounter());
						System.out.println("Reinforcement armies added " + this.getCardExchangeCounter());
						System.out.println("Remaining armies to be placed : " + reinforcementArmies);
						deck.add(card1);
						deck.add(card2);
						deck.add(card3);
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
		endOfActions = 1;
		setActions("Reinforcement finished");
	}

	/**
	 * This function is used for fortification phase.
	 * 
	 * @param listPlayer  List of players
	 * @param map         Map object which contains the map details like continents
	 *                    and countries.
	 * @param command	For testing
	 */
	public void fortification(Map map, ArrayList<Player> listPlayer, String command) {
		endOfActions = 0;
		setView("PhaseView");
		setState("Fortification");
		sc2 = new Scanner(System.in);
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
				for (int i = 0; i < this.getAssigned_countries().size(); i++) {
					List<Integer> templist = new ArrayList<Integer>();
					for (int j = 0; j < this.getAssigned_countries().get(i).getNeighbors().size(); j++) {
						if (map.getCountryFromName(this.getAssigned_countries().get(i).getNeighbors().get(j)).getOwner()
								.equals(this.getName())) {
							for (int k = 0; k < this.getAssigned_countries().size(); k++) {
								if (this.getAssigned_countries().get(k).equals(map.getCountryFromName(
										this.getAssigned_countries().get(i).getNeighbors().get(j)))) {
									templist.add(k);
								}
							}
						}
					}
					mapOfAssignedCountries.put(i, templist);
				}
				int source = -1, destination = -1, validPath = 0;
				for (int k = 0; k < this.getAssigned_countries().size(); k++) {
					if (this.getAssigned_countries().get(k).getName().equals(input[1])) {
						source = k;
					}
					if (this.getAssigned_countries().get(k).getName().equals(input[2])) {
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
						if (this.getAssigned_countries().get(source).getNoOfArmies() == 1) {
							System.out.println(
									"Sorry! You cannot fortify for this combination as the from country has only 1 army");
							if (command != null)
								return;
						} else if (Integer.parseInt(input[3]) > 0 && (Integer.parseInt(input[3]) < this
								.getAssigned_countries().get(source).getNoOfArmies())) {

							this.getAssigned_countries().get(source)
									.setNoOfArmies((this.getAssigned_countries().get(source).getNoOfArmies())
											- Integer.parseInt(input[3]));
							this.getAssigned_countries().get(destination)
									.setNoOfArmies(this.getAssigned_countries().get(destination).getNoOfArmies()
											+ Integer.parseInt(input[3]));
							System.out.println("Fortification successful");
							endOfActions = 1;
							setView("PhaseView");
							setActions("Fortified " + input[2] + " with " + input[3] + " armies from " + input[1]);
							return;

						} else {
							System.out.println("Invalid no of armies specified, for these two countries it can be 1-"
									+ (this.getAssigned_countries().get(source).getNoOfArmies() - 1));
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
				endOfActions = 1;
				setView("PhaseView");
				setActions("Skipped fortification");
				return;
			} else {
				System.out.println("Invalid command,type again");
				if (command != null)
					return;
			}
		} while (flag == 0);

	}

	/**
	 * This method is used for attack phase.
	 * @param map Object of Map
	 * @param listPlayer list of Players
	 * @return 1 if attack is successful otherwise 0.
	 * @throws Exception
	 */
	public int attack(Map map, ArrayList<Player> listPlayer) throws Exception {
		endOfActions = 0;
		setView("PhaseView");
		setState("Attack");
		sc3 = new Scanner(System.in);
		int attackDeadlock = 0;
		attackDeadlock = 0;
		// checking for deadlock
		attackDeadlock = attackDeadlock(map);
		if (attackDeadlock == 1) {
			endOfActions = 1;
			System.out.println("You cannot attack now because of the attack deadlock.");
			setActions("You cannot attack now because of the attack deadlock." + "\n Attack Finished.");
			return 0;
		}
		System.out.println("Type attack <countrynamefrom> <countynameto> <numdice> for a single attack");
		System.out.println("attack <countrynamefrom> <countynameto> -allout for an attack until no attack is possible");
		System.out.println("attack –noattack to end attack phase");
		System.out.println("Type showmap");
		String input;
		do {
			attackDeadlock = 0;
			// checking for deadlock
			attackDeadlock = attackDeadlock(map);
			if (attackDeadlock == 1) {
				endOfActions = 1;
				System.out.println("You cannot attack now because of the attack deadlock.");
				setActions("You cannot attack now because of the attack deadlock." + "\n Attack Finished.");
				return 0;
			}
			input = sc3.nextLine();
			while (validate(input, map) == 0) {
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
					setActions("Attacking country: " + toCountry.getName() + " from country :" + fromCountry.getName());
					Player defender = listPlayer.get(index);
					int isAllout = 0;
					if (s[3].equals("-allout")) {
						while (toCountry.getNoOfArmies() != 0 && fromCountry.getNoOfArmies() != 1) {
							if (fromCountry.getNoOfArmies() >= 3)
								attackerDice = 3;
							else
								attackerDice = 2;
							if (toCountry.getNoOfArmies() >= 2)
								defenderDice = 2;
							else
								defenderDice = 1;
							Dice diceRoll = new Dice(attackerDice, defenderDice);
							int result[][] = diceRoll.rollAll();
							result = diceRoll.sort(result);
							int min = Math.min(attackerDice, defenderDice);
							for (int i = 0; i < min; i++) {
								if (result[0][i] > result[1][i])// attacker wins
								{
									map.setNoOfArmies(defender, defender.getNoOfArmies() - 1);
									toCountry.setNoOfArmies(toCountry.getNoOfArmies() - 1);
								} else { // defender wins
									map.setNoOfArmies(this, this.noOfArmies - 1);
									fromCountry.setNoOfArmies((fromCountry.getNoOfArmies() - 1));
								}
								if (fromCountry.getNoOfArmies() == 1)
									break;
							}
						}
						if (fromCountry.getNoOfArmies() == 1) {
							System.out.println("Player :" + defender.getName() + " has defended successfully and attacking country :"
									+ fromCountry.getName() + " has only 1 army left");
							this.setActions("Player :" + defender.getName() + " has defended successfully and attacking country :"
									+ fromCountry.getName() + " has only 1 army left");
						}
						isAllout = 1;
					} else {
						attackerDice = Integer.parseInt(s[3]);
						System.out.println("Player :" + defend + " has to defend country :" + s[2]
								+ " \nType defend numdice to choose no of dices to defend your country.");
						this.setActions("Player :" + defend + " has to defend country :" + s[2]);
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
									this.setActions("Defender lost 1 army");
								} else { // defender wins
									this.noOfArmies = this.noOfArmies - 1;
									fromCountry.setNoOfArmies(fromCountry.getNoOfArmies() - 1);
									System.out.println("Attacker lost 1 army");
									this.setActions("Attacker lost 1 army");
								}
							}
						}
					}
					if (validCommand == 1 || isAllout == 1) {
						if (toCountry.getNoOfArmies() == 0) { // attacker has conquered the defending country.
							map.setCountryOwner(toCountry, this.name);
							this.getAssigned_countries().add(toCountry);
							defender.getAssigned_countries().remove(toCountry);
							toCountry.setNoOfArmies(1);
							fromCountry.setNoOfArmies(fromCountry.getNoOfArmies() - 1);
							System.out.println("You have conquered country: " + toCountry.getName());
							setActions(this.name + " has conquered country: " + toCountry.getName());
							if (defender.getAssigned_countries().size() == 0) {// defender is out of the game
								for (int i = 0; i < defender.getCards().size(); i++) {
									this.getCards().add(defender.getCards().get(i));
								}
								listPlayer.remove(defender);
								// checking for game finish condition
								if (endGame(listPlayer) == 1)
									return 1;

							} else {
								String card = this.randomCard();
								this.cards.add(card);
								System.out.println("You have received: " + card + " card");
								this.setActions(this.name + " has received: " + card + " card");
								deck.remove(card);
							}
							// System.out.println(fromCountry.getNoOfArmies());
							if (fromCountry.getNoOfArmies()> attackerDice) {
								System.out.println(
										"Move armies from " + fromCountry.getName() + " to " + toCountry.getName());
								System.out.println("Number of dices used by the attacker in the last attack:"+attackerDice);
								System.out.println(
										"Available armies you can move : " + attackerDice+"-"+ (fromCountry.getNoOfArmies() - 1));
								System.out.println("Type attackmove <number> to move");
								int valid = 0;
								do {
									String command = sc3.nextLine();
									valid = attackMove(command, fromCountry, toCountry,attackerDice);
								} while (valid == 0);
							}
							else
								System.out.println(
										"You cannot move more armies to the conquered country as you have less or equal number of armies left in the attacking country as compared to the no of dices rolled for the last attack.");
							Continent cont = map.getContinentFromName(toCountry.getContinentName());
							int flag = 0;
							for (String country : cont.getCountries()) {
								Country c = map.getCountryFromName(country);
								if (!this.name.equals(c.getOwner())) {
									flag = 1;
									map.setContinentOwner(cont, "FREE CONTINENTS");
									break;
								}
							}
							if (flag == 0) { // continent has been conquered
								map.setContinentOwner(cont, this.name);
								System.out.println("You have conquered continent: " + cont.getName());
								setActions(this.name + " has conquered continent: " + cont.getName());
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

	/**
	 * Function to move the armies after conquering country
	 * @param command entered command
	 * @param fromCountry name of country from which armies are moved
	 * @param toCountry name of country to which armies should be moved
	 * @return 1 if armies are successfully moved otherwise 0.
	 */
	public int attackMove(String command, Country fromCountry, Country toCountry,int attackerDice) {
		String str[] = command.split(" ");

		if (str.length == 2 && str[0].equals("attackmove")) {
			int n;
			try {
			n = Integer.parseInt(str[1]);
			}catch(Exception e) {
				System.out.println("Invalid command");
				return 0;
			}
			if (n >= attackerDice && n <= fromCountry.getNoOfArmies() - 1) {
				fromCountry.setNoOfArmies(fromCountry.getNoOfArmies() - n);
				toCountry.setNoOfArmies(n + 1);
				this.setActions(
						"Moving :" + n + " armies from :" + fromCountry.getName() + " to " + toCountry.getName());
				return 1;
			} else {
				if (n < attackerDice || n > fromCountry.getNoOfArmies() - 1)
					System.out.println("Incorrect no of armies, Kindly type again.");
				return 0;
			}
		} else {
			System.out.println("Incorrect command, Kindly type again.");
			return 0;
		}
	}

	/**
	 * This method checks for attack deadlock
	 * @param map Object of Map
	 * @return 1 if deadlock occurred otherwise 0.
	 */
	public int attackDeadlock(Map map) {
		if (this.getNoOfArmies() == this.getAssigned_countries().size())
			return 1;
		else {
			for (Country c : this.getAssigned_countries()) {
				if (c.getNoOfArmies() != 1) {
					for (String s : c.getNeighbors()) {
						Country c1 = map.getCountryFromName(s);
						if (!(c1.getOwner().equals(this.name))) {
							return 0;
						}
					}
				}
			}
		}
		return 1;
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

	/**
	 * This is the method to check the attack command.
	 * 
	 * @return 1 if the command is valid otherwise 0.
	 */
	public int validate(String command, Map map) {
		String s[] = command.split(" ");
		int countryFound = 0;
		int neighborFound = 0;
		if (command.equals("showmap"))
			return 1;
		if (s.length == 2) {
			if (command.equals("attack -noattack")) {
				endOfActions = 1;
				setActions("Attack finished");
				return 1;
			}
		}
		if (s.length == 4) {
			if (s[0].equals("attack")) {
				for (Country c : this.getAssigned_countries()) {
					if (c.getName().equals(s[1])) {
						countryFound = 1;
						if (c.getNoOfArmies() > 1) {
							for (int i = 0; i < c.getNeighbors().size(); i++) {
								if (c.getNeighbors().get(i).equals(s[2])) {
									neighborFound = 1;
									Country c2 = map.getCountryFromName(s[2]);
									if (!this.getName().equals(c2.getOwner())) {
										if (s[3].equals("-allout"))
											return 1;
										else {
											int numdice=0;
											try {
											numdice = Integer.parseInt(s[3]);
											}catch(Exception e) {
												//System.out.println("Invalid command.");
												continue;
											}
											int noOfArmies = c.getNoOfArmies();
											if (numdice > 3) {
												System.out.println("Number of dices cannot be more than 3");
												return 0;
											}
											if (numdice > noOfArmies) {
												System.out.println(
														"Number of dices can be less than or equal to the no of armies in that country.");
												return 0;
											}
											if (numdice == 0) {
												System.out.println("Number of dices cannot be 0");
												return 0;
											}
											if (numdice < 0) {
												System.out.println("Incorrect number of dices");
												return 0;
											}
											return 1;
										}
									} else {
										System.out.println("Sorry!You cannot attack your own country.");
										return 0;
									}

								}
							}
						} else {
							System.out.println("You only have 1 army left in the FromCountry.Hence you cannot attack");
							return 0;
						}
						if (neighborFound == 0) {
							System.out.println("Sorry!To country is not an adjacent country of From country.");
							return 0;
						}

					}

				}
				if (countryFound == 0) {
					System.out.println("Sorry!From country is not assigned to you.");
					return 0;
				}
			} else
				return 0;
		}
		return 0;
	}
}
