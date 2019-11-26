package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.Player;
import pattern.observer.Observable;
import pattern.observer.Observer;

/**
 * Class to implement the Card Exchange View.
 *
 */
public class CardExchangeView implements Observer {

	public static JFrame frame = null;
	private static JTextArea txtrActions;
	public static CardExchangeView window = null;
	private static JScrollPane scrollPane;

	/**
	 * This method updates the card exchange view after receiving the notification
	 * 
	 * @param obj
	 *            Object of observable class
	 */
	public void update(Observable obj) {
		// TODO Auto-generated method stub
		if (((Player) obj).getView().contains("CardExchangeView")) {
			if (frame == null) {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

				frame = new JFrame();
				frame.setBounds(screenSize.width * 2 / 3, screenSize.height * 2 / 3, screenSize.width / 3,
						screenSize.height / 3);
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.setTitle("Card Exchange View");
				frame.setAlwaysOnTop(true);
				txtrActions = new JTextArea();
				txtrActions.setEditable(false);
				txtrActions.setMargin(new Insets(5, 10, 10, 5));

				txtrActions.setForeground(Color.white);
				txtrActions.setBackground(Color.BLACK);
				// txtrActions.setText("");

				scrollPane = new JScrollPane();
				scrollPane.setViewportView(txtrActions);

				scrollPane.setForeground(Color.white);
				scrollPane.setBackground(Color.BLACK);

				frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {

							CardExchangeView.frame.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

			txtrActions.append("Player Name : " + ((Player) obj).getName() + "\n");
			txtrActions.append("CardList :\n");
			for (int i = 0; i < ((Player) obj).getCards().size(); i++) {
				txtrActions.append(i + 1 + " : " + ((Player) obj).getCards().get(i) + "\n");
			}
			txtrActions.append(((Player) obj).getActions());
			txtrActions.append("\n");
		}
	}

	/**
	 * Method to close the frame
	 */
	public void close() {
		// TODO Auto-generated method stub
		if (frame != null) {
			frame.setVisible(false);
			frame = null;
		}

	}
}
