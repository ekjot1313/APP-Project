package test.gametest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import game.ArmyAllocator;
import game.PlayerAllocator;

/**
 * Test class to check the ArmyAllocator class
 * @author Mitalee Naik
 *
 */
public class ArmyAllocatorTest {
	/**
	 * Object of Map
	 */
	static Map testMap=new Map();
	/**
	 * Object of Country india
	 */
	static Country india =new Country();
	/**
	 * Object of Country pakistan
	 */
	static Country pakistan =new Country();
	/**
	 * Object of Country china
	 */
	static Country china =new Country();
	/**
	 * Object of Player A
	 */
	static Player A = new Player();
	/**
	 * Object of Player B
	 */
	static Player B = new Player();
	/**
	 * ArrayList of player objects
	 */
	static ArrayList<Player> listOfPlayers;
	
	/**
	 * Set up method to initialize objects
	 */
	@Before 
	public void before() {
		
		india.setName("india");
		india.setContinentName("asia");
		india.getNeighbors().add("pakistan");
		india.getNeighbors().add("china");
		
		pakistan.setName("pakistan");
		pakistan.setContinentName("asia");
		pakistan.getNeighbors().add("india");
		
		china.setName("china");
		china.setContinentName("asia");
		china.getNeighbors().add("india");
		
		testMap.getListOfCountries().add(india);
		testMap.getListOfCountries().add(pakistan);
		testMap.getListOfCountries().add(china);
		
		Continent asia=new Continent();
		asia.getCountries().add("india");
		asia.getCountries().add("pakistan");
		asia.getCountries().add("china");
		
		testMap.getListOfContinent().add(asia);
		
		A.setName("A");
		B.setName("B");
		
		listOfPlayers=new ArrayList<Player>();
		listOfPlayers.add(A);
		listOfPlayers.add(B);
		testMap.setListOfPlayers(listOfPlayers);
	}
	
	/**
	 * Test method for {@link game.ArmyAllocator#calculateTotalArmies(ArrayList, Map, int)}
	 */
	@Test
	public void testCalculateTotalArmies() {
		PlayerAllocator pa = new PlayerAllocator();
		pa.allocate(testMap,"populatecountries");
		pa.populateCountries(testMap);
		ArmyAllocator aa = new ArmyAllocator();
		int total =aa.calculateTotalArmies(listOfPlayers,testMap,1);
		assertEquals(40, total);
	}
	
}
