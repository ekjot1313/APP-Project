package test.daotest;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;


public class DaoPlayerTest {
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
		testMap.getListOfContinent().add(asia);
		
		A.setName("A");
		A.setNoOfArmies(40);
		A.getAssigned_countries().add(india);
		A.getAssigned_countries().add(pakistan);
		B.setName("B");
		B.setNoOfArmies(40);
		B.getAssigned_countries().add(china);
		listOfPlayers=new ArrayList<Player>();
		listOfPlayers.add(A);
		listOfPlayers.add(B);
	}
	
	
	@Test
	public void testValidate() {
		String command="attack india china -allout";
		int result=A.validate(command, testMap);
		assertEquals(1, result);
		
		System.out.println("for Player A-> attack india china -1 ");
		command="attack india china -1";
		result=A.validate(command, testMap);
		assertEquals(0, result);
		
		System.out.println("\nfor Player A-> attack china india -allout ");
		command="attack china india -allout";
		result=A.validate(command, testMap);
		assertEquals(0, result);
		
		System.out.println("\nfor Player A-> attack pakistan india -allout");
		command="attack pakistan india -allout";
		result=A.validate(command, testMap);
		assertEquals(0, result);
		
		System.out.println("\nfor Player A-> attack pakistan china -allout");
		command="attack pakistan china -allout";
		result=A.validate(command, testMap);
		assertEquals(0, result);
		
		System.out.println("\nfor Player A-> attack pakistan china -allout");
		command="attack pakistan china -allout";
		result=B.validate(command, testMap);
		assertEquals(0, result);
		
		System.out.println("\nfor Player B-> attack china pakistan -allout ");
		command="attack china pakistan -allout";
		result=B.validate(command, testMap);
		assertEquals(0, result);
		
		System.out.println("\nfor Player B-> attack china india 4");
		command="attack china india 4";
		result=B.validate(command, testMap);
		assertEquals(0, result);
	}
	@Test
	public void testAttackMove() {
		//if china wins india-
		
		china.setNoOfArmies(20);
		india.setNoOfArmies(1);
		india.setOwner("B");
		String command="attackmove 45";
		System.out.println("\nFor-> attackmove 45");
		int result=B.attackMove(command, china, india);
		assertEquals(0, result);
		System.out.println("\nFor-> attackmove -1");
		command="attackmove -1";
		result=B.attackMove(command, china, india);
		assertEquals(0, result);
		System.out.println("\nFor-> attackmove 3");
		command="attackmove 3";
		result=B.attackMove(command, china, india);
		assertEquals(1, result);
		assertEquals(4, india.getNoOfArmies());
		assertEquals(17, china.getNoOfArmies());
	}
	@Test
	public void testEndGame() {
		int result=A.endGame(listOfPlayers);
		assertEquals(0,result);
		listOfPlayers.remove(1);
		
		result=A.endGame(listOfPlayers);
		assertEquals(1,result);
	}
	@Test
	public void testAttackDeadlock() {
		
		int result=A.attackDeadlock(testMap);
		assertEquals(0,result);
		
		india.setNoOfArmies(1);
		result=A.attackDeadlock(testMap);
		assertEquals(1,result);
		
		china.setNoOfArmies(1);
		B.setNoOfArmies(1);
		result=B.attackDeadlock(testMap);
		assertEquals(1,result);
	}
	@Test
	public void testFortification() {
		
		System.out.println("\nFor Player A-> fortify pakistan china 10");
		A.fortification(testMap, listOfPlayers, "fortify pakistan china 10");
		assertEquals(20,pakistan.getNoOfArmies());
		assertEquals(40,china.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify -none");
		A.fortification(testMap, listOfPlayers, "fortify -none");
		assertEquals(20,pakistan.getNoOfArmies());
		assertEquals(20,india.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify china india 5");
		A.fortification(testMap, listOfPlayers, "fortify china india 5");
		assertEquals(20,pakistan.getNoOfArmies());
		assertEquals(20,india.getNoOfArmies());
		assertEquals(40,china.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify pakistan india 30");
		A.fortification(testMap, listOfPlayers, "fortify pakistan india 30");
		assertEquals(20,india.getNoOfArmies());
		assertEquals(20,pakistan.getNoOfArmies());
	
		System.out.println("\nFor Player A-> fortify pakistan india 10");
		A.fortification(testMap, listOfPlayers, "fortify pakistan india 10");
		assertEquals(30,india.getNoOfArmies());
		assertEquals(10,pakistan.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify pakistan india 10");
		pakistan.setNoOfArmies(1);
		A.fortification(testMap, listOfPlayers, "fortify pakistan india 10");
		assertEquals(30,india.getNoOfArmies());
		assertEquals(1,pakistan.getNoOfArmies());
		
		System.out.println("\nFor Player A-> fortify pakistan india 10 5");
		pakistan.setNoOfArmies(1);
		A.fortification(testMap, listOfPlayers, "fortify pakistan india 10 5");
		assertEquals(30,india.getNoOfArmies());
		assertEquals(1,pakistan.getNoOfArmies());
		
	}
	
	
}
