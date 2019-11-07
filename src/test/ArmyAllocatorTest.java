package test;

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

public class ArmyAllocatorTest {
	static Map testMap=new Map();
	static Country india =new Country();
	static Country pakistan =new Country();
	static Country china =new Country();
	static Player A = new Player();
	static Player B = new Player();
	static ArrayList<Player> listOfPlayers;
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
