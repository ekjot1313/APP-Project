package test.pattern.adapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.mapworkstest.MapEditorTest;

@RunWith(Suite.class)
@SuiteClasses({ ConquestReaderWriterTest.class, DominationReaderWriterTest.class})
public class AdapterTestSuite {

}
