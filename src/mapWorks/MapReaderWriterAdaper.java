package mapWorks;

import java.io.File;
import java.io.IOException;

import dao.Map;

public class MapReaderWriterAdaper extends DominationReaderWriter {
	ConquestReaderWriter crw = new ConquestReaderWriter();

	@Override
	public int parseMapFile(File file) {
		// TODO Auto-generated method stub
		return crw.parseMapFile(file);
	}

	@Override
	public void saveMap(Map map, String fileName) throws IOException {
		// TODO Auto-generated method stub
		crw.saveMap(map, fileName);
	}
	
	
}
