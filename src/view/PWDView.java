package view;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import dao.Continent;
import dao.Country;
import dao.Map;
import pattern.Observable;
import pattern.Observer;
import java.text.DecimalFormat;
/**
 * This is Player World Domination View class.
 * 
 * @author Hartaj, Ekjot
 *
 */

public class PWDView implements Observer {

	private static final DecimalFormat df2 = new DecimalFormat("#.##");
	
	private static JFrame frame = null;
	private static JTextArea countryPercentageTA;
	private static JTextArea continentOwnerTA;
	private static JTextArea playerArmiesTA;
	private static JScrollPane scrollPane1;
	private static JScrollPane scrollPane2;
	private static JScrollPane scrollPane3;
	private static Map map = null;

	private static String percMap = "";
	private static String contOwn = "";

	@Override
	public void update(Observable obj) {
		// TODO Auto-generated method stub

		map = (Map) obj;

		
//		 for (Continent continent : map.getListOfContinent()) { String owner =
//		 continent.getOwner();
//		 System.out.println("continent:"+continent.getName()+"   owner+"+owner ); }
		 
//		for (Country country : map.getListOfCountries()) {
//			String owner = country.getOwner();
//System.out.println("country:"+country.getName()+"   owner+"+owner );
//		}
		
		
		calcPercentMap();
		//calcContinentControl();
		//calcTotalArmies();

	}

	private void calcTotalArmies() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method gives continents owned by every player
	 */
	private void calcContinentControl() {
		// TODO Auto-generated method stub

		
		ArrayList<String> players = new ArrayList<String>();

		for (Continent continent : map.getListOfContinent()) {
			String owner = continent.getOwner();

			if (!players.contains(owner)) {
				players.add(owner);

			}
		}
		
		
		for (Continent continent : map.getListOfContinent()) {
			String owner = continent.getOwner();
System.out.println("continent:"+continent.getOwner()+"   owner+"+owner );
		}
System.exit(0);
		ArrayList<ArrayList<String>> continentsOwned = new ArrayList<ArrayList<String>>();
	
		
		for (String player : players) {
			
			ArrayList<String> myContinents = new ArrayList<String>();

			for (Continent continent : map.getListOfContinent()) {
				String owner = continent.getOwner();
				
				System.out.println("continent:"+continent.getName()+"    owner:"+owner+"   player:"+player);
				if (owner.equals(player)) {
					myContinents.add(continent.getName());
				}

			} 
			
			continentsOwned.add(myContinents);
			
		}
		
		
		
		
		
		
		
		
		
		for (int i = 0; i < continentsOwned.size(); i++) {

			/*
			 * if (players.get(i) == null) { contOwn += "FREE CONTINENTS: "; } else {
			 * contOwn += players.get(i) + ": "; }
			 */

			for (String cont : continentsOwned.get(i)) {
				contOwn += cont + ", ";
			}

			contOwn += "\n";
		}

		continentOwnerTA.setText(contOwn);
		contOwn = "";

	}

	/**
	 * This method calculates the percentage of map controlled by each player.
	 */
	private void calcPercentMap() {
		// TODO Auto-generated method stub
		int totalCountryNum = map.getListOfCountries().size();
		ArrayList<String> players = new ArrayList<String>();

		for (Country country : map.getListOfCountries()) {
			String owner = country.getOwner();

			if (!players.contains(owner)) {
				players.add(owner);

			}
		}

		double[] num = new double[players.size()];

		for (Country country : map.getListOfCountries()) {
			String owner = country.getOwner();
			int ind = players.indexOf(owner);
			num[ind]++;
		}

		for (int i = 0; i < num.length; i++) {
			

			percMap += players.get(i) + ": " + df2.format(num[i] * 100 / totalCountryNum) + "%\n";
		}

		countryPercentageTA.setText(percMap);
		percMap = "";

	}

	/**
	 * Create the application.
	 */
	public PWDView() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		if (frame == null) {
			frame = new JFrame("Player World Domination View");
			frame.setBounds(100, 100, 650, 500);
			// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			countryPercentageTA = new JTextArea();

			countryPercentageTA.setEditable(false);

			continentOwnerTA = new JTextArea();
			continentOwnerTA.setEditable(false);

			playerArmiesTA = new JTextArea();
			playerArmiesTA.setEditable(false);

			scrollPane1 = new JScrollPane();
			scrollPane1.setViewportView(countryPercentageTA);
			scrollPane1.setBorder((TitledBorder) BorderFactory.createTitledBorder("Map Percentage"));

			scrollPane2 = new JScrollPane();
			scrollPane2.setViewportView(continentOwnerTA);
			scrollPane2.setBorder((TitledBorder) BorderFactory.createTitledBorder("Continents Information"));

			scrollPane3 = new JScrollPane();
			scrollPane3.setViewportView(playerArmiesTA);
			scrollPane3.setBorder((TitledBorder) BorderFactory.createTitledBorder("Armies Owned"));

			frame.setLayout(new GridLayout(1, 3));

			frame.getContentPane().add(scrollPane1);
			frame.getContentPane().add(scrollPane2);
			frame.getContentPane().add(scrollPane3);
			frame.setVisible(true);

		}
	}
	/*
	 * public static void main(String[] args) {
	 * 
	 * PWDView pwdView=new PWDView(); countryPercentageTA.setText(
	 * "1\n1\n1\n1\n1\n1\n1\n\1n\1\n1\n1\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\1\n\n\n\n\n\n\n\n\n\n\1"
	 * ); continentOwnerTA.setText(""); playerArmiesTA.setText(""); }
	 */

}
