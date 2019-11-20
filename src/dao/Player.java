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

	

	
}
