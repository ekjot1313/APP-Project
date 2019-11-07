package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.daotest.DaoTestSuite;
import test.gametest.GameTestSuite;
import test.mapworkstest.MapWorksTestSuite;
import test.viewtest.ViewTestSuite;

@RunWith(Suite.class)
@SuiteClasses({DaoTestSuite.class, MapWorksTestSuite.class, GameTestSuite.class, ViewTestSuite.class  })
public class AllTests {
}