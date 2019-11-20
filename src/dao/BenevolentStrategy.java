package dao;

import java.util.ArrayList;

public class BenevolentStrategy implements Strategy  {
	public void reinforcement(Map map, ArrayList<Player> listPlayer,Player P) {
		
	}
	public int attack(Map map, ArrayList<Player> listPlayer,Player P) {
		P.setEndOfActions(1); 
		System.out.println("Attack skipped");
		P.setActions("Attack finished");
		return 0;
	}
	public void fortification(Map map, ArrayList<Player> listPlayer, String command,Player P) {
		P.setEndOfActions(1); 
		System.out.println("Fortification skipped");
		P.setActions("Fortification finished");
	}

}
