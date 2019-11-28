package pattern.Adapter;

import java.io.File;
import java.io.IOException;

import dao.Map;

/**
 * This class is used to implement Adapter Pattern
 *
 */
public class MapReaderWriterAdapter extends DominationReaderWriter {
	ConquestReaderWriter crw = new ConquestReaderWriter();

	/**
	 * Constructor to initialize ConquestReaderWriter object
	 * 
	 * @param crw Object of ConquestReaderWriter class
	 */
	public MapReaderWriterAdapter(ConquestReaderWriter crw) {
		this.crw = crw;
	}

	/**
	 * This method parses the map file in Conquest format
	 * 
	 * @param map  Map Object
	 * @param file Map file to be parsed
	 * @return 1 if successful else 0
	 */
	@Override
	public int parseMapFile(Map map, File file) {
		return crw.parseMapFile(map, file);
	}

	/**
	 * This method saves the map file in Conquest format
	 * 
	 * @param map      Map Object
	 * @param fileName Name of the file
	 * @throws IOException for Buffered Reader
	 */
	@Override
	public void saveMap(Map map, String fileName) throws IOException {
		crw.saveMap(map, fileName);
	}

}
