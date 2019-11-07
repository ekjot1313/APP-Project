/**
 * 
 */
package test.viewtest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
import view.PWDView;

/**
 * @author ekjot
 *
 */
public class ViewPWDViewTests {

	PWDView view;
	Map map;
	Player A;
	Player B;
	Continent asia;
	Continent africa;
	Country india;
	Country pakistan;
	Country congo;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		view=new PWDView(false);
		
		
	 map=new Map();
	 
	 A=new Player();
	 A.setName("A");
	 A.setNoOfArmies(10);
	 
	 B=new Player();
	 B.setName("B");
	 B.setNoOfArmies(5);
	 
	 map.addPlayer(A);
	 map.addPlayer(B);
	 
	 asia=new Continent();
	 asia.setName("asia");
	 asia.setOwner("FREE CONTINENTS");
	 
	 africa=new Continent();
	 africa.setName("africa");
	 africa.setOwner("B");
	 
	 india=new Country();
	 india.setName("india");
	 india.setOwner("FREE COUNTRIES");
	 
	 pakistan=new Country();
	 pakistan.setName("pakistan");
	 pakistan.setOwner("A");
	 
	 congo=new Country();
	 congo.setName("congo");
	 congo.setOwner("B");
	 
	 map.addContinent(asia);
	 map.addContinent(africa);
	 
	 map.addCountry(india);
	 map.addCountry(pakistan);
	 map.addCountry(congo);
	}

	/**
	 * Test method for {@link view.PWDView#calcTotalArmies()}.
	 */
	@Test
	public final void testCalcTotalArmies() {
		assertEquals("A: 10\nB: 5\n",view.calcTotalArmies(map));
		
	}

	/**
	 * Test method for {@link view.PWDView#calcContinentControl()}.
	 */
	@Test
	public final void testCalcContinentControl() {

		assertEquals("FREE CONTINENTS: \n   asia,\n\nA: \n\nB: \n   africa,\n\n",view.calcContinentControl(map));
	}

	/**
	 * Test method for {@link view.PWDView#calcPercentMap()}.
	 */
	@Test
	public final void testCalcPercentMap() {
		
		assertEquals("FREE COUNTRIES: 33.33%\nA: 33.33%\nB: 33.33%\n",view.calcPercentMap(map));
	}

}
