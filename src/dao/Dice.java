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

	/**
	 * This method returns a random number between 1 and 6
	 * @return rolledNumber Rolled number from dice.
	 */
	int roll() {
		int rolledNumber=number.nextInt(6) + 1;
		return rolledNumber;
	}

	/**
	 * This method returns a 2D array of 2*3, of 3 rolledNumbers for both attacker and defender. 
	 * 3rd rolledNumber for defender will always be 0.
	 * 
	 * @return result 2D array of rolled numbers
	 */
	int[][] rollAll(int attackerDice,int defenderDice) {
		int[][] result = new int[2][3];
		for(int i=0;i<2;i++)
			for(int j=0;j<3;j++)
				result[i][j]=0;
		int common=Math.min(attackerDice,defenderDice);
		int col;
		for(col=0;col<common;col++) {
			result[0][col]=roll();
			result[1][col]=roll();
		}
		if(attackerDice != defenderDice) {
		if(attackerDice>defenderDice) {
			for(int n=col;n<attackerDice;n++) {
				result[0][n]=roll();
			}
		}
		else {
			for(int n=col;n<defenderDice;n++) {
				result[1][n]=roll();
			}
		}
		}
			/*
		for (int i = 0; i < 2; i++) {
			result[0][i] = roll();
			result[1][i] = roll();
		}
		result[0][2] = roll();*/
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
	 * This method prints the given 2D array of 2*3 onto console
	 * This will omit 3rd element in 2nd row(as it is always 0).
	 * 
	 * @param result
	 * @return
	 */
	static String print(int[][] result) {
		String str="";
		for (int i = 0; i < 2; i++) {

			str+=result[0][i] + "    " + result[1][i]+"\n";
		}
		str+=result[0][2]+"\n";
		return str;
	}

}
