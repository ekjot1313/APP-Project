package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;

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
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			frame = new JFrame();
			frame.setBounds(screenSize.width*2/3, screenSize.height*2/3, screenSize.width/3, screenSize.height/3);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setTitle("Card Exchange View");
			frame.setAlwaysOnTop(true);
			txtrActions = new JTextArea();
			txtrActions.setEditable(false);
			txtrActions.setMargin( new Insets(5,10,10,5) );
			//txtrActions.setText("");
			
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtrActions);
			
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
		if(((Player) obj).getView().contains("CardExchangeView")) {
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
