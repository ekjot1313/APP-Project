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
						
						System.out.println(currentLine);
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
				
				if(currentLine.contains("[countries]"))
				{
					while((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("["))
					{
						
						System.out.println(currentLine);
						if(currentLine.length() == 0) {
							continue;
						}
						String[] countryDetails = currentLine.split(" ");
						
						Country country = new Country();
						country.setName(countryDetails[1]);
						country.setContinentName(listOfContinent.get((Integer.parseInt(countryDetails[2]))-1));
						listOfCountries.add(country);
					}
				}
				if(currentLine.contains("[borders]"))
				{	while((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("["))
					{	
						System.out.println(currentLine);
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
		
		for(Continent c :listOfContinent)
		System.out.println(c.getName() +" :"+c.getContinentValue());
		System.out.println("countries");
		for(Country c :listOfCountries)
			System.out.println(c.getName() +" :"+c.getContinentName().getName()+":"+c.getNeighbours());
			
	}
	public static void main(String args[])
	{
		MapReader m=new MapReader();
		m.parseMapFile("C:\\Program Files\\Domination\\maps\\ameroki.map");
	}
	
}
