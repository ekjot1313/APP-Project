package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MapReaderTestBuild1.class, DaoDiceTest.class, MapTest.class })
public class AllTests {
}