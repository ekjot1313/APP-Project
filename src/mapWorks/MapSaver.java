package mapWorks;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Game.Map;

/**
 * This Class will save the given Map object to .map file
 * 
 * @author ekjot
 *
 */
public class MapSaver {        //UNDER CONSTRUCTION
	public Map map;
	public String fileName;

	
	public void saveMap(Map map,String fileName) {
		String currentPath = System.getProperty("user.dir")+"\\src\\Maps\\";
		String mapPath=currentPath+mapSaver.fileName+".map";
		
		BufferedWriter bwFile=new BufferedWriter(new FileWriter(mapPath));
		
	}
	public static void main(String[] args) throws IOException {
		
	

	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

}
