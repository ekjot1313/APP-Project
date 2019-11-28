package test.daotest;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import dao.Dice;

/**
 * Test class to check the Dice class
 * 
 * @author Hartaj, Ekjot
 */
public class DaoDiceTest {

	/**
	 * Object of dice class
	 */
	Dice dice;

	/**
	 * Test method for {@link dao.Dice#roll()}.
	 */
	@Test
	public final void testRoll() {
		dice = new Dice();
		int rolled = dice.roll();
		assertTrue(rolled >= 1 && rolled <= 6);
	}

	/**
	 * Test method for {@link dao.Dice#sort(int[][])}.
	 */
	@Test
	public final void testSort() {
		dice = new Dice();
		int[][] arr = { { 1, 2, 3 }, { 4, 5, 6 } };
		int[][] expected = { { 3, 2, 1, }, { 6, 5, 4 } };

		assertTrue(Arrays.deepEquals(expected, dice.sort(arr)));
	}

	/**
	 * Test method for {@link dao.Dice#setAttackerDice(int)}.
	 */
	@Test
	public final void testSetAttackerDice() {

		dice = new Dice();
		int attackerDice = 3;
		assertTrue(dice.setAttackerDice(attackerDice));

		attackerDice = 4;
		assertTrue(!dice.setAttackerDice(attackerDice));

		attackerDice = 0;
		assertTrue(!dice.setAttackerDice(attackerDice));
	}

	/**
	 * Test method for {@link dao.Dice#setDefenderDice(int)}.
	 */
	@Test
	public final void testSetDefenderDice() {

		dice = new Dice();

		int defenderDice = 2;
		assertTrue(dice.setDefenderDice(defenderDice));

		defenderDice = 3;
		assertTrue(!dice.setDefenderDice(defenderDice));

		defenderDice = 0;
		assertTrue(!dice.setDefenderDice(defenderDice));
	}

	/**
	 * Test method for {@link dao.Dice#rollAll()}.
	 */
	@Test
	public final void testRollAll() {

		dice = new Dice(3, 2);

		int[][] arr = dice.rollAll();
		boolean result = true;

		for (int i = 0; i < dice.getAttackerDice(); i++) {
			if (arr[0][i] < 1 || arr[0][i] > 6) {
				result = false;
			}

		}

		for (int i = 0; i < dice.getDefenderDice(); i++) {
			if (arr[1][i] < 1 || arr[1][i] > 6) {
				result = false;
			}

		}

		assertTrue(result);

	}

	/**
	 * Test method for {@link dao.Dice#print(int[][])}.
	 */
	@Test
	public final void testPrint() {
		dice = new Dice(3, 2);
		int[][] arr = { { 1, 2, 3 }, { 1, 2, 3 } };
		String str = dice.print(arr);

		assertTrue(str.equals("1\n2\n3\n\n\n1\n2\n"));

	}

}