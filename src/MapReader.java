import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
public class MapReader {
	public List<Continent> listOfContinent;
	private File fileObject;
	private BufferedReader bufferReaderForFile;
	private String currentLine;
	public void parseMapFile(String filePath)
	{
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
					}
				}
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public static void main(String args[])
	{
		MapReader m=new MapReader();
		//String s="C:\\Program Files\\Domination\\maps\\ameroki.map\";
		m.parseMapFile("C:\\Program Files\\Domination\\maps\\ameroki.map");
	}
	
}
