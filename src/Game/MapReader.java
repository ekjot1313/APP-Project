package Game;

import Game.Map;
import mapWorks.MapEditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * 
 * @author Piyush
 * @author Mitalee Naik
 * @since 1.0.0
 *
 */
public class MapReader {
	
	/**
	 * Map to store the current map object
	 */
	public Map map;
	/**
	 * FileObject to parse the map file
	 */
	private File fileObject;
	/**
	 * BufferedReader to process map file
	 */
	private BufferedReader bufferReaderForFile;
	/**
	 * CurrentLine to store the current line of parsing
	 */
	private String currentLine;
	/**
	 * Map to store the Index of countries
	 */
	public HashMap<Integer, List<Integer>> mapOfWorld = new HashMap<Integer, List<Integer>>();
	
	/**
	 * Default Constructor
	 */
	public MapReader(){
		this.map = new Map();
	}
	/**
	 * method to parse the map file
	 * @param file
	 * @return
	 */
	public int parseMapFile(File file) {
		map = new Map();

		try {
			
			bufferReaderForFile = new BufferedReader(new FileReader(file));
			
			while ((currentLine = bufferReaderForFile.readLine()) != null) {
				System.out.println("curr line :" +currentLine);
				if (currentLine.contains("[continents]")) {
					loadContinents();
				}

				if (currentLine.contains("[countries]")) {
					loadCountries();
				}
				if (currentLine.contains("[borders]")) {
					loadBorders();
				}

			}
			System.out.println("mapOfworld "+mapOfWorld.toString());
			// validate map call
			int notConnected = validateMap(map);
			int notConnectedSubGraph = validateContinent(map);
			System.out.println();
			if (notConnected == 0 && notConnectedSubGraph == 0) {
				System.out.println("Valid Map");
				// display
				display(map);
			} else {
				System.out.println("Invalid map");
				return 0;
			}

		} catch (Exception e) {
			if (e.toString().contains("FileNotFoundException"))
				System.out.println("Invalid filename");
			System.out.println(e);
			return 0;
		}
		return 1;
	}

	private void loadBorders() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			// System.out.println(currentLine);
			if (currentLine.length() == 0) {
				continue;
			}
			String[] neighbourDetails = currentLine.split(" ");
			for (int i = 0; i < neighbourDetails.length - 1; i++) {
				map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getNeighbors()
						.add(map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1).getName());

