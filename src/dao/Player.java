package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pattern.Strategy.Strategy;
import pattern.observer.Observable;

/**
 * This class represents the Player details
 * @author Piyush
 *
 */
public class Player extends Observable {
	private Strategy strategy;

	/**
	 * This method returns the strategy
	 *
	 * @return Strategy
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	/**
	 * This method sets the strategy
	 * 
	 * @param strategy Strategy to be set
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public int test = 0;
	private String testCommand;

	/**
	 * This method returns the test command
	 * 
	 * @return Test Command
	 */
	public String getTestCommand() {
		return testCommand;
	}

	/**
	 * This method is used to set the test command
	 * 
	 * @param testCommand Test Command to be set
	 */
	public void setTestCommand(String testCommand) {
		test = 1;
		this.testCommand = testCommand;
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
	 * To store current state - Reinforcement ,Attack, Fortify
	 */
	private String state;

	/**
	 * Deck of cards
	 */
	public static ArrayList<String> deck = new ArrayList<String>();
	/**
	 * To store current actions
	 */
	private String actions;

	/**
	 * The name of the player
	 */
	private String name;

	/**
	 * This holds the count of no of times the player has exchanged cards to get
	 * armies.
	 */
	public static int cardExchangeCounter;

	/**
	 * ArrayList of cards
	 */
	private ArrayList<String> cards;

	/**
	 * Number of armies assigned to the player
	 */
	private int noOfArmies = 0;
	/**
	 * Number of unassigned armies
	 */
	private int unassignedarmies = 0;
	/**
	 * List of assigned countries
	 */
	private List<Country> assigned_countries;

	/**
	 * Constructor initializes the list of assigned countries
	 */
	public Player() {
		assigned_countries = new ArrayList<Country>();
		cards = new ArrayList<String>();
	}

	/**
	 * This method is used to get a random card
	 * 
	 * @return Random Card
	 */
	public String randomCard() {
		if (deck.size() == 0)
			return "None";
		Random number = new Random();
		int no = number.nextInt(deck.size());
		return deck.get(no);
	}

	/**
	 * This method returns the number of unassigned armies
	 * 
	 * @return Number of Unassigned Armies
	 */
	public int getUnassignedarmies() {
		return unassignedarmies;
	}

	/**
	 * This method sets the number of unassigned armies
	 * 
	 * @param unassignedarmies Number of unassigned armies
	 */

	public void setUnassignedarmies(int unassignedarmies) {
		this.unassignedarmies = unassignedarmies;
	}

	/**
	 * This method returns the name of the player.
	 * 
	 * @return Player Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the player.
	 * 
	 * @param name Player Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method returns the number of armies for a player.
	 * 
	 * @return Number of armies for a player
	 */
	public int getNoOfArmies() {
		return noOfArmies;
	}

	/**
	 * This method sets the number of armies for a player.
	 * 
	 * @param noOfArmies Number of armies of a particular player
	 */
	public void setNoOfArmies(int noOfArmies) {
		if (noOfArmies >= 0) {
			this.noOfArmies = noOfArmies;
		}
	}

	/**
	 * This method returns the list of countries belonging to a player.
	 * 
	 * @return List of countries belonging to a player
	 */
	public List<Country> getAssigned_countries() {
		return assigned_countries;
	}

	/**
	 * This method returns the current view
	 * 
	 * @return Current View
	 */
	public String getView() {
		return view;
	}

	/**
	 * This method sets the current view
	 * 
	 * @param view View to be set
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
	 * This method is used to get the end of actions
	 * 
	 * @return endOfActions
	 */
	public int getEndOfActions() {
		return endOfActions;
	}

	/**
	 * This method is used to set the end of actions
	 * 
	 * @param endOfActions End of Actions
	 */
	public void setEndOfActions(int endOfActions) {
		this.endOfActions = endOfActions;

	}

	/**
	 * This method is used to get the current actions
	 * 
	 * @return Actions
	 */
	public String getActions() {
		return actions;
	}

	/**
	 * This method is used to store the current actions
	 * 
	 * @param actions Actions to be stored
	 */
	public void setActions(String actions) {
		this.actions = actions;
		notify(this);
		this.actions = "";
	}

	/**
	 * This method to used to get the current state
	 * 
	 * @return Current State
	 */
	public String getState() {
		return state;
	}

	/**
	 * This method is used to set the current state
	 * 
	 * @param state State to be set
	 */
	public void setState(String state) {
		this.state = state;
		notify(this);
	}

	/**
	 * This method returns the cardExchangeCounter.
	 * 
	 * @return Card Exchange Counter
	 */
	public int getCardExchangeCounter() {
		return cardExchangeCounter;
	}

	/**
	 * This method sets the cardExchangeCounter.
	 * 
	 * @param cardExchangeCounter Counter to be set
	 */
	public void setCardExchangeCounter(int cardExchangeCounter) {
		this.cardExchangeCounter = cardExchangeCounter;
	}

	/**
	 * This method returns the card list.
	 * 
	 * @return List of Cards
	 */
	public ArrayList<String> getCards() {
		return cards;
	}

	/**
	 * This method sets the card list.
	 * 
	 * @param cards Card list to be set
	 */
	public void setCards(ArrayList<String> cards) {
		this.cards = cards;
	}

	/**
	 * This method is used for reinforcement phase and exchange of cards.
	 * 
	 * @param listPlayer List of players
	 * @param map        Map object which contains the map details like continents
	 *                   and countries.
	 * 
	 */
	public void executeReinforcement(Map map, ArrayList<Player> listPlayer) {
		this.strategy.reinforcement(map, listPlayer, this);
	}

	/**
	 * This method is used for fortification phase.
	 * 
	 * @param listPlayer List of players
	 * @param map        Map object which contains the map details like continents
	 *                   and countries.
	 * @param command    For testing
	 */
	public void executeFortification(Map map, ArrayList<Player> listPlayer, String command) {
		this.strategy.fortification(map, listPlayer, command, this);

	}

	/**
	 * This method is used for attack phase.
	 * 
	 * @param map        Object of Map
	 * @param listPlayer list of Players
	 * @return 1 if attack is successful otherwise 0.
	 * @throws Exception
	 */
	public int executeAttack(Map map, ArrayList<Player> listPlayer) throws Exception {
		int result = this.strategy.attack(map, listPlayer, this);
		return result;
	}

	/**
	 * This method checks if card exchange is possible or not.
	 *
	 * @return true if exchange is possible otherwise false;
	 */
	public boolean cardExchangePossible() {

		int infantryNum = (int) cards.stream().filter(card -> card.contains("infantry")).count();
		int cavalryNum = (int) cards.stream().filter(card -> card.contains("cavalry")).count();
		int artilleryNum = (int) cards.stream().filter(card -> card.contains("artillery")).count();
		if (infantryNum >= 3) {
			return true;
		} else if (cavalryNum >= 3) {
			return true;
		} else if (artilleryNum >= 3) {
			return true;
		} else if (infantryNum >= 1 && cavalryNum >= 1 && artilleryNum >= 1) {
			return true;
		}
		return false;
	}
}
