package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.Player;
import pattern.Observable;
import pattern.Observer;

public class CardExchangeView implements Observer{

	public static JFrame frame=null;
	private static JTextArea txtrActions;
	public static CardExchangeView window=null;
	private static JScrollPane scrollPane;
	
	public void update(Observable obj) {
		// TODO Auto-generated method stub
		if(frame==null) {
			frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Card Exchange View");
			txtrActions = new JTextArea();
			txtrActions.setEditable(false);
			//txtrActions.setText("");
			
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtrActions);
			
			frame.getContentPane().add(txtrActions, BorderLayout.CENTER);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					CardExchangeView window = new CardExchangeView();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		}
		if(((Player) obj).getView().contains("CardExchangeView")) {
			System.out.println("Im here");
		//txtrActions.setText("Card Exchange");
		txtrActions.append("Player Name : "+((Player) obj).getName()+"\n");
		txtrActions.append("CardList :\n");
		for(int i=0;i<((Player) obj).getCards().size();i++) {
			txtrActions.append(i+1+" : "+((Player) obj).getCards().get(i)+"\n");
		}
		txtrActions.append(((Player) obj).getActions());
		}
}
	
	/**
	 * Create the application.
	 */
	public CardExchangeView() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		
	}
	public void close() {
		// TODO Auto-generated method stub
		frame.setVisible(false);
		frame = null;
		
	}
}
