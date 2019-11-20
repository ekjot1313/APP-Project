package dao;

import java.util.ArrayList;

public interface Strategy {
	
	void reinforcement(Map map, ArrayList<Player> listPlayer,Player P);
	int attack(Map map, ArrayList<Player> listPlayer,Player P);
	void fortification(Map map, ArrayList<Player> listPlayer, String command,Player P);
}
