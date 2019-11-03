package view;

import pattern.Observable;
import pattern.Observer;

import java.util.ArrayList;

import dao.Player;

public class PhaseView implements Observer{
	static int count=0;
	@Override
	public void update(Observable obj) {
		// TODO Auto-generated method stub
		if(count==0) {
		System.out.println("Phase: "+((Player) obj).getState());
		System.out.println("Player Name: "+((Player) obj).getName());
		count++;
		}else {
			System.out.println(((Player) obj).getActions());
		}
	}

}
