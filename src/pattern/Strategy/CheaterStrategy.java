package pattern.Strategy;

import java.util.ArrayList;
import java.util.HashSet;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;

public class CheaterStrategy implements Strategy {

	@Override
	public void reinforcement(Map map, ArrayList<Player> listPlayer, Player P) {
		// TODO Auto-generated method stub

		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Reinforcement");

		// calculate reinforcement armies
		int reinforcementArmies = P.getNoOfArmies();

		// loop over playerlist and assign reinforcement armies
		System.out.println("Reinforcement armies to be assigned :" + reinforcementArmies);
		map.setNoOfArmies(P, P.getNoOfArmies() + reinforcementArmies);

		for (Country country : P.getAssigned_countries()) {

			int armiesTobeplaced = country.getNoOfArmies();

			if (armiesTobeplaced <= reinforcementArmies && armiesTobeplaced > 0) { // check reinforce command
				// and country is valid and
				// assigned to player
				System.out.println(country.getNoOfArmies() + " to ");
				country.setNoOfArmies(country.getNoOfArmies() + armiesTobeplaced);
				System.out.println(country.getNoOfArmies());
				reinforcementArmies -= armiesTobeplaced;
				System.out.println("Reinforcement armies placed successfully");
				System.out.println("Remaining armies to be placed : " + reinforcementArmies);
				P.setView("PhaseView");
				P.setActions("Reinforced " + armiesTobeplaced + " armies to " + country.getName());

			} else
				System.out
						.println("Number of armies to be assigned should be in the range : 1 -" + reinforcementArmies);

		}
		P.setEndOfActions(1);
		P.setActions("Reinforcement finished");
		

	}

	@Override
	public int attack(Map map, ArrayList<Player> listPlayer, Player P) {
		// TODO Auto-generated method stub

		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Attack");

		// get all neighbors
		HashSet<String> allNeighbors = new HashSet<String>();
		for (Country country : P.getAssigned_countries()) {
			for (String neighbor : country.getNeighbors()) {
				if (!map.getCountryFromName(neighbor).getOwner().equals(P.getName())) {
					// if neighbor country belongs to other player
					allNeighbors.add(neighbor);
				}
			}
		}

		for (String neighbor : allNeighbors) {

			Country toCountry = map.getCountryFromName(neighbor);

			// String defend = toCountry.getOwner();
			int index = -1;

			for (int i = 0; i < listPlayer.size(); i++) {

				if (listPlayer.get(i).getName().equals(toCountry.getOwner())) {
					index = i;
					break;
				}
			}

			P.setActions("Attacking on: " + toCountry.getName());

			Player defender = listPlayer.get(index);

			map.setNoOfArmies(P, P.getNoOfArmies() + toCountry.getNoOfArmies());
			map.setNoOfArmies(defender, defender.getNoOfArmies() - toCountry.getNoOfArmies());

			map.setCountryOwner(toCountry, P.getName());

			P.getAssigned_countries().add(toCountry);
			defender.getAssigned_countries().remove(toCountry);

			System.out.println("You have conquered country: " + toCountry.getName());
			P.setActions(P.getName() + " has conquered country: " + toCountry.getName());

			if (defender.getAssigned_countries().size() == 0) {// defender is out of the game
				for (int i = 0; i < defender.getCards().size(); i++) {
					P.getCards().add(defender.getCards().get(i));
				}
				listPlayer.remove(defender);
				// checking for game finish condition
				if (endGame(listPlayer) == 1)
					return 1;

			} else {
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
				P.setActions(P.getName() + " has conquered continent: " + cont.getName());
			}

		}
		P.setEndOfActions(1);
		P.setActions("Attack finished");
		
		return 0;

	}



	@Override
	public void fortification(Map map, ArrayList<Player> listPlayer, String command, Player P) {
		// TODO Auto-generated method stub

		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Fortification");

		// get all boundary countries
		HashSet<String> allBoundaries = new HashSet<String>();
		for (Country country : P.getAssigned_countries()) {
			for (String neighbor : country.getNeighbors()) {
				if (!map.getCountryFromName(neighbor).getOwner().equals(P.getName())) {
					// if neighbor country belongs to other player
					allBoundaries.add(country.getName());

				}
			}
		}

		// double armies of all boundary countries
		for (String boundaryCountry : allBoundaries) {
			Country country = map.getCountryFromName(boundaryCountry);
			int newArmy = country.getNoOfArmies();
			map.setNoOfArmies(P, P.getNoOfArmies() + newArmy);
			country.setNoOfArmies(2 * newArmy);
			
			P.setActions("Fortified " + country.getName() + " with " + newArmy + " armies");
		}

		System.out.println("Fortification successful");
		P.setEndOfActions(1);
		P.setActions("Fortification finished");
		
	}

	/**
	 * Function to check the end of game
	 * 
	 * @param listPlayer list of players
	 * @return 1 if end of game otherwise 0.
	 */
	public int endGame(ArrayList<Player> listPlayer) {
		if (listPlayer.size() == 1)
			return 1;
		return 0;
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
	public int attackDeadlock(Map testMap, Player P) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int calculateReinforceArmies(Map testMap, Player P) {
		// TODO Auto-generated method stub
		return 0;
	}



}
