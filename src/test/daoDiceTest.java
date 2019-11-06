/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.Dice;

/**
 * @author Hartaj
 *
 */
public class daoDiceTest {

	/**
	 * Test method for {@link dao.Dice#roll()}.
	 */
	@Test
	public final void testRoll() {
		
	}

	/**
	 * Test method for {@link dao.Dice#sort(int[][])}.
	 */
	@Test
	public final void testSort() {
	
	}

	/**
	 * Test method for {@link dao.Dice#setAttackerDice(int)}.
	 */
	@Test
	public final void testSetAttackerDice() {
		Dice dice=new Dice();
		
		int attackerDice=3;
		assertTrue(dice.setAttackerDice(attackerDice));
		
		attackerDice=4;
		assertTrue(!dice.setAttackerDice(attackerDice));
		
		attackerDice=0;
		assertTrue(!dice.setAttackerDice(attackerDice));
	}

	/**
	 * Test method for {@link dao.Dice#setDefenderDice(int)}.
	 */
	@Test
	public final void testSetDefenderDice() {
		
	Dice dice=new Dice();
		
		int defenderDice=2;
		assertTrue(dice.setDefenderDice(defenderDice));
		
		defenderDice=3;
		assertTrue(!dice.setDefenderDice(defenderDice));
		
		defenderDice=0;
		assertTrue(!dice.setDefenderDice(defenderDice));
	}

}