				if (!map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getContinentName().equals(
						map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1).getContinentName()))

				{
					map.getContinentFromName(
							map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getContinentName())
							.getBridges()
							.add(new Bridge(
									map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1)
											.getContinentName(),
									map.getListOfCountries().get(Integer.parseInt(neighbourDetails[0]) - 1).getName(),
									map.getListOfCountries().get(Integer.parseInt(neighbourDetails[i + 1]) - 1)
											.getName()));
				}
			}

		}

	}

	private void loadCountries() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {

			// System.out.println(currentLine);
			if (currentLine.length() == 0) {
				continue;
			}
			String[] countryDetails = currentLine.split(" ");
			Country country = new Country();
			country.setName(countryDetails[1]);
			country.setContinentName(map.getListOfContinent().get((Integer.parseInt(countryDetails[2])) - 1).getName());
			map.getListOfCountries().add(country);
			map.getListOfContinent().get((Integer.parseInt(countryDetails[2])) - 1).getCountries()
					.add(countryDetails[1]);
		}

	}

	private void loadContinents() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
			
			System.out.println(currentLine);
			if (currentLine.length() == 0) {
				continue;
			}
			String[] continentDetails = currentLine.split(" ");
			Continent continent = new Continent();
			continent.setName(continentDetails[0]);
			continent.setContinentValue(Integer.parseInt(continentDetails[1]));
			map.getListOfContinent().add(continent);

		}

	}

	/**
	 * This method display/print the map
	 * 
	 * @param map2
	 */
	public void display(Map map2) {
		// TODO Auto-generated method stub
		map =map2;
		int notConnected = validateMap(map);
		int notConnectedSubGraph = validateContinent(map);
		//int notConnectedSubGraph =0;
		System.out.println(notConnected +" notConnected");
		System.out.println(notConnectedSubGraph +" notConnectedSubGraph");
		
		if (notConnected == 0  && notConnectedSubGraph == 0) {
			System.out.println("Valid Map");
			// display
			if (map2.getListOfContinent().size() > 0) {
				for (Continent c : map2.getListOfContinent()) {
					System.out.println();
					System.out.println("Continent :" + c.getName());

					if(c.getBridges().size()>0) {
					System.out.println("Bridges");
					for (Bridge bridge : c.getBridges()) {
						System.out.println(
								bridge.getNeigContinent() + " " + bridge.getCountry1() + " " + bridge.getCountry2());
					}

				}
					for (String c1 : c.getCountries()) {
						System.out.print("Country :" + c1 + ": Neighbours->");

						for (Country country : map2.getListOfCountries()) {
							if (c1.equals(country.getName())) {

								for (String c2 : country.getNeighbors()) {
									System.out.print(c2 + "||");
								}

								System.out.println();
							}
						}

					}

				}
			} else {
				System.out.println("Map Empty.");
			}
		} else
			System.out.println("Invalid map");
		
		
	}

	public int validateMap(Map map) {
		// traversing
		int notConnected = 0;
		if (checkDuplicates(map) == 0) {

			// graph creation

			for (int i = 0; i < map.getListOfCountries().size(); i++) {
				List<Integer> templist = new ArrayList<Integer>();
				for (int j = 0; j < map.getListOfCountries().get(i).getNeighbors().size(); j++)
					templist.add(
							map.getListOfCountries().indexOf(map.getCountryFromName(map.getListOfCountries().get(i).getNeighbors().get(j))));
				mapOfWorld.put(i, templist);

			}

			Boolean[] visited = new Boolean[mapOfWorld.keySet().size()];
			for (int i = 0; i < visited.length; i++) {
				visited[i] = false;
			}

			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(0);
			visited[0] = true;
			 System.out.println(mapOfWorld.toString());

			while (queue.size() > 0) {
				 System.out.println(queue.peek());
				Integer c1 = queue.poll();
				Iterator i = mapOfWorld.get(c1).listIterator();
				while (i.hasNext()) {
					int n = (int) i.next();
					if (visited[n] == false) {
						visited[n] = true;
						queue.add(n);
					}

				}

			}

			for (int i = 0; i < visited.length; i++) {
				System.out.print(i + "=" + visited[i] + " ||");
				if (!visited[i]) {
					notConnected = 1;
					System.out.println();
					System.out.println("Not a connected graph");
					break;
				}
			}

		} else
			notConnected = 1;
		return notConnected;
	}

	public int checkDuplicates(Map map) {
		int duplicate = 0;
		for (int i = 0; i < (map.getListOfContinent().size()-1); i++)
			for (int j = i + 1; j < map.getListOfContinent().size(); j++)
				if ((map.getListOfContinent().get(i).getName())
						.equalsIgnoreCase(map.getListOfContinent().get(j).getName())) {
					duplicate = 1;
					System.out.println("Duplicate Continent :" + map.getListOfContinent().get(i).getName());
					break;
				}
		if (duplicate == 0)
			for (int i = 0; i < (map.getListOfCountries().size()-1); i++)
				for (int j = i + 1; j < map.getListOfCountries().size(); j++)
					if ((map.getListOfCountries().get(i).getName())
							.equalsIgnoreCase(map.getListOfCountries().get(j).getName())) {
						duplicate = 1;
						System.out.println("Duplicate Country :" + map.getListOfCountries().get(i).getName());
						break;
					}
		return duplicate;
	}

	public static void main(String args[]) {
		MapReader m = new MapReader();
		String filename = "ameroki.map";
		System.out.println(System.getProperty("user.dir"));
		String currentPath = System.getProperty("user.dir");
		currentPath += "\\src\\Maps\\" + filename;
		File file = new File(currentPath);
		m.parseMapFile(file);
	}

	/**
	 * This method returns the currently loaded map
	 * 
	 * @return
	 */
	public Map getMap() {
		return this.map;
	}
	
	
	public Map returnCopyOfMap(Object a) {
		return (Map)a;
		
	}
	
	
	public int validateContinent(Map map) {

		MapEditor mpeNew = new MapEditor();
		MapReader mr = new MapReader();
		Map newMap = new Map(map);
		mpeNew.map = newMap;
		
			for(int i =0;i<newMap.getListOfContinent().size();i++) {
				String remainingContinent ="editcontinent "; 
			
				for(int j=0;j<newMap.getListOfContinent().size();j++) {
					if(i != j )
						remainingContinent +="-remove "+newMap.getListOfContinent().get(j).getName()+" ";
					
				}
				remainingContinent.trim();
				mpeNew.editContinent(remainingContinent.split(" "));
				if(mr.validateMap(mpeNew.map) == 1) {
					return 1;
				}
				newMap = new Map(map);
				mpeNew.map = newMap;
			}
		
		return 0;
	}
	
	

}