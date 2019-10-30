package dao;

public class demo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Dice dice=new Dice(0,1);
		
		System.out.println(dice.print(dice.rollAll()));
		
	}

}
