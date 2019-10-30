package dao;

import java.util.Arrays;
import java.util.Random;

/**
 * This class represents a dice used in attack phase.
 * 
 * @author ekjot
 *
 */
public class Dice {

	// Random object to get random number.
	Random number = new Random();
	private int attackerDice = 0;
	private int defenderDice = 0;

	/**
	 * Constructor sets attackerDice and defenderDice
	 * 
	 * @param attackerDice
	 * @param defenderDice
	 * @throws Exception
	 */
	Dice(int attackerDice, int defenderDice) throws Exception {
		setAttackerDice(attackerDice);
		setDefenderDice(defenderDice);
	}

	/**
	 * This method returns a random number between 1 and 6
	 * 
	 * @return rolledNumber Rolled number from dice.
	 */
	int roll() {
		int rolledNumber = number.nextInt(6) + 1;
		return rolledNumber;
	}

	/**
	 * This method returns a 2D array of 2*3, of 3 rolledNumbers for both attacker
	 * and defender. 3rd rolledNumber for defender will always be 0.
	 * 
	 * @return result 2D array of rolled numbers
	 */
	int[][] rollAll() {

		int[][] result = new int[2][3];

		for (int i = 0; i < attackerDice; i++) {
			result[0][i] = roll();

		}

		for (int i = 0; i < defenderDice; i++) {
			result[1][i] = roll();

		}

		return result;
	}

	/**
	 * This method sort the given 2D array of dimension 2*3 in descending order.
	 * 
	 * @param arr Unsorted 2D array of 2*3.
	 * @return result Sorted 2D array of 2*3.
	 */
	static int[][] sort(int[][] arr) {

		Arrays.sort(arr[0]);
		Arrays.sort(arr[1]);

		int[][] result = new int[2][3];
		for (int i = 0; i < 2; i++) {
			result[i][0] = arr[i][2];
			result[i][1] = arr[i][1];
			result[i][2] = arr[i][0];
		}

		return result;
	}

	/**
	 * This method prints the given 2D array of 2*3 onto console This will omit 3rd
	 * element in 2nd row(as it is always 0).
	 * 
	 * @param result
	 * @return
	 */
	String print(int[][] result) {
		String str = "";
		for (int i = 0; i < attackerDice; i++) {

			str += result[0][i] + "\n";
		}

		str += "\n\n";
		for (int i = 0; i < defenderDice; i++) {

			str += result[1][i] + "\n";
		}

		return str;
	}

	/**
	 * @param attackerDice the attackerDice to set
	 * @throws Exception
	 */
	private void setAttackerDice(int attackerDice) throws Exception {

		if (attackerDice <= 3 && attackerDice > 0)
			this.attackerDice = attackerDice;
		else {

			throw new Exception("Attacker can select dice from 1 to 3(max) only.");
		}
	}

	/**
	 * @param defenderDice the defenderDice to set
	 * @throws Exception
	 */
	private void setDefenderDice(int defenderDice) throws Exception {
		if (defenderDice <= 2 && defenderDice > 0)
			this.defenderDice = defenderDice;
		else {

			throw new Exception("Defender can select dice from 1 to 2(max) only.");
		}
	}

}
