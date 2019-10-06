package Game;

import java.util.*;

public class PlayerAllocator extends MapReader {
	public List<Player> listOfPlayers;
	public PlayerAllocator(){
	this.listOfPlayers=new ArrayList<Player>();
	}
	public void allocate() {
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
		populateCountries(map);
		printPlayerCountries();
	}
	public void printPlayerCountries() {
		for(int i=0;i<listOfPlayers.size();i++) {
			System.out.println("Player :"+listOfPlayers.get(i).name);
			System.out.println("Countries owned:");
			for(int j=0;j<listOfPlayers.get(i).assigned_countries.size();j++)
			{
				System.out.println(listOfPlayers.get(i).getAssigned_countries().get(j).getName());
			}
		}
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
	public void populateCountries(Map map) {
		int countryCount=map.listOfCountries.size();
		int playerCount=listOfPlayers.size();
		int j=(countryCount/playerCount);
		int count=0;
		for(int i=0;i<j;i++)
		{	
			for(int k=0;k<playerCount;k++)
			{
				System.out.println(listOfPlayers.get(k).getName());
				System.out.println(map.listOfCountries.get(count).getName());
				listOfPlayers.get(k).assigned_countries.add(map.listOfCountries.get(count));
				map.listOfCountries.get(count).setOwner(listOfPlayers.get(k));
				count++;	
			}
		}
		int leftCountries=countryCount-count;
		for(int m=0;m<leftCountries;m++)
		{
			listOfPlayers.get(m).assigned_countries.add(map.listOfCountries.get(count));
			map.listOfCountries.get(count).setOwner(listOfPlayers.get(m));
			count++;
			
		}
	}

}
