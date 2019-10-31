package dao;

import java.util.*;
import dao.Map;
import game.PlayerAllocator;
import mapWorks.MapReader;

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
		cards= new ArrayList<String>();
	}
	/**
	 * 
	 */
	public String randomCard() {
		String card;
		Random number = new Random();
		int no=number.nextInt(3) + 1;
		if(no==1)
			return "Infantry";
		else if(no==2)
			return "Artillery";
		else
			return "Cavalry";
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
	 * This method calculates the number of reinforcement armies
	 * 
	 * @param listPlayer  List of Players
	 * @param map         Map Object
	 * @param playerIndex Index of the player in the list
	 * @return Number of reinforcement armies
	 */
	public int calculateReinforceArmies(Map map,ArrayList<Player> listPlayer) {
		int noOfArmies =this.getAssigned_countries().size() / 3;
		int reinforcementArmies = noOfArmies <= 3 ? 3 : noOfArmies;
		return reinforcementArmies;
	}

	/**
	 * This method is used for reinforcement phase.
	 * 
	 * @param listPlayer  List of players
	 * @param map         Map object which contains the map details like continents
	 *                    and countries.
	 * @param playerIndex Index of a particular player in the List of player passed
	 *                    from main function
	 */
	public void reinforcement(Map map,ArrayList<Player> listPlayer) {

		Scanner sc = new Scanner(System.in);
		// calculate reinforcement armies
		int reinforcementArmies = calculateReinforceArmies(map,listPlayer);
		int forceExchangeCards  = 0;
		
		if(this.getCards().size() >= 5){
			forceExchangeCards =1;	
		}
		else
			forceExchangeCards =0;
		// loop over playerlist and assign reinforcement armies
		System.out.println("Reinforcement armies to be assigned :" + reinforcementArmies);
		System.out.println("Type reinforce <countryname> <num>  to assign armies\n Type showmap");
		System.out.println("Type exchangecards <num> <num> <num> -none to exchange cards\n Type showmap");
		
		while (reinforcementArmies != 0 || forceExchangeCards == 1) {
			String input = sc.nextLine();
			String[] inputArray = input.split(" ");
			
			if(this.getCards().size() >= 5){
				forceExchangeCards =1;	
			}
			else
				forceExchangeCards =0;
			
			if (input.equals("showmap")) {
				MapReader mr = new MapReader();
				
				map.displayAll();
			} else if (inputArray.length == 3 && inputArray[0].equals("reinforce")) {
				int armiesTobeplaced = Integer.parseInt(inputArray[2]);
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

						this.setNoOfArmies(this.getNoOfArmies() + armiesTobeplaced);
						map.getCountryFromName(inputArray[1]).setNoOfArmies(
								map.getCountryFromName(inputArray[1]).getNoOfArmies() + armiesTobeplaced);
						reinforcementArmies -= armiesTobeplaced;
						System.out.println("Reinforcement armies placed successfully");
						System.out.println("Remaining armies to be placed : "+reinforcementArmies);
						if(forceExchangeCards == 1 && reinforcementArmies == 0)
							System.out.println("You have more than 5 exchange cards ,please exchange cards to proceed to attack phase");
						
					} else
						System.out.println(
								"Number of armies to be assigned should be in the range : 1 -" + reinforcementArmies);
				}
			}
		 else if(inputArray.length == 4 && inputArray[0].equals("exchangecards")){
			 System.out.println(this.getCards());
			 
			 if(this.getCards().size() <3) {
				 System.out.println("You don't have enough cards to exchange");
				 continue;
			 }
			 
			//check if three numbers are valid
			int num1;
			int num2;
			int num3;
			boolean IsExchangeCards =false;
			try{
				num1 = Integer.parseInt(inputArray[1]) - 1;
				num2 = Integer.parseInt(inputArray[2]) - 1;
				num3 = Integer.parseInt(inputArray[3]) - 1;
			System.out.println(num1 +""+num2 +""+num3);
			//check if three numbers are valid cards indexes in player's hand
				ArrayList<String> playerCards = this.getCards();
				String card1 = playerCards.get(num1);
				String card2 = playerCards.get(num2);
				String card3 = playerCards.get(num3);
				if(!card1.isEmpty() && !card2.isEmpty()&& !card3.isEmpty() ){
					//numbers are valid ,check if all same or all different
					String concatCards =card1+card2+card3;
					if(concatCards.equalsIgnoreCase("infantryinfantryinfantry") || concatCards.equalsIgnoreCase("cavalrycavalrycavalry") || concatCards.equalsIgnoreCase("artilleryartilleryartillery")){
						//all same
						IsExchangeCards = true;
					}else{
						Set<String> diffChecker = new HashSet<String>();
						diffChecker.add(card1);
						diffChecker.add(card2);
						diffChecker.add(card3);
						if(diffChecker.size() == 3){
							//all different
							IsExchangeCards = true;
						}else{
							System.out.println("Exchange not possible");
							continue;
						}
					}
				}
				
					
				if(IsExchangeCards){
					playerCards.remove(card1);
					playerCards.remove(card2);
					playerCards.remove(card3);
					
					this.setCardExchangeCounter(this.getCardExchangeCounter() + 5);
					reinforcementArmies += this.getCardExchangeCounter();
					System.out.println("Reinforcement armies added "+this.getCardExchangeCounter());
					System.out.println("Remaining armies to be placed : "+reinforcementArmies);
					
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid Command. 'num' should be a number.");
				continue;
			}catch (NullPointerException e) {
				System.out.println("Invalid Command. 'num' should be a number.");	
				continue;
			}
			catch(IndexOutOfBoundsException e){
				e.printStackTrace();
				System.out.println("Invalid card number.");
				continue;
			}
			
			
		} else if(inputArray.length == 2 && inputArray[0].equals("exchangecards") && inputArray[1].equals("-none")){
			//Player chooses not to exchange cards
			//check if cards is more than 5
			if(forceExchangeCards == 1)
				System.out.println("Exchange cards cannot be skipped as no. of cards is more than 5");
			else if(forceExchangeCards == 0)
				System.out.println("Exchange cards skipped");
			
		}
			
			else
				System.out.println("Invalid command .Type again");
		}
		
	}

	/**
	 * This function is used for fortification phase.
	 * 
	 * @param listPlayer  List of players
	 * @param map         Map object which contains the map details like continents
	 *                    and countries.
	 * @param playerIndex Index of a particular player in the List of player passed
	 *                    from main function
	 */
	public void fortification(Map map,ArrayList<Player> listPlayer) {
		Scanner sc = new Scanner(System.in);
		int flag = 0;
		do {

			System.out.println(
					"Type fortify <from country name> <to country name> <number of armies> or fortify none (choose to not do a move)\n Type showmap");
			String in = sc.nextLine();
			String input[] = in.split(" ");
			if (in.equals("showmap")) {
				MapReader mr = new MapReader();
				map.displayAll();
			} else if (input.length == 4 && input[0].equals("fortify")) {
				HashMap<Integer, List<Integer>> mapOfAssignedCountries = new HashMap<Integer, List<Integer>>();
				for (int i = 0; i < this.getAssigned_countries().size(); i++) {
					List<Integer> templist = new ArrayList<Integer>();
					for (int j = 0; j <this.getAssigned_countries().get(i).getNeighbors()
							.size(); j++) {
						if (map.getCountryFromName(
								this.getAssigned_countries().get(i).getNeighbors().get(j))
								.getOwner().equals(this.getName())) {
							for (int k = 0; k < this.getAssigned_countries().size(); k++) {
								if (this.getAssigned_countries().get(k)
										.equals(map.getCountryFromName(this.getAssigned_countries().get(i).getNeighbors().get(j)))) {
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
					if (source == -1 && destination == -1)
						System.out.println("Sorry!From Country:" + input[1] + " and To Country :" + input[2]
								+ " doesn't belong to you");
					else if (source == -1)
						System.out.println("Sorry!From Country :" + input[1] + " doesn't belong to you");
					else
						System.out.println("Sorry!To Country :" + input[2] + " doesn't belong to you");
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
						if (Integer.parseInt(input[3]) > 0 && (Integer.parseInt(input[3]) < this.getAssigned_countries().get(source).getNoOfArmies())) {
							this.getAssigned_countries().get(source).setNoOfArmies(
									(this.getAssigned_countries().get(source).getNoOfArmies())
											- Integer.parseInt(input[3]));
							this.getAssigned_countries().get(destination)
									.setNoOfArmies(this.getAssigned_countries().get(destination)
											.getNoOfArmies() + Integer.parseInt(input[3]));
							System.out.println("Fortification successful");
							
							return;
						} else
							System.out.println("Invalid no of armies specified, for these two countries it can be 1-"
									+ (this.getAssigned_countries().get(source).getNoOfArmies()
											- 1));
					} else {
						System.out.println(
								"There's no path between the mentioned countries.(You can move any number of armies from one of the owned countries to the other, provided that there is a path between these two countries that is composed of countries owned by you)");
					}
				}
			} else if (in.equals("fortify none")) {
				System.out.println("Skipped fortification");
				
				return;
			} else
				System.out.println("Invalid command,type again");
		} while (flag == 0);

		
	}

	/**
	 * This method is used for attack phase.
	 * @throws Exception 
	 */
	public int attack(Map map,ArrayList<Player> listPlayer) throws Exception {
			Scanner sc = new Scanner(System.in);
			int attackDeadlock=0;
			System.out.println("Type attack <countrynamefrom> <countynameto> <numdice> for a single attack");
			System.out.println("attack <countrynamefrom> <countynameto> -allout for an attack until no attack is possible");
			System.out.println("attack –noattack to end attack phase");
			String input;
			do {
			input=sc.nextLine();
			
			while(validate(input,map)==0) {
				System.out.println("Kindly type again");
				input=sc.nextLine();
			}
			attackDeadlock=0;
			String s[]=input.split(" ");
			if(!input.equals("attack -noattack")) {
			Country fromCountry=map.getCountryFromName(s[1]);
			System.out.println("Valid command");
			int attackerDice=0;
			
			int defenderDice = 0;
			Country toCountry=map.getCountryFromName(s[2]);
			int validCommand=0;
			String defend=toCountry.getOwner();
			int index=-1;
			for(int i=0;i<listPlayer.size();i++) {
				
				if(listPlayer.get(i).getName().equals(toCountry.getOwner())) {
					index=i;
					break;
				}
			}
			Player defender=listPlayer.get(index);
			int isAllout=0;
			if(s[3].equals("attack -allout")) {
				while(toCountry.getNoOfArmies()!=0 && fromCountry.getNoOfArmies()!=1) {
					if(fromCountry.getNoOfArmies()>=3)
						attackerDice=3;
					else
						attackerDice=2;
					if(toCountry.getNoOfArmies()>=2)
						defenderDice=2;
					else
						defenderDice=1;
					Dice diceRoll=new Dice(attackerDice, defenderDice);
					int result[][]=diceRoll.rollAll();
					result=diceRoll.sort(result);
					int min=Math.min(attackerDice, defenderDice);
					for(int i=0;i<min;i++) {
						if(result[0][i]>result[1][i])//attacker wins
						{
							defender.setNoOfArmies(defender.getNoOfArmies()-1);
							toCountry.setNoOfArmies(toCountry.getNoOfArmies()-1);
						}
						else {  		//defender wins
							this.noOfArmies=this.noOfArmies-1;
							fromCountry.setNoOfArmies(fromCountry.getNoOfArmies()-1);
						}
						
					}
				}
				isAllout=1;
			}
			else {
				//int gameOver=0;
				attackerDice=Integer.parseInt(s[3]);
				System.out.println("Player :"+defend+" has to defend country :"+s[2]+" \nType defend numdice to choose no of dices to defend your country.");
				
				while(validCommand==0) {
					input=sc.nextLine();
					String str[]=input.split(" ");
					if(str.length == 2 && str[0].equals("defend")) {
						int dice=Integer.parseInt(str[1]);
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
					Dice diceRoll=new Dice(attackerDice, defenderDice);
					int result[][]=diceRoll.rollAll();
					result=diceRoll.sort(result);
					int min=Math.min(attackerDice, defenderDice);
					for(int i=0;i<min;i++) {
						if(result[0][i]>result[1][i])//attacker wins
						{
							defender.setNoOfArmies(defender.getNoOfArmies()-1);
							toCountry.setNoOfArmies(toCountry.getNoOfArmies()-1);
						}
						else {  		//defender wins
							this.noOfArmies=this.noOfArmies-1;
							fromCountry.setNoOfArmies(fromCountry.getNoOfArmies()-1);
						}
					}
				}
			}
			if(validCommand==1 || isAllout==1) {
				if(toCountry.getNoOfArmies()==0) { //attacker has conquered the defending country.
						Scanner in=new Scanner(System.in);
						toCountry.setOwner(this.name);
						this.getAssigned_countries().add(toCountry);
						defender.getAssigned_countries().remove(toCountry);
						toCountry.setNoOfArmies(1);
						fromCountry.setNoOfArmies(fromCountry.getNoOfArmies()-1);
						System.out.println("You have conquered country:"+toCountry.getName());
						System.out.println("Move armies from "+fromCountry.getName()+" to"+toCountry.getName());
						System.out.println("Available armies you can move : 1-"+(fromCountry.getNoOfArmies()-1));
						System.out.println("Type attackmove <number> to move");
						int n,valid=0;
						do {	
							String command=sc.nextLine();
							String str[]=command.split(" ");
							if(str.length==2 && str[0].equals("attackmove")) {
								n=Integer.parseInt(str[1]);
								if(n>0 && n<=fromCountry.getNoOfArmies()-1) {
									fromCountry.setNoOfArmies(fromCountry.getNoOfArmies()-n);
									toCountry.setNoOfArmies(n+1);
									valid=1;
									break;
								}
								else {
									if(n==0 || n>fromCountry.getNoOfArmies()-1)
										System.out.println("Incorrect no of armies, Kindly type again.");
								}
							}
							else {
									System.out.println("Incorrect command, Kindly type again.");
							}
						}while(valid==0);
						//card exchange logic
						if(defender.getAssigned_countries().size()==0) {//defender is out of the game
							for(int i=0;i<defender.getCards().size();i++) {
								this.getCards().add(defender.getCards().get(i));
							}
							listPlayer.remove(defender);
							if(listPlayer.size()==1) {	//checking for game finish condition
								return 1;
							}
						}
						else {
						String card=this.randomCard();
						this.cards.add(card);
						}
						//checking for continent 
						Continent cont=map.getContinentFromName(toCountry.getContinentName());
						int flag=0;
						for(String country:cont.getCountries()) {
							Country c=map.getCountryFromName(country);
							if(this.name.equals(c.getOwner())) {
								flag=1;
								break;
							}
						}
						if(flag==0) { //continent has been conquered
							cont.setOwner(this.name);
							cont.setAssignArmy(1);
						}
					}
				}
			}
			//checking for deadlock
			attackDeadlock= attackDeadlock(map);
			}while(!input.equals("attack -noattack") && attackDeadlock==0);
			return 0;
	}
	/**
	 * This method checks for attack deadlock
	 */
	public int attackDeadlock(Map map) {
		if(this.getNoOfArmies()==this.getAssigned_countries().size())
			return 1;
		else {
			for(Country c:this.getAssigned_countries()) {
				if(c.getNoOfArmies()!=1) {
					for(String s:c.getNeighbors()) {
						Country c1=map.getCountryFromName(s);
						if(!(c1.getOwner().equals(this.name))) {
							return 0;
						}
					}
				}
			}
		}
		return 1;
	}
	/**
	 * This method is for a single attack
	 */
	public void singleAttack() {
		
	}
	/**
	 * This is the method to check the attack command.
	 * @return 1 if the command is valid otherwise 0.
	 */
	public int validate(String command,Map map) {
		String s[]=command.split(" ");
		int countryFound=0;
		int neighborFound=0;
		if(s.length==2) {
			if(command.equals("attack -noattack"))
				return 1;
		}
		if(s.length==4) {
			if(s[0].equals("attack")) {
				String countryFrom=s[1];
				String countryTo=s[2];
				for(Country c:this.getAssigned_countries()) {
					if(c.getName().equals(s[1])) {
						countryFound=1;
						if(c.getNoOfArmies()>1) {
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
										return 1;
									}
								}
								else {
									System.out.println("Sorry!You cannot attack your own country.");
									return 0;
								}
								
							}
						}
						}
						else {
							System.out.println("You only have 1 army left in the FromCountry.Hence you cannot attack");
							return 0;
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
	/**
	 * 
	 */
		public Player getPlayerFromName(String name,ArrayList<Player> listPlayer) {
			for(int i=0;i<listPlayer.size();i++) {
				if(listPlayer.get(i).getName().equals(name)) {
				return listPlayer.get(i);
				}
			}
			return null;
		}
}

