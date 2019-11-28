package pattern.observer;

import java.awt.Color;
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
import pattern.observer.Observable;
import pattern.observer.Observer;
import java.text.DecimalFormat;

/**
 * Class to implement the Player World Domination View.
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
	
	
	
	/**
	 * This method updates the player world domination view after receiving the notification
	 * @param obj Object of observable class
	 */
	public void update(Observable obj) {
		// TODO Auto-generated method stub

		Map map = (Map) obj;

		calcPercentMap(map);
		calcContinentControl(map);
		calcTotalArmies(map);

	}

	/**
	 * Method to calculate total number of armies owned by each player
	 * @param map Map object
	 * @return Player name along with number of armies owned
	 */
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
	 * Method to calculate continents owned by every player
	 * @param map Map object
	 * @return Player name along with continent owned
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
	 * Method to calculate the percentage of map controlled by each player.
	 * @param map Map Object
	 * @return PLayer name along with percentage of map controlled
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
			if(ind != -1)
			num[ind]++;
		}

		for (int i = 0; i < num.length; i++) {

			mapOwn += players.get(i) + ": " + df2.format(num[i] * 100 / totalCountryNum) + "%\n";
		}

		countryPercentageTA.setText(mapOwn);
		return mapOwn;

	}

	/**
	 * Constructor to create the application.
	 * @param visible1 value of visible
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
			
			
			frame = new JFrame("Player World Domination View");
			
			
			
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setAlwaysOnTop(true);
			frame.setFocusableWindowState(false);

			countryPercentageTA = new JTextArea();

			countryPercentageTA.setEditable(false);
			countryPercentageTA.setForeground(Color.white);
			countryPercentageTA.setBackground(Color.BLACK);
			

			continentOwnerTA = new JTextArea();
			continentOwnerTA.setEditable(false);
			continentOwnerTA.setForeground(Color.white);
			continentOwnerTA.setBackground(Color.BLACK);

			playerArmiesTA = new JTextArea();
			playerArmiesTA.setEditable(false);
			playerArmiesTA.setForeground(Color.white);
			playerArmiesTA.setBackground(Color.BLACK);

			scrollPane1 = new JScrollPane();
			scrollPane1.setViewportView(countryPercentageTA);
			
			TitledBorder border=(TitledBorder) BorderFactory.createTitledBorder("Map Percentage");
			border.setTitleColor(Color.white);
			scrollPane1.setBorder(border);
			scrollPane1.setBackground(Color.BLACK);

			scrollPane2 = new JScrollPane();
			scrollPane2.setViewportView(continentOwnerTA);
			border=(TitledBorder) BorderFactory.createTitledBorder("Continents Information");
			border.setTitleColor(Color.white);
			scrollPane2.setBorder(border);
			scrollPane2.setBackground(Color.BLACK);

			scrollPane3 = new JScrollPane();
			scrollPane3.setViewportView(playerArmiesTA);
			border=(TitledBorder) BorderFactory.createTitledBorder("Armies Owned");
			border.setTitleColor(Color.white);
			scrollPane3.setBorder(border);
			scrollPane3.setBackground(Color.BLACK);

			frame.setLayout(new GridLayout(1, 3));

			frame.getContentPane().add(scrollPane1);
			frame.getContentPane().add(scrollPane2);
			frame.getContentPane().add(scrollPane3);
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setBounds((screenSize.width*2)/3, 0, (screenSize.width)/3, (screenSize.height)/3);
			frame.setBackground(Color.BLACK);
			setVisible(visible);
			
		}
	}

	/**
	 * Method to close the frame
	 */
	public void close() {
		// TODO Auto-generated method stub
		frame.dispose();
	}

	/**
	 * Method to check if the frame is visible
	 * @return value of visible
	 */
	public static boolean isVisible() {
		return visible;
	}

	/**
	 * Method to make the frame visible
	 * @param visible1 value of visible
	 */
	public static void setVisible(boolean visible1) {
		visible=visible1;
		frame.setVisible(visible1);
	}

}
