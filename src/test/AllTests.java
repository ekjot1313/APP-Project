package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.GameEngine.GameEngineTestSuite;
import test.daotest.DaoTestSuite;
import test.gametest.GameTestSuite;
import test.mapworkstest.MapWorksTestSuite;
import test.pattern.Adapter.AdapterTestSuite;
import test.pattern.Strategy.StrategyTestSuite;
import test.pattern.builder.BuilderTestSuite;
import test.pattern.observer.ViewTestSuite;

@RunWith(Suite.class)
@SuiteClasses({DaoTestSuite.class, MapWorksTestSuite.class, GameTestSuite.class, ViewTestSuite.class ,StrategyTestSuite.class , GameEngineTestSuite.class, BuilderTestSuite.class, AdapterTestSuite.class})
public class AllTests {
}