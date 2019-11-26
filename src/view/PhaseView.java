package view;

import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;

import pattern.observer.Observable;
import pattern.observer.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import dao.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Class to implement the phase view
 *
 */
public class PhaseView implements Observer {
	static int count=0;
	private static JFrame frame=null;
	private static JTextArea txtrActions;
	static PhaseView window=null;
	private static JScrollPane scrollPane;
	
	/**
	 * This method updates the phase view after receiving the notification
	 * @param obj Object of observable class
	 */
	public void update(Observable obj) {
		// TODO Auto-generated method stub
		if(((Player) obj).getView().contains("PhaseView")) {
			
		if(frame==null) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		      
			
			frame = new JFrame();
			frame.setBounds(screenSize.width*2/3, screenSize.height/3, screenSize.width/3, screenSize.height/3);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setTitle("Phase View");
			frame.setAlwaysOnTop(true);
			txtrActions = new JTextArea();
			txtrActions.setEditable(false);
			txtrActions.setText("actions");
			txtrActions.setMargin( new Insets(5,10,10,5) );
			
			txtrActions.setForeground(Color.white);
			txtrActions.setBackground(Color.BLACK);
			
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtrActions);
			
			scrollPane.setForeground(Color.white);
			scrollPane.setBackground(Color.BLACK);
			

			
			frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					PhaseView.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		}
		if(count==0) {
			
			txtrActions.setText("");
			txtrActions.append("\nPlayer Name: "+((Player) obj).getName()+"\n");
			txtrActions.append("Phase: "+((Player) obj).getState()+"\n");
		
		count++;
		}else {
			if(((Player) obj).getActions()!=null)
				txtrActions.append(((Player) obj).getActions()+"\n");
			if(((Player) obj).getEndOfActions() ==1 )
				count=0;
		}
		}
	}
}
