
package test.pattern.strategy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import pattern.strategy.BenevolentStrategy;
import pattern.strategy.HumanStrategy;
import pattern.strategy.Strategy;

/**
 * Class to test the Player Class 
 * @author Piyush
 *
 */
public class BenevolentStrategyTest {
	/**
	 * Object of Map 
	 */
	Map testMap;
	/**
	 * Object of Country india 
	 */
	Country india;
	/**
	 * Object of Country pakistan
	 */
	Country pakistan;
	/**
	 * Object of Country china
	 */
	Country china;
	/**
	 * Object of Player A
	 */
	Player A;
	/**
	 * Object of Player B
	 */
	Player B;
	/**
	 * ArrayList to store the list of players
	 */
	ArrayList<Player> listOfPlayers;
	
	/**
	 * Set up method to initialize the objects
	 */
	@Before 
	public void before() {
		testMap=new Map();
		india =new Country();
		pakistan =new Country();
		china =new Country();
		A = new Player();
		B = new Player();
		india.setName("india");
		india.setContinentName("asia");
		india.setNoOfArmies(20);
		india.setOwner("A");
		india.getNeighbors().add("pakistan");
		india.getNeighbors().add("china");
		
		pakistan.setName("pakistan");
		pakistan.setContinentName("asia");
		pakistan.setNoOfArmies(20);
		pakistan.setOwner("A");
		pakistan.getNeighbors().add("india");
		china.setName("china");
		china.setContinentName("asia");
		china.setNoOfArmies(40);
		china.setOwner("B");
		china.getNeighbors().add("india");
		testMap.getListOfCountries().add(india);
		testMap.getListOfCountries().add(pakistan);
		testMap.getListOfCountries().add(china);
		Continent asia=new Continent();
		asia.setName("asia");
		testMap.getListOfContinent().add(asia);
		A.setStrategy(new BenevolentStrategy());
		A.setName("A");
		A.setNoOfArmies(40);
		A.getAssigned_countries().add(india);
		A.getAssigned_countries().add(pakistan);
		B.setStrategy(new BenevolentStrategy());
		B.setName("B");
		B.setNoOfArmies(40);
		B.getAssigned_countries().add(china);
		listOfPlayers=new ArrayList<Player>();
		listOfPlayers.add(A);
		listOfPlayers.add(B);
	}
	/**
	 * Test method for Attack
	 */
	@Test
	public void testAttack() {
		try {
			A.executeAttack(testMap, listOfPlayers);
			assertEquals(20,india.getNoOfArmies());
			assertEquals(20,pakistan.getNoOfArmies());
			assertEquals(40,china.getNoOfArmies());
			assertEquals(40,A.getNoOfArmies());
			assertEquals(40,B.getNoOfArmies());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Test method for Reinforcement
	 */
	@Test
	public void testReinforcement() {
		try {
			india.setNoOfArmies(18);
			pakistan.setNoOfArmies(22);
			A.executeReinforcement(testMap, listOfPlayers);
			assertEquals(21, india.getNoOfArmies()); 
			assertEquals(22, pakistan.getNoOfArmies());
			assertEquals(43, A.getNoOfArmies());
			A.setNoOfArmies(40);
			india.setNoOfArmies(22);
			pakistan.setNoOfArmies(18);
			A.executeReinforcement(testMap, listOfPlayers);
			assertEquals(25, india.getNoOfArmies()); 
			assertEquals(18, pakistan.getNoOfArmies());
			assertEquals(43, A.getNoOfArmies());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Test method for fortification
	 */
	@Test
	public void testFortification() {
		try{
 		india.setNoOfArmies(22);
		pakistan.setNoOfArmies(10);
		A.executeFortification(testMap, listOfPlayers,null);
		assertEquals(31, india.getNoOfArmies()); 
		assertEquals(1, pakistan.getNoOfArmies());
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
}
