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
	private Strategy strategy;
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

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
	public void executeReinforcement(Map map, ArrayList<Player> listPlayer) {
		this.strategy.reinforcement(map, listPlayer, this);
	}

	/**
	 * This function is used for fortification phase.
	 * 
	 * @param listPlayer  List of players
	 * @param map         Map object which contains the map details like continents
	 *                    and countries.
	 * @param command	For testing
	 */
	public void executeFortification(Map map, ArrayList<Player> listPlayer, String command) {
			this.strategy.fortification(map, listPlayer, command, this);

	}

	/**
	 * This method is used for attack phase.
	 * @param map Object of Map
	 * @param listPlayer list of Players
	 * @return 1 if attack is successful otherwise 0.
	 * @throws Exception
	 */
	public int executeAttack(Map map, ArrayList<Player> listPlayer) throws Exception {
		int result=this.strategy.attack(map, listPlayer, this);
		return result;
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
				toCountry.setNoOfArmies(n);
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
											if (numdice >= noOfArmies) {
												System.out.println(
														"Number of dices should be less than the no of armies in that country.");
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
