package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

	public static void main(String[] args) {
		PWDView view = new PWDView();
	}

	@Override
	 public void update(Observable obj) {
	// TODO Auto-generated method stub

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
