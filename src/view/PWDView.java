package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.Map;
import pattern.Observable;
import pattern.Observer;

/**
 * This is Player World Domination View class.
 * 
 * @author Hartaj, Ekjot
 *
 */

public class PWDView implements Observer {

	private static JFrame frame = null;
	private static JTextArea txtrActions;
	private static JScrollPane scrollPane;
	private static Map map=null;

	public static void main(String[] args) {
		PWDView view = new PWDView();
	}

	@Override
	 public void update(Observable obj) {
	// TODO Auto-generated method stub
		
		map=(Map)obj;
		
		calcPercentMap();
		calcContinentControl();
		calcTotalArmies();

}

	private void calcTotalArmies() {
		// TODO Auto-generated method stub
		
	}

	private void calcContinentControl() {
		// TODO Auto-generated method stub
		
	}
/**
 * This method calculates the percentage of map controlled by each player.
 */
	private void calcPercentMap() {
		// TODO Auto-generated method stub
		int totalCountryNum=map.getListOfCountries().size();
		
		
		
		
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
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			txtrActions = new JTextArea();
			txtrActions.setEditable(false);

			scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtrActions);

			frame.getContentPane().add(txtrActions, BorderLayout.CENTER);
			frame.setVisible(true);

		}
	}
}
