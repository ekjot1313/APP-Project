
package test.pattern.Strategy;

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
import pattern.Strategy.AggressiveStrategy;
import pattern.Strategy.BenevolentStrategy;
import pattern.Strategy.HumanStrategy;
import pattern.Strategy.Strategy;

/**
 * Class to test the Player Class 
 * @author Piyush
 *
 */
public class AggressiveStrategyTest {
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
		//pakistan.getNeighbors().add(china);
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
		A.setStrategy(new AggressiveStrategy());
		A.setName("A");
		A.setNoOfArmies(40);
		A.getAssigned_countries().add(india);
		A.getAssigned_countries().add(pakistan);
		B.setStrategy(new AggressiveStrategy());
		B.setName("B");
		B.setNoOfArmies(40);
		B.getAssigned_countries().add(china);
		listOfPlayers=new ArrayList<Player>();
		listOfPlayers.add(A);
		listOfPlayers.add(B);
	}
	/**
	 * Test method for reinforcement
	 */
	@Test
	public void testReinforcement() {
		try {
			india.setNoOfArmies(18);
			pakistan.setNoOfArmies(22);
			Player.deck = new ArrayList<String>();
			A.getCards().add("Test1 infantry");
			A.getCards().add("Test2 infantry");
			A.getCards().add("Test3 infantry");
			A.executeReinforcement(testMap, listOfPlayers);
			assertEquals(26, india.getNoOfArmies()); 
			assertEquals(22, pakistan.getNoOfArmies());
			assertEquals(48, A.getNoOfArmies());
			
			A.getCards().add("Test1 infantry");
			A.getCards().add("Test2 cavalry");
			A.getCards().add("Test3 artillery");
			india.getNeighbors().clear();
			india.getNeighbors().add("pakistan");
			pakistan.getNeighbors().clear();
			pakistan.getNeighbors().add("china");
			pakistan.getNeighbors().add("india");
			china.getNeighbors().clear();
			china.getNeighbors().add("pakistan");
			china.setNoOfArmies(1);
			A.setNoOfArmies(40);
			B.setNoOfArmies(1);
			india.setNoOfArmies(22);
			pakistan.setNoOfArmies(18);
			A.executeReinforcement(testMap, listOfPlayers);
			assertEquals(22, india.getNoOfArmies()); 
			assertEquals(31, pakistan.getNoOfArmies());
			assertEquals(53, A.getNoOfArmies());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Test method for attack
	 */
	@Test
	public void testAttack() {
		try {
			china.setNoOfArmies(1);
			Country nepal=new Country();
			nepal.setName("nepal");
			nepal.getNeighbors().add("india");
			india.getNeighbors().add("nepal");
			nepal.setOwner("B");
			testMap.getListOfCountries().add(nepal);
			nepal.setContinentName("asia");
			B.setNoOfArmies(2);
			B.setNoOfArmies(2);
			A.executeAttack(testMap, listOfPlayers);
			assertEquals(1,listOfPlayers.size());
			assertEquals(1,A.getStrategy().endGame(listOfPlayers));
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
		
		india.setNoOfArmies(22);
		pakistan.setNoOfArmies(10);
		india.getNeighbors().clear();
		india.getNeighbors().add("pakistan");
		pakistan.getNeighbors().clear();
		pakistan.getNeighbors().add("china");
		pakistan.getNeighbors().add("india");
		china.getNeighbors().clear();
		china.getNeighbors().add("pakistan");
		A.executeFortification(testMap, listOfPlayers,null);
		assertEquals(1, india.getNoOfArmies()); 
		assertEquals(31, pakistan.getNoOfArmies());
		
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
}
