package Strategy;

import java.util.ArrayList;
import java.util.Random;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import dao.Strategy;

public class RandomStrategy implements Strategy {
	

	Random random = new Random();

	@Override
	public void reinforcement(Map map, ArrayList<Player> listPlayer, Player P) {
		// TODO Auto-generated method stub

		P.setEndOfActions(0);
		P.setView("PhaseView");
		P.setState("Reinforcement");
		// calculate reinforcement armies
		int reinforcementArmies = calculateReinforceArmies(map, P);

		// loop over playerlist and assign reinforcement armies
		System.out.println("Reinforcement armies to be assigned :" + reinforcementArmies);
		System.out.println("Type reinforce <countryname> <num>  to assign armies ");

		map.setNoOfArmies(P, P.getNoOfArmies() + reinforcementArmies);
		while (reinforcementArmies != 0) {

			String input = getRandomReinforceInput(P, reinforcementArmies);
			String[] inputArray = input.split(" ");

			if (inputArray.length == 3 && inputArray[0].equals("reinforce")) {
				int armiesTobeplaced = 0;
				try {
					armiesTobeplaced = Integer.parseInt(inputArray[2]);
				} catch (Exception e) {
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

					} else
						System.out.println(
								"Number of armies to be assigned should be in the range : 1 -" + reinforcementArmies);
				}
			}

			else
				System.out.println("Invalid command .Type again");
		}
		P.setEndOfActions(1);
		P.setActions("Reinforcement finished");

	}

	private String getRandomReinforceInput(Player p, int reinforcementArmies) {
		// TODO Auto-generated method stub

		String input = "reinforce ";
		int randomCountryIndex = random.nextInt(p.getAssigned_countries().size());
		input += p.getAssigned_countries().get(randomCountryIndex).getName() + " ";
		int randomArmies = random.nextInt(reinforcementArmies) + 1;

		input += randomArmies;
		return input;

	}

	@Override
	public int attack(Map map, ArrayList<Player> listPlayer, Player P) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fortification(Map map, ArrayList<Player> listPlayer, String command, Player P) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method calculates the number of reinforcement armies
	 * 
	 * 
	 * @param map
	 *            Map Object
	 * @param P
	 *            Player
	 * @return Number of reinforcement armies
	 */
	public int calculateReinforceArmies(Map map, Player P) {
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

}
