package dao;

import java.util.*;
import dao.Map;

/**
 * This class represents the Player details
 * 
 * @author Piyush
 *
 */


public class Player {
	/**
	 * The name of the player
	 */
	private String name;
	/**
	 * List of cards for a player
	 */
	/**
	 * This holds the count of no of times the player has exchanged cards to get armies.
	 */
	private int cardExchangeCounter;
	/**
	 * This method returns the cardExchangeCounter.
	 * @return
	 */
	public int getCardExchangeCounter() {
		return cardExchangeCounter;
	}
	/**
	 * This method sets the cardExchangeCounter.
	 * @param cardExchangeCounter
	 */
	public void setCardExchangeCounter(int cardExchangeCounter) {
		this.cardExchangeCounter = cardExchangeCounter;
	}
	private ArrayList<String> cards;
	/**
	 * This method returns the card list.
	 * @return
	 */
	public ArrayList<String> getCards() {
		return cards;
	}
	/**
	 *  This method sets the card list.
	 * @param cards
	 */
	public void setCards(ArrayList<String> cards) {
		this.cards = cards;
	}
	/**
	 * Number of armies assigned ot the player
	 */
	private int noOfArmies;
	/**
	 * Number of unassigned armies
	 */
	private int unassignedarmies;
	/**
	 * list of assigned countries
	 */
	private List<Country> assigned_countries;

	/**
	 * Constructor initializes the list of assigned countries
	 */
	public Player() {
		assigned_countries = new ArrayList<Country>();
		ArrayList<String> cards= new ArrayList<String>();
	}

	/**
	 * This method returns the number of unassigned armies.
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
		this.noOfArmies = noOfArmies;
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
	 * This method sets the list of countries belonging to a player.
	 * 
	 * @param assigned_countries Countries assigned to a player
	 */
	public void setAssigned_countries(List<Country> assigned_countries) {
		this.assigned_countries = assigned_countries;
	}
	/**
	 * This method is used for attack phase.
	 */
	public void attack(Map map,ArrayList<Player> listPlayer) {
			Scanner sc = new Scanner(System.in);
			int attackDeadlock=0;
			System.out.println("Type attack <countrynamefrom> <countynameto> <numdice> for a single attack");
			System.out.println("attack <countrynamefrom> <countynameto> -allout for an attack until no attack is possible");
			System.out.println("–noattack to end attack phase");
			String input=sc.nextLine();
			String s[]=input.split(" ");
			while(validate(input,map)==0) {
				System.out.println("Kindly type again");
			}
			Country countryFrom=map.getCountryFromName(s[1]);
			System.out.println("Valid command");
			int attackerDice=Integer.parseInt(s[3]);
			int defenderDice;
			while(!input.equals("-noattack") && attackDeadlock==0) {
				Country toCountry=map.getCountryFromName(s[2]);
				int validCommand=0;
				String defend=toCountry.getOwner();
				Player defender=new Player();
				
				System.out.println("Player :"+defend+" has to defend country :"+s[2]+" \nType defend numdice to choose no of dices to defend your country.");
				input=sc.nextLine();
				String str[]=input.split(" ");
				while(validCommand==0) {
					if(str.length == 2 && str[0].equals("defend")) {
						int dice=Integer.parseInt(s[1]);
						int noOfArmies=toCountry.getNoOfArmies();
						if(dice >0 && dice <3 && dice<=noOfArmies) {
							defenderDice=dice;
							validCommand=1;
						}
						else
							System.out.println("Dice range for a defender is 1-2");
					}
					else {
						System.out.println("Invalid command,type again.");
					}
				}
				if(validCommand==1) {
					
				}
			}
	}
	/**
	 * This is the method to check the attack command.
	 * @return 1 if the command is valid otherwise 0.
	 */
	public int validate(String command,Map map) {
		String s[]=command.split(" ");
		int countryFound=0;
		int neighborFound=0;
		if(s.length==1) {
			if(s[0].equals("-noattack"))
				return 1;
		}
		if(s.length==4) {
			if(s[0].equals("attack")) {
				String countryFrom=s[1];
				String countryTo=s[2];
				for(Country c:getAssigned_countries()) {
					if(c.getName().equals(s[1])) {
						countryFound=1;
						for(int i=0;i<c.getNeighbors().size();i++) {
							if(c.getNeighbors().get(i).equals(s[2])) {
								neighborFound=1;
								Country c2=map.getCountryFromName(s[2]);
								if(!this.getName().equals(c2.getOwner())) {
									if(s[3].equals("-allout"))
										return 1;
									else {
										int numdice=Integer.parseInt(s[3]);
										int noOfArmies=c.getNoOfArmies();
										if(numdice >3) {
											System.out.println("Number of dices cannot be more than 3");
											return 0;
										}
										if(numdice> noOfArmies) {
											System.out.println("Number of dices can be less than or equal to the no of armies in that country.");
											return 0;
										}
										if(numdice == 0) {
											System.out.println("Number of dices cannot be 0");
											return 0;
										}
										
									}
								}
								else {
									System.out.println("Sorry!You cannot attack your own country.");
									return 0;
								}
								
							}
						}
						if(neighborFound==0) {
							System.out.println("Sorry!To country is not an adjacent country of From country.");
							return 0;
						}
						
					}
					
				}
				if(countryFound==0) {
					System.out.println("Sorry!From country is not assigned to you.");
					return 0;
				}
			}
			else 
				return 0;		
		}
		return 0;
	}
}
