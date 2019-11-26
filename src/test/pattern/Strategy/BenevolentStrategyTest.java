
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
import pattern.Strategy.HumanStrategy;

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
		Map testMap=new Map();
		Country india =new Country();
		Country pakistan =new Country();
		Country china =new Country();
		Player A = new Player();
		Player B = new Player();
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
		A.setStrategy(new HumanStrategy());
		A.setName("A");
		A.setNoOfArmies(40);
		A.getAssigned_countries().add(india);
		A.getAssigned_countries().add(pakistan);
		B.setStrategy(new HumanStrategy());
		B.setName("B");
		B.setNoOfArmies(40);
		B.getAssigned_countries().add(china);
		listOfPlayers=new ArrayList<Player>();
		listOfPlayers.add(A);
		listOfPlayers.add(B);
	}
	/**
	 * Test method for 
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
	 * Test method for 
	 */
	@Test
	public void testReinforcement() {
		try {
			india.setNoOfArmies(18);
			pakistan.setNoOfArmies(22);
			A.executeReinforcement(testMap, listOfPlayers);
			assertEquals(31, india.getNoOfArmies()); 
			assertEquals(22, pakistan.getNoOfArmies());
			assertEquals(53, A.getNoOfArmies());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Test method for {@link dao.Player#fortification(Map, ArrayList, String)}.
	 */
	@Test
	public void testFortification() {
		System.out.println("\nFor Player A-> fortify pakistan china 10");
		A.executeFortification(testMap, listOfPlayers, "fortify pakistan china 10");
		assertEquals(20,pakistan.getNoOfArmies());
		assertEquals(40,china.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify -none");
		A.executeFortification(testMap, listOfPlayers, "fortify -none");
		assertEquals(20,pakistan.getNoOfArmies());
		assertEquals(20,india.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify china india 5");
		A.executeFortification(testMap, listOfPlayers, "fortify china india 5");
		assertEquals(20,pakistan.getNoOfArmies());
		assertEquals(20,india.getNoOfArmies());
		assertEquals(40,china.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify pakistan india 30");
		A.executeFortification(testMap, listOfPlayers, "fortify pakistan india 30");
		assertEquals(20,india.getNoOfArmies());
		assertEquals(20,pakistan.getNoOfArmies());
	
		System.out.println("\nFor Player A-> fortify pakistan india 10");
		A.executeFortification(testMap, listOfPlayers, "fortify pakistan india 10");
		assertEquals(30,india.getNoOfArmies());
		assertEquals(10,pakistan.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify pakistan india 10");
		pakistan.setNoOfArmies(1);
		A.executeFortification(testMap, listOfPlayers, "fortify pakistan india 10");
		assertEquals(30,india.getNoOfArmies());
		assertEquals(1,pakistan.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify pakistan india 10 5");
		pakistan.setNoOfArmies(1);
		A.executeFortification(testMap, listOfPlayers, "fortify pakistan india 10 5");
		assertEquals(30,india.getNoOfArmies());
		assertEquals(1,pakistan.getNoOfArmies());
		A.getAssigned_countries().clear();
		B.getAssigned_countries().clear();
		
	}
	
	
}
