package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import dao.Continent;
import dao.Country;
import dao.Map;
import dao.Player;
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
	private static boolean visible=true;
	
	
	

	@Override
	public void update(Observable obj) {
		// TODO Auto-generated method stub

		Map map = (Map) obj;

		calcPercentMap(map);
		calcContinentControl(map);
		calcTotalArmies(map);

	}

	public String calcTotalArmies(Map map) {
		// TODO Auto-generated method stub
		String armyOwn = "";
		for(Player player:map.getListOfPlayers()) {
			armyOwn+=player.getName()+": "+player.getNoOfArmies()+"\n";
		}
		
		playerArmiesTA.setText(armyOwn);
		return armyOwn;
		
		
	}

	/**
	 * This method gives continents owned by every player
	 * @param map 
	 */
	public String calcContinentControl(Map map) {
		// TODO Auto-generated method stub
		String contOwn = "";
		List<String> players = new ArrayList<String>();

		players.add("FREE CONTINENTS");

		// manually copying to avoid getting same memory address
		for (Player player : map.getListOfPlayers()) {
			players.add(player.getName());
		}

		ArrayList<ArrayList<String>> continentsOwned = new ArrayList<ArrayList<String>>();

		for (String player : players) {

			ArrayList<String> myContinents = new ArrayList<String>();

			for (Continent continent : map.getListOfContinent()) {
				String owner = continent.getOwner();
				if (owner.equals(player)) {
					myContinents.add(continent.getName());
				}

			}

			continentsOwned.add(myContinents);

		}

		for (int i = 0; i < continentsOwned.size(); i++) {

			contOwn+=players.get(i)+": \n";
			for (String cont : continentsOwned.get(i)) {
				contOwn += "   "+cont + ",\n";
			}

			contOwn += "\n";
		}
		continentOwnerTA.setText(contOwn);
		return contOwn;

	}

	/**
	 * This method calculates the percentage of map controlled by each player.
	 * @param map2 
	 */
	public String calcPercentMap(Map map) {
		// TODO Auto-generated method stub
		String mapOwn = "";
		int totalCountryNum = map.getListOfCountries().size();
		List<String> players = new ArrayList<String>();
		players.add("FREE COUNTRIES");

		// manually copying to avoid getting same memory address
		for (Player player : map.getListOfPlayers()) {
			players.add(player.getName());
		}

		double[] num = new double[players.size()];

		for (Country country : map.getListOfCountries()) {
			String owner = country.getOwner();
			int ind = players.indexOf(owner);
			num[ind]++;
		}

		for (int i = 0; i < num.length; i++) {

			mapOwn += players.get(i) + ": " + df2.format(num[i] * 100 / totalCountryNum) + "%\n";
		}

		countryPercentageTA.setText(mapOwn);
		return mapOwn;

	}

	/**
	 * Create the application.
	 */
	public PWDView(Boolean visible1) {
		visible=visible1;
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		if (frame == null) {
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			frame = new JFrame("Player World Domination View");
			frame.setBounds(screenSize.width*2/3, 0, screenSize.width/3, screenSize.height/3);
			// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setAlwaysOnTop(true);
			frame.setFocusableWindowState(false);

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
			setVisible(visible);
			
		}
	}


	public void close() {
		// TODO Auto-generated method stub
		frame.dispose();
	}

	public static boolean isVisible() {
		return visible;
	}

	public static void setVisible(boolean visible1) {
		visible=visible1;
		frame.setVisible(visible1);
	}

}
