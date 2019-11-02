package view;

import pattern.Observable;
import pattern.Observer;
import dao.Player;

public class PhaseView implements Observer{

	@Override
	public void update(Observable obj) {
		// TODO Auto-generated method stub
		System.out.println("Phase: "+((Player) obj).getState());
		System.out.println("Player Name: "+((Player) obj).getName());
	}

}
