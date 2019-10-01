package Game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
public class MapReader {
	public List<Continent> listOfContinent;
	public List<Country> listOfCountries;
	private File fileObject;
	private BufferedReader bufferReaderForFile;
	private String currentLine;
	public HashMap<Integer,List<Integer>> mapOfWorld = new HashMap<Integer,List<Integer>>();
	public void parseMapFile(String filePath)
	{
		
		listOfContinent = new ArrayList<Continent>();
		listOfCountries = new ArrayList<Country>();
		try
		{
			
			bufferReaderForFile=new BufferedReader(new FileReader(new File(filePath)));
			while((currentLine=bufferReaderForFile.readLine())!= null )
			{
				
				if(currentLine.contains("[continents]"))
				{
					while((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("["))
					{
						
						//System.out.println(currentLine);
						if(currentLine.length() == 0) {
							continue;
						}
						String[] continentDetails = currentLine.split(" ");
						
						Continent continent = new Continent();
						continent.setName(continentDetails[0]);
						continent.setContinentValue(Integer.parseInt(continentDetails[1]));
						listOfContinent.add(continent);
						
					}
				}
				for(int i=0;i<listOfContinent.size();i++)
				{
					listOfContinent.get(i).setCountries(new ArrayList<Country>());
				}
				if(currentLine.contains("[countries]"))
				{
					while((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("["))
					{
						
						//System.out.println(currentLine);
						if(currentLine.length() == 0) {
							continue;
						}
						String[] countryDetails = currentLine.split(" ");
						
						Country country = new Country();
						country.setName(countryDetails[1]);
						country.setContinentName(listOfContinent.get((Integer.parseInt(countryDetails[2]))-1));
						listOfCountries.add(country);
						listOfContinent.get((Integer.parseInt(countryDetails[2]))-1).getCountries().add(country);
					}
				}
				for(int i=0;i<listOfCountries.size();i++)
				{
					listOfCountries.get(i).neighbours= new ArrayList<Country>();
				}
				if(currentLine.contains("[borders]"))
				{	while((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("["))
					{	
						//System.out.println(currentLine);
						if(currentLine.length() == 0) {
							continue;
							}
						String[] neighbourDetails= currentLine.split(" ");
						for(int i=0;i<neighbourDetails.length-1;i++)
						{
						listOfCountries.get(Integer.parseInt(neighbourDetails[0])-1).neighbours.add(listOfCountries.get(Integer.parseInt(neighbourDetails[i+1])-1));
						
						}
					}
				}
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		//graph creation
		
			for(int i=0;i<listOfCountries.size();i++) {
				List<Integer> templist = new ArrayList<Integer>();
					for(int j=0;j<listOfCountries.get(i).getNeighbours().size();j++)
						templist.add(listOfCountries.indexOf(listOfCountries.get(i).neighbours.get(j)));
						mapOfWorld.put(i,templist);
				
				
			}
			
			System.out.println(mapOfWorld.toString());
			
		/*	
			//display map
			for(Country c:mapOfWorld.keySet()) {
				System.out.println(c.getName());
				//System.out.println(mapOfWorld.get(c));
				for(Country c1: mapOfWorld.get(c)) {
					System.out.print(c1.getName()+"||");
				}
				System.out.println();
			}
		
		System.out.println("---------------");
		*/
		//validate map call
		validateMap(mapOfWorld);
		/*
		//display
		/*for(Continent c :listOfContinent) {
			System.out.println("Continent :"+c.getName());
			for(Country c1 :c.getCountries()) {
				System.out.print("Country :"+c1.getName()+":Neighbours->");
				for(Country c2 :c1.getNeighbours()) {
					System.out.print(c2.getName()+"||");
				}
				System.out.println();
			}
			System.out.println();
		}*/
			
	}
	
	public static void validateMap(HashMap<Integer,List<Integer>> mapOfWorld) {
		//traversing
		Boolean[] visited =new Boolean[mapOfWorld.keySet().size()];
		
		for(int i =0;i<visited.length;i++) {
			visited[i] =false;
		}
		
		LinkedList<Integer> queue= new LinkedList<Integer>();
		queue.add(0);
		visited[0] = true;
		//System.out.println(queue.poll().name);
		while(queue.size()>0)
		{
			Integer c1=queue.poll();	
			Iterator i = mapOfWorld.get(c1).listIterator();
			while(i.hasNext()) {
				int n = (int)i.next();				
				if(visited[n]== false) {
					visited[n] =true;
					queue.add(n);
				}
				
			}
			
		}
		int notConnected =0;
		for(int i=0;i<visited.length;i++) {
			System.out.print(i+"="+visited[i]+" ||");
			if(!visited[i]) {
				notConnected =1;
				break;
			}
		}
		System.out.println(notConnected);
		
	}
	
	public static void main(String args[])
	{
		MapReader m=new MapReader();
		m.parseMapFile("C:\\Program Files\\Domination\\maps\\testmap.map");
	}
	
}
