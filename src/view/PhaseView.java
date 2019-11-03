package view;

import java.awt.EventQueue;
import pattern.Observable;
import pattern.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import dao.Player;

import java.awt.BorderLayout;

public class PhaseView implements Observer {
	static int count=0;
	private static JFrame frame=null;
	private static JTextArea txtrActions;
	static PhaseView window=null;
	private static JScrollPane scrollPane;
	private String currentState ="";
	public void update(Observable obj) {
		// TODO Auto-generated method stub

		if(frame==null) {
			frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Phase View");
			txtrActions = new JTextArea();
			txtrActions.setEditable(false);
			txtrActions.setText("actions");
			
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtrActions);
			
			frame.getContentPane().add(txtrActions, BorderLayout.CENTER);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					PhaseView window = new PhaseView();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		}
		if(!currentState.equals(((Player) obj).getState()))
			count =0;
		if(count==0) {
			
			txtrActions.setText("");
			txtrActions.append("\nPlayer Name: "+((Player) obj).getName()+"\n");
			txtrActions.append("Phase: "+((Player) obj).getState()+"\n");
			currentState =((Player) obj).getState();
		
		count++;
		}else {
			if(((Player) obj).getActions()!=null)
				txtrActions.append(((Player) obj).getActions()+"\n");
		}
	}
	/**
	 * Create the application.
	 */
	public PhaseView() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		
	}

}
