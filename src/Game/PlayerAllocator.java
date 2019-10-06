package Game;

import java.util.*;

public class PlayerAllocator {
	public static List<Player> listOfPlayers;
	PlayerAllocator(){
	this.listOfPlayers=new ArrayList<Player>();
	}
	public static void allocate() {
		Scanner in=new Scanner(System.in);
		String cmd=in.nextLine();
		do
		{
			if(validate(cmd)==1)
			{	String str[]=cmd.split(" ");
				System.out.println("Valid command");
				for(int i=1;i<str.length;i++) {
					if(str[i].equals("-add")) {
						Player p=new Player();
						p.name=str[i+1];
						listOfPlayers.add(p);
						i++;
					}
					if(str[i].equals("-remove")) {
						int j;
						for(j=0;j<listOfPlayers.size();j++)
						{
							if(listOfPlayers.get(j).getName().contentEquals(str[i+1])) {
								listOfPlayers.remove(j);
								j++;
								break;
							}
						}
						if(j==listOfPlayers.size()) {
							System.out.println("Player Not found");
							break;
						}
						
					}
				}
				System.out.println("Player List");
				for(int i=0;i<listOfPlayers.size();i++)
				{
					System.out.println(listOfPlayers.get(i).getName());
				}
					
			}
			else
				System.out.println("Invalid command,Type again");
			cmd=in.nextLine();
		}while(!cmd.equals("populate countries"));
	}
	public static int validate(String command) {
		String str[]=command.split(" ");
		int flag=0,count;
		if((str.length%2)!=0) {
			if(str[0].equals("gameplayer") && (str[1].equals("-add") || str[1].equals("-remove"))) {
					for(int i=1;i<str.length;i++)
					{	count=0;
						if(str[i].equals("-add") || str[i].equals("-remove"))
						{	if(str[i+1].contains("-"))
							 	return 0;
							else
							{	i++;
								count++;
							}
							if(count!=1)
								return 0;
						}
					}
					return 1;
			}
		}
		return 0;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerAllocator P= new PlayerAllocator();
		P.allocate();
	}

}
