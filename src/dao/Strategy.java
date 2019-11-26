package dao;

import java.util.ArrayList;

public interface Strategy {
	
	void reinforcement(Map map, ArrayList<Player> listPlayer,Player P);
	int attack(Map map, ArrayList<Player> listPlayer,Player P);
	void fortification(Map map, ArrayList<Player> listPlayer, String command,Player P);
	int validate(String command, Map testMap,Player P);
	int attackMove(String command, Country fromCountry, Country toCountry,int attackerDice,Player P);
	int endGame(ArrayList<Player> listOfPlayers);
	int attackDeadlock(Map testMap,Player P);
	int calculateReinforceArmies(Map testMap, Player P);
}
