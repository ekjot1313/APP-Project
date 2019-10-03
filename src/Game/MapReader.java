package Game;

import Game.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class MapReader {
	public Map map;
	// public List<Continent> listOfContinent;
	// public List<Country> listOfCountries;
	private File fileObject;
	private BufferedReader bufferReaderForFile;
	private String currentLine;
	public HashMap<Integer, List<Integer>> mapOfWorld = new HashMap<Integer, List<Integer>>();

	public void parseMapFile(String filePath) {
		map = new Map();
		map.listOfContinent = new ArrayList<Continent>();
		map.listOfCountries = new ArrayList<Country>();
		try {

			bufferReaderForFile = new BufferedReader(new FileReader(new File(filePath)));
			while ((currentLine = bufferReaderForFile.readLine()) != null) {

				if (currentLine.contains("[continents]")) {
					while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {

						// System.out.println(currentLine);
						if (currentLine.length() == 0) {
							continue;
						}
						String[] continentDetails = currentLine.split(" ");

						Continent continent = new Continent();
						continent.setName(continentDetails[0]);
						continent.setContinentValue(Integer.parseInt(continentDetails[1]));
						map.listOfContinent.add(continent);

					}
				}
				for (int i = 0; i < map.listOfContinent.size(); i++) {
					map.listOfContinent.get(i).setCountries(new ArrayList<Country>());
				}
				if (currentLine.contains("[countries]")) {
					while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {

						// System.out.println(currentLine);
						if (currentLine.length() == 0) {
							continue;
						}
						String[] countryDetails = currentLine.split(" ");

						Country country = new Country();
						country.setName(countryDetails[1]);
						country.setContinentName(map.listOfContinent.get((Integer.parseInt(countryDetails[2])) - 1));
						map.listOfCountries.add(country);
						map.listOfContinent.get((Integer.parseInt(countryDetails[2])) - 1).getCountries().add(country);
					}
				}
				for (int i = 0; i < map.listOfCountries.size(); i++) {
					map.listOfCountries.get(i).neighbours = new ArrayList<Country>();
				}
				if (currentLine.contains("[borders]")) {
					while ((currentLine = bufferReaderForFile.readLine()) != null && !currentLine.contains("[")) {
						// System.out.println(currentLine);
						if (currentLine.length() == 0) {
							continue;
						}
						String[] neighbourDetails = currentLine.split(" ");
						for (int i = 0; i < neighbourDetails.length - 1; i++) {
							map.listOfCountries.get(Integer.parseInt(neighbourDetails[0]) - 1).neighbours
									.add(map.listOfCountries.get(Integer.parseInt(neighbourDetails[i + 1]) - 1));

						}
					}
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		// graph creation

		for (int i = 0; i < map.listOfCountries.size(); i++) {
			List<Integer> templist = new ArrayList<Integer>();
			for (int j = 0; j < map.listOfCountries.get(i).getNeighbours().size(); j++)
				templist.add(map.listOfCountries.indexOf(map.listOfCountries.get(i).neighbours.get(j)));
			mapOfWorld.put(i, templist);

		}

		System.out.println(mapOfWorld.toString());

		/*
		 * //display map for(Country c:mapOfWorld.keySet()) {
		 * System.out.println(c.getName()); //System.out.println(mapOfWorld.get(c));
		 * for(Country c1: mapOfWorld.get(c)) { System.out.print(c1.getName()+"||"); }
		 * System.out.println(); }
		 * 
		 * System.out.println("---------------");
		 */
		// validate map call
		int notConnected = validateMap(mapOfWorld, map.listOfContinent, map.listOfCountries);
		System.out.println();
		if (notConnected == 0) {
			System.out.println("Valid Map");
			// display
			for (Continent c : map.listOfContinent) {
				System.out.println("Continent :" + c.getName());
				for (Country c1 : c.getCountries()) {
					System.out.print("Country :" + c1.getName() + ":Neighbours->");
					for (Country c2 : c1.getNeighbours()) {
						System.out.print(c2.getName() + "||");
					}
					System.out.println();
				}
				System.out.println();
			}
		} else
			System.out.println("Invalid map");

	}

	public static int validateMap(HashMap<Integer, List<Integer>> mapOfWorld, List<Continent> listOfContinent,
			List<Country> listOfCountries) {
		// traversing
		int notConnected = 0;
		if (checkDuplicates(listOfContinent, listOfCountries) == 0) {
			Boolean[] visited = new Boolean[mapOfWorld.keySet().size()];
			for (int i = 0; i < visited.length; i++) {
				visited[i] = false;
			}

			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(0);
			visited[0] = true;
			// System.out.println(queue.poll().name);

			while (queue.size() > 0) {
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

	public static int checkDuplicates(List<Continent> listOfContinent, List<Country> listOfCountries) {
		int duplicate = 0;
		for (int i = 0; i < (listOfContinent.size() - 1); i++)
			for (int j = i + 1; j < listOfContinent.size(); j++)
				if ((listOfContinent.get(i).getName()).equalsIgnoreCase(listOfContinent.get(j).getName())) {
					duplicate = 1;
					System.out.println("Duplicate Continent :" + listOfContinent.get(i).getName());
					break;
				}
		if (duplicate == 0)
			for (int i = 0; i < (listOfCountries.size() - 1); i++)
				for (int j = i + 1; j < listOfCountries.size(); j++)
					if ((listOfCountries.get(i).getName()).equalsIgnoreCase(listOfCountries.get(j).getName())) {
						duplicate = 1;
						System.out.println("Duplicate Country :" + listOfCountries.get(i).getName());
						break;
					}
		return duplicate;
	}

	public static void main(String args[]) {
		MapReader m = new MapReader();
		m.parseMapFile("C:\\Users\\ekjot\\Desktop\\Sample map.map");
	}

}
