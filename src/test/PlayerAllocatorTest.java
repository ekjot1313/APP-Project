package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import game.PlayerAllocator;

public class PlayerAllocatorTest {
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
	public void testAllocate() {
		PlayerAllocator pa = new PlayerAllocator();
		pa.allocate(testMap,"gameplayer -add C");
		int playeradded =0;
		for(Player p :testMap.getListOfPlayers()) {
		if(p.getName().equals("C"))
			playeradded=1;
		}
		assertEquals(1,playeradded);
		
		int playerremoved =1;
		pa.allocate(testMap,"gameplayer -remove C");
		for(Player p :testMap.getListOfPlayers()) {
			if(p.getName().equals("C"))
				playerremoved=0;
		}
		assertEquals(1,playerremoved);
		
	}
	
	@Test
	public void testPopulateCountries() {
		PlayerAllocator pa = new PlayerAllocator();
		pa.allocate(testMap,"populatecountries");
		pa.populateCountries(testMap);
		boolean isPopulated = false;
		if(china.getOwner()!= null && india.getOwner()!= null && pakistan.getOwner() !=null)
			isPopulated =true;
		assertTrue(isPopulated);
	}
	
}
